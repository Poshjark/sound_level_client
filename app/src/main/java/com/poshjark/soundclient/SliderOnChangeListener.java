package com.poshjark.soundclient;

import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;


public class SliderOnChangeListener implements Slider.OnChangeListener{
    Client client;

    public SliderOnChangeListener(Client _client){
        client = _client;
    }
    @Override
    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
        if(fromUser){
            View parent = (View)slider.getParent();
            TextView text = (TextView) parent.findViewById(R.id.volume_text);
            text.setText(String.valueOf(value));
            StringBuilder new_sound_level = new StringBuilder(Integer.toString(Math.round(value)));
            while(new_sound_level.length() < 3){
                new_sound_level.insert(0, "0");
            }
            client.send_message("vsl" + new_sound_level);
        }

    }

}
