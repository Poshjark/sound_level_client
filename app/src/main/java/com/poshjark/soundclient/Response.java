package com.poshjark.soundclient;


public class Response {
    private boolean success;
    private float volume_level;
    private boolean muted;

    Response(String response_str){
        try{
            success = response_str.charAt(0) == 't';
            volume_level = Float.parseFloat(response_str.substring(1,4));
            //Log.i("Response parsing","new volume level is ");
            muted = response_str.charAt(4) == 't';
        }
        catch (IndexOutOfBoundsException e){
            success = false;
        }
        catch (NumberFormatException e){
            success = false;
            volume_level = -1;
            muted = false;
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public boolean isSuccess() {
        return success;
    }

    public float getVolume_level() {
        return volume_level;
    }
}
