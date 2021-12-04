package com.aparesh01.blinktalk;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity2 extends Activity {

    public final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public final static String MODULE_MAC = "98:D3:71:F5:D5:24";
    public final static int REQUEST_ENABLE_BT = 1;
    public static BluetoothAdapter bta1;
    public static BluetoothSocket mmSocket1;
    public static BluetoothDevice mmDevice1;
    public Handler myHandler2;
    ConnectedBtThread btt1 = null;
    ImageButton lightBtn,alarmBtn,musicBtn,keyBoard;
    TextView textView;
    int count,st,var;
    Boolean forLight = false;
    Boolean forAlarm = false;
    Boolean forMusic = false;
    MediaPlayer mediaPlayer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lightBtn = findViewById(R.id.btn1);
        alarmBtn = findViewById(R.id.btn2);
        musicBtn = findViewById(R.id.btn3);
        keyBoard = findViewById(R.id.btn4);
        textView = findViewById(R.id.main2_textview1);
        bta1 = BluetoothAdapter.getDefaultAdapter();
        if(!bta1.isEnabled()){
            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBt,REQUEST_ENABLE_BT);
        }else{
            textView.setText("Connecting...");
            initiateProcess();
        }
    }

    void Def_LightBtn(){
        lightBtn.setBackground(getDrawable(R.drawable.gradient));
    }
    void Def_AlarmBtn(){
        alarmBtn.setBackground(getDrawable(R.drawable.gradient));
    }
    void Def_MusicBtn(){
        musicBtn.setBackground(getDrawable(R.drawable.gradient));
    }
    void Def_KeyBtn(){
        keyBoard.setBackground(getDrawable(R.drawable.gradient));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT){
            textView.setText("Connecting...");
            initiateProcess();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initiateProcess() {
        if(bta1.isEnabled()){
            BluetoothSocket tmp;
            mmDevice1 = bta1.getRemoteDevice(MODULE_MAC);
            try{
                tmp = mmDevice1.createRfcommSocketToServiceRecord(MY_UUID);
                mmSocket1 = tmp;
                mmSocket1.connect();
                textView.setText("Connected");
                Toast.makeText(this, "Bluetooth is Connected", Toast.LENGTH_SHORT).show();
            }catch(IOException e) {
                try {
                    mmSocket1.close();
                } catch (IOException e2) {
                    return;
                }
            }
            myHandler2 = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == ConnectedBtThread.RESPONSE_MESSAGE){
                        String txt = (String) msg.obj;
                        count = Integer.parseInt(txt);
                        if(count  == 0 && st ==0) {
                            st = 1;
                            var = 0;
                            onLightBtn();
                        }
                        else if (count ==1 && st == 1){
                            if (var < 4){
                                var++;
                                if(var == 1){
                                    onAlarmBtn();
                                }
                                else if (var == 2){
                                    onMusicBtn();
                                }
                                else if (var == 3){
                                    onKeyBtn();
                                }
                            }
                            else{
                                var =0;
                                onLightBtn();
                            }
                        }
                        else if (count == 0 && st == 1){
                            switch (var){
                                case 0:
                                    onSelectLightBtn();
                                    break;
                                case 1:
                                    onSelectAlarmBtn();
                                    break;
                                case 2:
                                    onSelectMusicBtn();
                                    break;
                                case 3:
                                    onSelectKeyBtn();
                                    break;
                            }
                        }
                    }
                }
            };
            Log.i("[BLUETOOTH]","Creating and Running Thread");
            btt1 = new ConnectedBtThread(mmSocket1,myHandler2);
            btt1.start();
            }
            }

    private void onSelectKeyBtn() {
        count = 1; st = 0; var = 0;
        Def_KeyBtn();
        btt1.cancel();
        stopPlayer();
        try {
            mmSocket1.close();
        } catch (IOException e2) {
           Log.e("[ERR-Socket]",""+e2);
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void onSelectMusicBtn() {
        count = 1; st = 0; var = 0;
        Def_MusicBtn();
        if (!forMusic){
            if (mediaPlayer == null){
                forMusic = true;
                mediaPlayer = MediaPlayer.create(this,R.raw.emotional);
                textView.setText("Playing music..");
                musicBtn.setImageResource(R.drawable.music_playing);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopPlayer();
                    }
                });
            }
            mediaPlayer.start();
        }else{
            stopPlayer();
            textView.setText("Music stopped");
            musicBtn.setImageResource(R.drawable.music_logo);
            forMusic = false;
        }
    }

    private void onSelectAlarmBtn() {
        count = 1; st = 0; var = 0;
        Def_AlarmBtn();
        Log.i("[BLUETOOTH]", "Attempting to send data");
        if (mmSocket1.isConnected() && btt1 != null) {
            if (!forAlarm) {
                String sendtxt = "3";
                btt1.write(sendtxt.getBytes());
                forAlarm = true;
                textView.setText("Bell start ringing");
                alarmBtn.setImageResource(R.drawable.alarm_bell_white);
            } else {
                String sendtxt = "4";
                btt1.write(sendtxt.getBytes());
                forAlarm = false;
                textView.setText("Bell stop ringing");
                alarmBtn.setImageResource(R.drawable.alarm_bell_logo);
            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void onSelectLightBtn() {
        count = 1; st = 0; var = 0;
        Def_LightBtn();
        Log.i("[BLUETOOTH]", "Attempting to send data");
        if (mmSocket1.isConnected() && btt1 != null) {
            if (!forLight) {
                String sendtxt = "2";
                btt1.write(sendtxt.getBytes());
                forLight = true;
                textView.setText("Light ON");
                lightBtn.setImageResource(R.drawable.light_on2);
            } else {
                String sendtxt = "4";
                btt1.write(sendtxt.getBytes());
                forLight = false;
                textView.setText("Light OFF");
                lightBtn.setImageResource(R.drawable.light_off);
            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    void onKeyBtn() {
        Def_MusicBtn();
        keyBoard.setBackgroundColor(Color.rgb(255,255,153));
        textView.setText("Typing a message ?");
    }

    void onMusicBtn() {
        Def_AlarmBtn();
        musicBtn.setBackgroundColor(Color.rgb(255,255,153));
        if (!forMusic){
            textView.setText("Play Music ?");
        }
        else{
            textView.setText("Stop the Music ?");
        }
    }

    void onAlarmBtn() {
        Def_LightBtn();
        alarmBtn.setBackgroundColor(Color.rgb(255,255,153));
        if (!forAlarm){
            textView.setText("Ring the Bell ?");
        }
        else{
            textView.setText("Turning bell OFF ?");
        }
    }

    void onLightBtn(){
                Def_KeyBtn();
                lightBtn.setBackgroundColor(Color.rgb(255,255,153));
                if(!forLight){
                    textView.setText("Turning light ON ?");
                }else {
                    textView.setText("Turning light OFF ?");
                }
            }

    public void stopPlayer(){
            if (mediaPlayer != null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Toast.makeText(this,"Music stopped",Toast.LENGTH_SHORT).show();
                }

        }

    public void playMusic(View view) {
        if (!forMusic){
            if (mediaPlayer == null){
                forMusic = true;
                mediaPlayer = MediaPlayer.create(this,R.raw.emotional);
                textView.setText("Playing music..");
                musicBtn.setImageResource(R.drawable.music_playing);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopPlayer();
                    }
                });
            }
            mediaPlayer.start();
        }else{
            stopPlayer();
            textView.setText("Music stopped");
            musicBtn.setImageResource(R.drawable.music_logo);
            forMusic = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopPlayer();
        try{
            mmSocket1.close();
            btt1.cancel();
        }catch(IOException e){
            Log.e("[ERR-STOP]",""+e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
        try{
            mmSocket1.close();
            btt1.cancel();
        }catch(IOException e){
            Log.e("[ERR-STOP]",""+e);
        }
    }
}






//        final Intent intent = new Intent(this,MainActivity.class);
//        keyBoard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(intent);
//            }
//        });