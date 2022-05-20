package com.poshjark.soundclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client{
    private static final String TAG = "SoundClient/ClientClass";
    private final String host;
    private final int port;

    public Client(String _host, int _port){
        host = _host;
        port = _port;
    }

    public class ThreadTaskForSendingWithResponseSaving implements Runnable{
        private String message;
        private String response;

        @Override
        public void run() {
            try(
                    Socket socket = new Socket(host,port);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ){
                out.println(message);
                response = in.readLine();
            } catch(IOException e){
                System.exit(1);
            }
        }

        public void setMessage(String _message){
            message = _message;
        }


        public String getResponse(){
            return response;
        }

    }

    public String send_message(String message){
        String response;
        ThreadTaskForSendingWithResponseSaving send_message_task = new ThreadTaskForSendingWithResponseSaving();
        send_message_task.setMessage(message);
        Thread send_message_thread = new Thread(send_message_task);
        send_message_thread.start();
        try{
            send_message_thread.join();
        }
        catch (InterruptedException ie){
            Log.e(TAG, "Send message thread is interrupted\n");
        }
        response = send_message_task.getResponse();
        return response;
    }


}



