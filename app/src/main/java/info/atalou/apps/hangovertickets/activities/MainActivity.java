package info.atalou.apps.hangovertickets.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import info.atalou.apps.hangovertickets.constants.URLConstants;
import info.atalou.apps.hangovertickets.fragments.MyDialogFragment;
import info.atalou.apps.hangovertickets.R;
import info.atalou.apps.hangovertickets.models.Tickets;

public class MainActivity extends AppCompatActivity {
    private EditText eBarcode;
    private Button bRemove;
    private Timer timer;
    String strGateName, strDayName;
    public static final int PICK_TICKET_REQUEST    = 1;  // The request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // making toolbar transparent
        // transparentToolbar();

        setContentView(R.layout.activity_main);
        eBarcode = findViewById(R.id.barcode);
        bRemove = findViewById(R.id.remove);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);

        strDayName = SP.getString("key_pref_day","");
        strGateName = SP.getString("key_pref_gate","");

        editAction();
        removeAction();


    }

    private void removeAction() {
        bRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eBarcode.getText().clear();
                eBarcode.requestFocus();
                eBarcode.setFocusableInTouchMode(true);

               // eBarcode.setFocusable(true);
               // eBarcode.setFocusableInTouchMode(true);
            }
        });
    }

    private void transparentToolbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void editAction() {
        eBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // user is typing: reset already started timer (if existing)
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString().trim())) {
                    // user typed: start the timer
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // do your actual work here
                            sendRequest( eBarcode.getText().toString().trim().toUpperCase());

                        }
                    }, 600); // 600ms delay before the timer executes the "run" method from TimerTask

                }

            }
        });
    }


    private void sendRequest(String mBarcode){
        postRequest(mBarcode);
    }




    private void showEditDialog(Tickets tickets) {
        FragmentManager fm = getSupportFragmentManager();
        MyDialogFragment argumentFragment = MyDialogFragment.newInstance(tickets);
        Bundle data = new Bundle();//Use bundle to pass data
        data.putParcelable("data", tickets);
        argumentFragment.show(fm, "fragment_edit_name");
        eBarcode.getText().clear();
        eBarcode.requestFocus();
        eBarcode.setFocusableInTouchMode(true);

       // eBarcode.setFocusable(true);
       // eBarcode.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eBarcode.requestFocus();
        eBarcode.setFocusableInTouchMode(true);

       // eBarcode.setFocusable(true);
       // eBarcode.setFocusableInTouchMode(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eBarcode.requestFocus();
        eBarcode.setFocusableInTouchMode(true);

       // eBarcode.setFocusable(true);
       // eBarcode.setFocusableInTouchMode(true);
    }

    private void postRequest(final String mBarcode){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URLConstants.URL_TICKETS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("ticket");

                            Tickets tickets = new Tickets();

                            boolean error = jsonResponse.getBoolean("error");

                           // Toast.makeText(getApplicationContext(),"Site DAY: " + strDayName + " GATE " + strGateName,Toast.LENGTH_LONG).show();



                            if(error == false){
                                tickets.setBarcode(jsonResponse.getString("barcode"));
                                tickets.setDay(jsonResponse.getString("day"));
                                tickets.setGate(jsonResponse.getString("gate"));
                                tickets.setCategory(jsonResponse.getString("category"));
                                tickets.setColor(jsonResponse.getString("color"));
                                tickets.setMessage(jsonResponse.getString("msg"));
                                tickets.setScanDate(jsonResponse.getString("scandate"));


                            }else{

                               tickets.setBarcode(jsonResponse.getString("barcode"));
                                tickets.setMessage(jsonResponse.getString("msg"));


                                //Toast.makeText(getApplicationContext(),"Site MESSAGE: "+ jsonResponse.getString("msg"),Toast.LENGTH_LONG).show();


                                if (jsonResponse.getString("msg").equalsIgnoreCase("not_exist")) {
                                        tickets.setDay("error");
                                        tickets.setCategory("error");
                                }else{
                                    tickets.setCategory(jsonResponse.getString("category"));
                                    tickets.setDay(jsonResponse.getString("day"));
                                }

                            }


                                  showEditDialog(tickets);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Site JSONException: "+e.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("Resp ERROR", e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("barcode", String.format("%s",mBarcode));
                params.put("day", String.format("%s",strDayName));
                params.put("gate", String.format("%s",strGateName));
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                // launch settings activity
                startActivity(new Intent(MainActivity.this, SettingsPrefActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
