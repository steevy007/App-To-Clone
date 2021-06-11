package info.atalou.apps.hangovertickets.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import info.atalou.apps.hangovertickets.R;
import info.atalou.apps.hangovertickets.constants.URLConstants;
import info.atalou.apps.hangovertickets.models.Tickets;

public class RequestActivity extends Activity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent();
//        intent.putExtra("pos", position);
//        setResult(Activity.RESULT_OK, intent);
//        finish();


//        String newString;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                newString= null;
//            } else {
//                newString= extras.getString("STRING_I_NEED");
//            }
//        } else {
//            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
//        }


        Tickets ticket =  getIntent().getParcelableExtra("ticket");
        postRequest(ticket.getBarcode(),ticket.getDay(),ticket.getGate());
    }





    private void postRequest(final String barcode, final String day, final String gate){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLConstants.URL_TICKETS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("ticket");

                            Tickets tickets = new Tickets();
                            tickets.setBarcode(jsonResponse.getString("barcode"));
                            tickets.setDay(jsonResponse.getString("day"));
                            tickets.setGate(jsonResponse.getString("gate"));
                            tickets.setCategory(jsonResponse.getString("category"));
                            tickets.setColor(jsonResponse.getString("color"));
                            tickets.setStatus(Integer.parseInt(jsonResponse.getString("status")));
                            tickets.setScanDate(jsonResponse.getString("scandate"));
                            tickets.setMessage(jsonResponse.getString("msg"));


                            Intent intent = new Intent();
                            intent.putExtra("key_ticket", tickets);
                             setResult(Activity.RESULT_OK, intent);
                             finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Site JSONException: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            //intent.putExtra("key_ticket", tickets);
                            setResult(Activity.RESULT_CANCELED, intent);
                            finish();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Site VolleyError: "+error.toString()+"\n"+barcode,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent();
                        //intent.putExtra("key_ticket", tickets);
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("barcode", String.format("%s",barcode));
                params.put("day", String.format("%s",day));
                params.put("gate", String.format("%s",gate));
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
    }
}
