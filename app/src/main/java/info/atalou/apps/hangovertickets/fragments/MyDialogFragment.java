package info.atalou.apps.hangovertickets.fragments;



import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.atalou.apps.hangovertickets.R;
import info.atalou.apps.hangovertickets.models.Tickets;


public class MyDialogFragment extends DialogFragment {

    private LinearLayout message_color;
    private TextView message_icon,message_title, messages_ticket,messages_ticket_scan;
    private TextView ticket_barcode,ticket_day,ticket_category,ticket_color;

    private Tickets tickets;



    public MyDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static MyDialogFragment newInstance(Tickets tickets) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", tickets);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        message_color = view.findViewById(R.id.message_color);
        message_icon = view.findViewById(R.id.message_icon);
        message_title = view.findViewById(R.id.message_title);
        messages_ticket = view.findViewById(R.id.messages_ticket);
        messages_ticket_scan = view.findViewById(R.id.messages_ticket_scan);

        ticket_barcode = view.findViewById(R.id.ticket_barcode);
        ticket_day = view.findViewById(R.id.ticket_day);
        ticket_category = view.findViewById(R.id.ticket_category);
        ticket_color = view.findViewById(R.id.ticket_color);

        // Fetch arguments from bundle and set title
        tickets = getArguments().getParcelable("data");

        //Show Datas
        ticket_barcode.setText(tickets.getBarcode());
        messages_ticket_scan.setText((tickets.getScanDate()));
        if(tickets.getCategory().equalsIgnoreCase("error")) {
            ticket_category.setText("N/A");
        }else {
            ticket_category.setText(tickets.getCategory().toLowerCase());
        }


        if(tickets.getDay().equalsIgnoreCase("date_1")) {
            ticket_day.setText("DAY 1");

            switch (tickets.getCategory()){
                case "vip":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.vip_day_one));
                    break;
                case "ga":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.ga_day_one));
                    break;
                case "comp":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.comp_day_one));
                    break;
            }
        }
        else if(tickets.getDay().equalsIgnoreCase("date_2")) {
            ticket_day.setText("DAY 2");
            switch (tickets.getCategory()){
                case "vip":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.vip_day_two));
                    break;
                case "ga":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.ga_day_two));
                    break;
                case "comp":
                    ticket_color.setBackgroundColor(getResources().getColor(R.color.comp_day_two));
                    break;
            }

        } else ticket_day.setText("N/A");

        switch (tickets.getMessage()){
            case "not_exist":
                message_color.setBackgroundColor(getResources().getColor(R.color.not_exist));
                message_icon.setText(R.string.not_exist_icon);
                message_title.setText(R.string.not_exist);
                messages_ticket.setText(R.string.not_exist_msg);
                break;
            case "validate":
                message_color.setBackgroundColor(getResources().getColor(R.color.validate));
                message_icon.setText(R.string.validate_icon);
                message_title.setText(R.string.validate);
                messages_ticket.setText(R.string.validate_msg);
                break;
            case "warning":
                message_color.setBackgroundColor(getResources().getColor(R.color.warning));
                message_icon.setText(R.string.warning_icon);
                message_title.setText(R.string.warning);
                messages_ticket.setText(R.string.warning_msg);
                break;
            case "error_day":
                message_color.setBackgroundColor(getResources().getColor(R.color.error_day));
                message_icon.setText(R.string.error_day_icon);
                message_title.setText(R.string.error_day);
                messages_ticket.setText(R.string.error_day_msg);
                break;
            case "error_entrance":
                message_color.setBackgroundColor(getResources().getColor(R.color.error_entrance));
                message_icon.setText(R.string.error_entrance_icon);
                message_title.setText(R.string.error_entrance);
                messages_ticket.setText(R.string.error_entrance_msg);
                break;
            case "error_both":
                message_color.setBackgroundColor(getResources().getColor(R.color.bg_swipe_group_item_pinned));
                message_icon.setText(R.string.material_icon_flag);
                message_title.setText(R.string.error_both);
                message_icon.setTextColor(Color.WHITE);
                message_title.setTextColor(Color.WHITE);
                messages_ticket.setText(R.string.error_both_msg);
                break;



        }

    }



}
