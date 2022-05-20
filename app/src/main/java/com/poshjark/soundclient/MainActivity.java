package com.poshjark.soundclient;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.Slider;



public class MainActivity extends AppCompatActivity {

    Button volume_up_button_ref;
    Client client;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new Client("192.168.0.100",3000);
        update_volume_level();
        volume_up_button_ref = (Button) findViewById(R.id.volume_step_up_button);
        Button volume_down_button_ref = (Button) findViewById(R.id.volume_step_down_button);
        volume_up_button_ref.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                volume_step_change(true);
            }
        }));
        volume_down_button_ref.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View view) {
                volume_step_change(false);
            }
        }));

        Slider slider = (Slider) findViewById(R.id.slider);
        slider.addOnChangeListener( new SliderOnChangeListener(this.client));

        Button play_pause_button = (Button) findViewById(R.id.play_pause_button);
        play_pause_button.setOnClickListener(new OnClickListenerWithMessageSending("mpp"));

        Button next_media_button = (Button) findViewById(R.id.next_media_button);
        next_media_button.setOnClickListener(new OnClickListenerWithMessageSending("mnx"));

        Button prev_media_button = (Button) findViewById(R.id.previous_media_button);
        prev_media_button.setOnClickListener(new OnClickListenerWithMessageSending("mpr"));

        final EditText text = findViewById(R.id.ip_form);
        InputFilterForIP[] filters = new InputFilterForIP[1];
        filters[0] = new InputFilterForIP();
        text.setFilters(filters);
    }



    private void update_volume_level(){
        Response response = new Response(client.send_message("gvl"));
        handle_response(response);
    }

    public void volume_step_change(boolean positive){
        String command_for_server;
        if(positive){
            command_for_server = "vsu";
        }
        else{
            command_for_server = "vsd";
        }
        Response response = new Response(client.send_message(command_for_server));
        handle_response(response);
    }

    private void handle_response(Response response){
        Slider slider = findViewById(R.id.slider);
        TextView txt = findViewById(R.id.volume_text);
        txt.setText(String.valueOf(response.getVolume_level()));
        slider.setValue(response.getVolume_level());
    }

    public class OnClickListenerWithMessageSending implements View.OnClickListener{
        private final String message;
        public OnClickListenerWithMessageSending(String _message){
            this.message = _message;
        }

        @Override
        public void onClick(View v) {
            Response response = new Response(client.send_message(message));
            handle_response(response);
        }
    }
}

