package com.aparesh01.blinktalk;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import static com.aparesh01.blinktalk.MainActivity2.bta1;
import static com.aparesh01.blinktalk.MainActivity2.mmDevice1;

public class MainActivity extends Activity {

    public final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public final static String MODULE_MAC = "98:D3:71:F5:D5:24";
    public final static int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bta;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    TextToSpeech textToSpeech;
    public Handler mHandler;
    ConnectedBtThread btt = null;
    TextView tv1,tv2;
    Button start_btn,c1_btn,c2_btn,c3_btn,c4_btn,c5_btn,c6_btn,r1_btn,r2_btn,r3_btn,r4_btn,r5_btn,r6_btn,A_btn,B_btn,C_btn,D_btn,E_btn,F_btn,G_btn,H_btn,done_btn,cancel_btn,space_btn,comma_btn,dot_btn,delete_btn;
    Button I_btn,J_btn,K_btn,L_btn,M_btn,N_btn,O_btn,P_btn,Q_btn,R_btn,S_btn,T_btn,U_btn,V_btn,W_btn,X_btn,Y_btn,Z_btn,zero_btn,one_btn,two_btn,three_btn,four_btn,five_btn,six_btn,seven_btn,eight_btn,nine_btn;
    String strfortv1 = "";
    int count = 1,var = 0,vertical = 0,columnValue = 0,st = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        start_btn = findViewById(R.id.start_btn);
        c1_btn = findViewById(R.id.column1);
        c2_btn = findViewById(R.id.column2);
        c3_btn = findViewById(R.id.column3);
        c4_btn = findViewById(R.id.column4);
        c5_btn = findViewById(R.id.column5);
        c6_btn = findViewById(R.id.column6);
        r1_btn = findViewById(R.id.row1);
        r2_btn = findViewById(R.id.row2);
        r3_btn = findViewById(R.id.row3);
        r4_btn = findViewById(R.id.row4);
        r5_btn = findViewById(R.id.row5);
        r6_btn = findViewById(R.id.row6);
        A_btn = findViewById(R.id.a_btn);
        B_btn = findViewById(R.id.b_btn);
        C_btn = findViewById(R.id.c_btn);
        D_btn = findViewById(R.id.d_btn);
        E_btn = findViewById(R.id.e_btn);
        F_btn = findViewById(R.id.f_btn);
        G_btn = findViewById(R.id.g_btn);
        H_btn = findViewById(R.id.h_btn);
        I_btn = findViewById(R.id.i_btn);
        J_btn = findViewById(R.id.j_btn);
        K_btn = findViewById(R.id.k_btn);
        L_btn = findViewById(R.id.l_btn);
        M_btn = findViewById(R.id.m_btn);
        N_btn = findViewById(R.id.n_btn);
        O_btn = findViewById(R.id.o_btn);
        P_btn = findViewById(R.id.p_btn);
        Q_btn = findViewById(R.id.q_btn);
        R_btn = findViewById(R.id.r_btn);
        S_btn = findViewById(R.id.s_btn);
        T_btn = findViewById(R.id.t_btn);
        U_btn = findViewById(R.id.u_btn);
        V_btn = findViewById(R.id.v_btn);
        W_btn = findViewById(R.id.w_btn);
        X_btn = findViewById(R.id.x_btn);
        Y_btn = findViewById(R.id.y_btn);
        Z_btn = findViewById(R.id.z_btn);
        zero_btn = findViewById(R.id.zero_btn);
        one_btn = findViewById(R.id.one_btn);
        two_btn = findViewById(R.id.two_btn);
        three_btn = findViewById(R.id.three_btn);
        four_btn = findViewById(R.id.four_btn);
        five_btn = findViewById(R.id.five_btn);
        six_btn = findViewById(R.id.six_btn);
        seven_btn = findViewById(R.id.seven_btn);
        eight_btn = findViewById(R.id.eight_btn);
        nine_btn = findViewById(R.id.nine_btn);
        done_btn = findViewById(R.id.done_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        space_btn = findViewById(R.id.space_btn);
        comma_btn = findViewById(R.id.comma_btn);
        dot_btn = findViewById(R.id.stop_btn);
        delete_btn = findViewById(R.id.delete_btn);

        initiateBluetoothProcess();

//        bta = BluetoothAdapter.getDefaultAdapter();
//        if(!bta.isEnabled()){
//            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBt,REQUEST_ENABLE_BT);
//        }else{
//            initiateBluetoothProcess();
//        }

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int tts = textToSpeech.setLanguage(Locale.US);
                    if (tts == TextToSpeech.LANG_MISSING_DATA || tts == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("[TTS]","The language is not supported");
                    }else{
                        Log.i("[TTS]","The language is supported");
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Text To Speech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initialize();

    }

    public void initialize(){

        count = 1;var = 0; vertical = 0 ; columnValue = 0; st = 0;

        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        c1_btn.setBackgroundResource(android.R.drawable.btn_default);
        c2_btn.setBackgroundResource(android.R.drawable.btn_default);
        c3_btn.setBackgroundResource(android.R.drawable.btn_default);
        c4_btn.setBackgroundResource(android.R.drawable.btn_default);
        c5_btn.setBackgroundResource(android.R.drawable.btn_default);
        c6_btn.setBackgroundResource(android.R.drawable.btn_default);
        r1_btn.setBackgroundResource(android.R.drawable.btn_default);
        r2_btn.setBackgroundResource(android.R.drawable.btn_default);
        r3_btn.setBackgroundResource(android.R.drawable.btn_default);
        r4_btn.setBackgroundResource(android.R.drawable.btn_default);
        r5_btn.setBackgroundResource(android.R.drawable.btn_default);
        r6_btn.setBackgroundResource(android.R.drawable.btn_default);
        A_btn.setBackgroundResource(android.R.drawable.btn_default);
        B_btn.setBackgroundResource(android.R.drawable.btn_default);
        C_btn.setBackgroundResource(android.R.drawable.btn_default);
        D_btn.setBackgroundResource(android.R.drawable.btn_default);
        E_btn.setBackgroundResource(android.R.drawable.btn_default);
        F_btn.setBackgroundResource(android.R.drawable.btn_default);
        G_btn.setBackgroundResource(android.R.drawable.btn_default);
        H_btn.setBackgroundResource(android.R.drawable.btn_default);
        I_btn.setBackgroundResource(android.R.drawable.btn_default);
        J_btn.setBackgroundResource(android.R.drawable.btn_default);
        K_btn.setBackgroundResource(android.R.drawable.btn_default);
        L_btn.setBackgroundResource(android.R.drawable.btn_default);
        M_btn.setBackgroundResource(android.R.drawable.btn_default);
        N_btn.setBackgroundResource(android.R.drawable.btn_default);
        O_btn.setBackgroundResource(android.R.drawable.btn_default);
        P_btn.setBackgroundResource(android.R.drawable.btn_default);
        Q_btn.setBackgroundResource(android.R.drawable.btn_default);
        R_btn.setBackgroundResource(android.R.drawable.btn_default);
        S_btn.setBackgroundResource(android.R.drawable.btn_default);
        T_btn.setBackgroundResource(android.R.drawable.btn_default);
        U_btn.setBackgroundResource(android.R.drawable.btn_default);
        V_btn.setBackgroundResource(android.R.drawable.btn_default);
        W_btn.setBackgroundResource(android.R.drawable.btn_default);
        X_btn.setBackgroundResource(android.R.drawable.btn_default);
        Y_btn.setBackgroundResource(android.R.drawable.btn_default);
        Z_btn.setBackgroundResource(android.R.drawable.btn_default);
        zero_btn.setBackgroundResource(android.R.drawable.btn_default);
        one_btn.setBackgroundResource(android.R.drawable.btn_default);
        two_btn.setBackgroundResource(android.R.drawable.btn_default);
        three_btn.setBackgroundResource(android.R.drawable.btn_default);
        four_btn.setBackgroundResource(android.R.drawable.btn_default);
        five_btn.setBackgroundResource(android.R.drawable.btn_default);
        six_btn.setBackgroundResource(android.R.drawable.btn_default);
        seven_btn.setBackgroundResource(android.R.drawable.btn_default);
        eight_btn.setBackgroundResource(android.R.drawable.btn_default);
        nine_btn.setBackgroundResource(android.R.drawable.btn_default);
        done_btn.setBackgroundResource(android.R.drawable.btn_default);
        cancel_btn.setBackgroundResource(android.R.drawable.btn_default);
        space_btn.setBackgroundResource(android.R.drawable.btn_default);
        comma_btn.setBackgroundResource(android.R.drawable.btn_default);
        dot_btn.setBackgroundResource(android.R.drawable.btn_default);
        delete_btn.setBackgroundResource(android.R.drawable.btn_default);
    }


    public void onstart(){
        start_btn.setBackgroundColor(Color.rgb(135,185,255));
        if (vertical == 0){
            onC1();
        }else{
            onR1();
        }

    }

    public void onC1(){
        tv2.setText("");
        c6ColorDef();
        c1_btn.setBackgroundColor(Color.rgb(255,180,255));
        A_btn.setBackgroundColor(Color.rgb(255,255,170));
        G_btn.setBackgroundColor(Color.rgb(255,255,170));
        M_btn.setBackgroundColor(Color.rgb(255,255,170));
        S_btn.setBackgroundColor(Color.rgb(255,255,170));
        Y_btn.setBackgroundColor(Color.rgb(255,255,170));
        four_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[A], [G], [M], [S], [Y], [4]");
    }

    public void onC2(){
        tv2.setText("");
        c1ColorDef();
        c2_btn.setBackgroundColor(Color.rgb(255,180,255));
        B_btn.setBackgroundColor(Color.rgb(255,255,170));
        H_btn.setBackgroundColor(Color.rgb(255,255,170));
        N_btn.setBackgroundColor(Color.rgb(255,255,170));
        T_btn.setBackgroundColor(Color.rgb(255,255,170));
        Z_btn.setBackgroundColor(Color.rgb(255,255,170));
        five_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[B], [H], [N], [T], [Z], [5]");
    }
    public void onC3(){
        tv2.setText("");
        c2ColorDef();
        c3_btn.setBackgroundColor(Color.rgb(255,180,255));
        C_btn.setBackgroundColor(Color.rgb(255,255,170));
        I_btn.setBackgroundColor(Color.rgb(255,255,170));
        O_btn.setBackgroundColor(Color.rgb(255,255,170));
        U_btn.setBackgroundColor(Color.rgb(255,255,170));
        zero_btn.setBackgroundColor(Color.rgb(255,255,170));
        six_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[C], [I], [O], [U], [0], [6]");
    }
    public void onC4(){
        tv2.setText("");
        c3ColorDef();
        c4_btn.setBackgroundColor(Color.rgb(255,180,255));
        D_btn.setBackgroundColor(Color.rgb(255,255,170));
        J_btn.setBackgroundColor(Color.rgb(255,255,170));
        P_btn.setBackgroundColor(Color.rgb(255,255,170));
        V_btn.setBackgroundColor(Color.rgb(255,255,170));
        one_btn.setBackgroundColor(Color.rgb(255,255,170));
        seven_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[D], [J], [P], [V], [1], [7]");
    }
    public void onC5(){
        tv2.setText("");
        c4ColorDef();
        c5_btn.setBackgroundColor(Color.rgb(255,180,255));
        E_btn.setBackgroundColor(Color.rgb(255,255,170));
        K_btn.setBackgroundColor(Color.rgb(255,255,170));
        Q_btn.setBackgroundColor(Color.rgb(255,255,170));
        W_btn.setBackgroundColor(Color.rgb(255,255,170));
        two_btn.setBackgroundColor(Color.rgb(255,255,170));
        eight_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[E], [K], [Q], [W], [2], [8]");
    }
    public void onC6(){
        tv2.setText("");
        c5ColorDef();
        c6_btn.setBackgroundColor(Color.rgb(255,180,255));
        F_btn.setBackgroundColor(Color.rgb(255,255,170));
        L_btn.setBackgroundColor(Color.rgb(255,255,170));
        R_btn.setBackgroundColor(Color.rgb(255,255,170));
        X_btn.setBackgroundColor(Color.rgb(255,255,170));
        three_btn.setBackgroundColor(Color.rgb(255,255,170));
        nine_btn.setBackgroundColor(Color.rgb(255,255,170));
        tv2.setText("[F], [L], [R], [X], [3], [9]");
    }

    public void onSelectC1(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 1;
    }
    public void onSelectC2(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 2;
    }
    public void onSelectC3(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 3;
    }
    public void onSelectC4(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 4;
    }
    public void onSelectC5(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 5;
    }
    public void onSelectC6(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;
        st = 0;
        var = 0;
        vertical = 1;
        columnValue = 6;
    }

    public void onR1(){
        delete_btn.setBackgroundResource(android.R.drawable.btn_default);
        r1_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            A_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[A]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            B_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[B]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            C_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[C]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            D_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[D]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            E_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[E]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            F_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[F]");
        }
    }

    public void onR2(){
        r1_btn.setBackgroundResource(android.R.drawable.btn_default);
        r2_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            G_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[G]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            H_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[H]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            I_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[I]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            J_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[J]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            K_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[K]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            L_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[L]");
        }
    }
    public void onR3(){
        r2_btn.setBackgroundResource(android.R.drawable.btn_default);
        r3_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            M_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[M]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            N_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[N]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            O_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[O]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            P_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[P]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            Q_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[Q]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            R_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[R]");
        }
    }
    public void onR4(){
        r3_btn.setBackgroundResource(android.R.drawable.btn_default);
        r4_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            S_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[S]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            T_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[T]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            U_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[U]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            V_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[V]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            W_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[W]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            X_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[X]");
        }
    }
    public void onR5(){
        r4_btn.setBackgroundResource(android.R.drawable.btn_default);
        r5_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            Y_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[Y]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            Z_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[Z]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            zero_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[0]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            one_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[1]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            two_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[2]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            three_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[3]");
        }
    }
    public void onR6(){
        r5_btn.setBackgroundResource(android.R.drawable.btn_default);
        r6_btn.setBackgroundColor(Color.rgb(200,160,255));
        if (columnValue == 1){
            c1ColorDef();
            four_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[4]");
        }
        else if (columnValue == 2){
            c2ColorDef();
            five_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[5]");
        }
        else if (columnValue == 3){
            c3ColorDef();
            six_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[6]");
        }
        else if (columnValue == 4){
            c4ColorDef();
            seven_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[7]");
        }
        else if (columnValue == 5){
            c5ColorDef();
            eight_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[8]");
        }
        else if (columnValue == 6){
            c6ColorDef();
            nine_btn.setBackgroundColor(Color.rgb(153,255,153));
            tv2.setText("[9]");
        }
    }

    public void onDone(){
        r6_btn.setBackgroundResource(android.R.drawable.btn_default);
        four_btn.setBackgroundResource(android.R.drawable.btn_default);
        five_btn.setBackgroundResource(android.R.drawable.btn_default);
        six_btn.setBackgroundResource(android.R.drawable.btn_default);
        seven_btn.setBackgroundResource(android.R.drawable.btn_default);
        eight_btn.setBackgroundResource(android.R.drawable.btn_default);
        nine_btn.setBackgroundResource(android.R.drawable.btn_default);
        done_btn.setBackgroundColor(Color.rgb(153,255,153));
    }
    public void onCancel(){
        done_btn.setBackgroundResource(android.R.drawable.btn_default);
        cancel_btn.setBackgroundColor(Color.rgb(153,255,153));
    }
    public void onSpace(){
        cancel_btn.setBackgroundResource(android.R.drawable.btn_default);
        space_btn.setBackgroundColor(Color.rgb(153,255,153));
    }
    public void onComma(){
        space_btn.setBackgroundResource(android.R.drawable.btn_default);
        comma_btn.setBackgroundColor(Color.rgb(153,255,153));
    }
    public void onDot(){
        comma_btn.setBackgroundResource(android.R.drawable.btn_default);
        dot_btn.setBackgroundColor(Color.rgb(153,255,153));
    }
    public void onDelete(){
        dot_btn.setBackgroundResource(android.R.drawable.btn_default);
        delete_btn.setBackgroundColor(Color.rgb(153,255,153));
    }

    public void onSelectR1(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r1_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                A_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="A";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                B_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="B";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                C_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="C";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                D_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "D";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                E_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "E";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                F_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "F";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectR2(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r2_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                G_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="G";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                H_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="H";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                I_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="I";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                J_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "J";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                K_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "K";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                L_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "L";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectR3(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r3_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                M_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="M";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                N_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="N";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                O_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="O";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                P_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "P";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                Q_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "Q";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                R_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "R";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectR4(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r4_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                S_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="S";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                T_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="T";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                U_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="U";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                V_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "V";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                W_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "W";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                X_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "X";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectR5(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r5_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                Y_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="Y";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                Z_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="Z";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                zero_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="0";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                one_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "1";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                two_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "2";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                three_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "3";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectR6(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        r6_btn.setBackgroundResource(android.R.drawable.btn_default);
        count = 1;vertical = 0; var = 0; st = 0;
        switch(columnValue){
            case 1:
                four_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="4";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 2:
                five_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="5";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 3:
                six_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1+="6";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 4:
                seven_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "7";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 5:
                eight_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "8";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            case 6:
                nine_btn.setBackgroundResource(android.R.drawable.btn_default);
                strfortv1 += "9";
                tv1.setText(strfortv1);
                tv2.setText("");
                columnValue = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 3011", Toast.LENGTH_SHORT).show();
                columnValue = 0;
        }
    }

    public void onSelectDone(){
        if(strfortv1.length()<1){
            Toast.makeText(this, "You don't select any letters or numbers", Toast.LENGTH_SHORT).show();
        }else {
            start_btn.setBackgroundResource(android.R.drawable.btn_default);
            done_btn.setBackgroundResource(android.R.drawable.btn_default);
            int speechStatus = textToSpeech.speak(tv1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            if (speechStatus == TextToSpeech.ERROR) {
                Toast.makeText(this, "ERROR CODE : 5431", Toast.LENGTH_SHORT).show();
            }
        }
        strfortv1 = "";
        count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
    }

    public void onselectCancel(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        cancel_btn.setBackgroundResource(android.R.drawable.btn_default);
        switch (columnValue){
            case 1:
                c1ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 2:
                c2ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 3:
                c3ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 4:
                c4ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 5 :
                c5ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 6:
                c6ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 1155", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSelectSpace(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        space_btn.setBackgroundResource(android.R.drawable.btn_default);
        strfortv1+= " ";
        tv1.setText(strfortv1);
        count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
    }

    public void onSelectComma(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        comma_btn.setBackgroundResource(android.R.drawable.btn_default);
        strfortv1 += ", ";
        tv1.setText(strfortv1);
        count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
    }

    public void onSelectDot(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        dot_btn.setBackgroundResource(android.R.drawable.btn_default);
        strfortv1 += ". ";
        tv1.setText(strfortv1);
        count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
    }
    public void onSelectDelete(){
        start_btn.setBackgroundResource(android.R.drawable.btn_default);
        delete_btn.setBackgroundResource(android.R.drawable.btn_default);
        if(strfortv1.length()<1){
            strfortv1 ="";
            tv1.setText(strfortv1);
        }else{
            strfortv1 = strfortv1.substring(0,strfortv1.length()-1);
            tv1.setText(strfortv1);
        }
        count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
    }


    public void c1ColorDef(){
        c1_btn.setBackgroundResource(android.R.drawable.btn_default);
        A_btn.setBackgroundResource(android.R.drawable.btn_default);
        G_btn.setBackgroundResource(android.R.drawable.btn_default);
        M_btn.setBackgroundResource(android.R.drawable.btn_default);
        S_btn.setBackgroundResource(android.R.drawable.btn_default);
        Y_btn.setBackgroundResource(android.R.drawable.btn_default);
        four_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void c2ColorDef(){
        c2_btn.setBackgroundResource(android.R.drawable.btn_default);
        B_btn.setBackgroundResource(android.R.drawable.btn_default);
        H_btn.setBackgroundResource(android.R.drawable.btn_default);
        N_btn.setBackgroundResource(android.R.drawable.btn_default);
        T_btn.setBackgroundResource(android.R.drawable.btn_default);
        Z_btn.setBackgroundResource(android.R.drawable.btn_default);
        five_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void c3ColorDef(){
        c3_btn.setBackgroundResource(android.R.drawable.btn_default);
        C_btn.setBackgroundResource(android.R.drawable.btn_default);
        I_btn.setBackgroundResource(android.R.drawable.btn_default);
        O_btn.setBackgroundResource(android.R.drawable.btn_default);
        U_btn.setBackgroundResource(android.R.drawable.btn_default);
        zero_btn.setBackgroundResource(android.R.drawable.btn_default);
        six_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void c4ColorDef(){
        c4_btn.setBackgroundResource(android.R.drawable.btn_default);
        D_btn.setBackgroundResource(android.R.drawable.btn_default);
        J_btn.setBackgroundResource(android.R.drawable.btn_default);
        P_btn.setBackgroundResource(android.R.drawable.btn_default);
        V_btn.setBackgroundResource(android.R.drawable.btn_default);
        one_btn.setBackgroundResource(android.R.drawable.btn_default);
        seven_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void c5ColorDef(){
        c5_btn.setBackgroundResource(android.R.drawable.btn_default);
        E_btn.setBackgroundResource(android.R.drawable.btn_default);
        K_btn.setBackgroundResource(android.R.drawable.btn_default);
        Q_btn.setBackgroundResource(android.R.drawable.btn_default);
        W_btn.setBackgroundResource(android.R.drawable.btn_default);
        two_btn.setBackgroundResource(android.R.drawable.btn_default);
        eight_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void c6ColorDef(){
        c6_btn.setBackgroundResource(android.R.drawable.btn_default);
        F_btn.setBackgroundResource(android.R.drawable.btn_default);
        L_btn.setBackgroundResource(android.R.drawable.btn_default);
        R_btn.setBackgroundResource(android.R.drawable.btn_default);
        X_btn.setBackgroundResource(android.R.drawable.btn_default);
        three_btn.setBackgroundResource(android.R.drawable.btn_default);
        nine_btn.setBackgroundResource(android.R.drawable.btn_default);
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT){
            initiateBluetoothProcess();
        }
    }*/

    public void initiateBluetoothProcess() {

        if(bta1.isEnabled()){
            BluetoothSocket tmp;
            //mmDevice = bta1.getRemoteDevice(MODULE_MAC);
            try{
                tmp = mmDevice1.createRfcommSocketToServiceRecord(MY_UUID);
                mmSocket = tmp;
                mmSocket.connect();
                Toast.makeText(this, "Bluetooth is Connected", Toast.LENGTH_SHORT).show();
            }catch(IOException e){
                try{
                    mmSocket.close();
                }catch (IOException e2){
                    return;
                }
            }

            mHandler = new Handler(Looper.getMainLooper()){
                public void handleMessage(Message msg){
                    if (msg.what == ConnectedBtThread.RESPONSE_MESSAGE){
                        String txt = (String) msg.obj;
                        count = Integer.parseInt(txt);
                        if(count  == 0 && st ==0){
                            st = 1;
                            var = 0;
                            onstart();
                        }
                        else if (count == 1 && st == 1){
                            if (vertical == 0) {
                                if (var < 6) {
                                    var++;
                                    if (var == 1) {
                                        onC2();
                                    } else if (var == 2) {
                                        onC3();
                                    } else if (var == 3) {
                                        onC4();
                                    } else if (var == 4) {
                                        onC5();
                                    } else if (var == 5) {
                                        onC6();
                                    }
                                }
                                else {
                                    var = 0;
                                    onC1();
                                }
                            }
                            else if (vertical == 1){
                                if (var<12){
                                    var++;
                                    if (var == 1){
                                        onR2();
                                    }else if (var == 2){
                                        onR3();
                                    }else if (var == 3){
                                        onR4();
                                    }else if (var == 4){
                                        onR5();
                                    }else if (var == 5){
                                        onR6();
                                    }else if (var == 6){
                                        onDone();
                                    }else if (var == 7){
                                        onCancel();
                                    }else if (var == 8){
                                        onSpace();
                                    }else if (var == 9){
                                        onComma();
                                    }else if (var == 10){
                                        onDot();
                                    }else if (var == 11){
                                        onDelete();
                                    }
                                }else{
                                    var =0;
                                    onR1();
                                }
                            }
                        }
                        //here the color changing methods will be called
                        else if (count ==0 && st ==1){
                            if (vertical == 0){
                                switch(var){
                                    case 0:
                                        onSelectC1();
                                        break;
                                    case 1:
                                        onSelectC2();
                                        break;
                                    case 2:
                                        onSelectC3();
                                        break;
                                    case 3:
                                        onSelectC4();
                                        break;
                                    case 4:
                                        onSelectC5();
                                        break;
                                    case 5:
                                        onSelectC6();
                                        break;
                                }
                            }else{
                                switch(var){
                                    case 0:
                                        onSelectR1();
                                        break;
                                    case 1:
                                        onSelectR2();
                                        break;
                                    case 2:
                                        onSelectR3();
                                        break;
                                    case 3:
                                        onSelectR4();
                                        break;
                                    case 4:
                                        onSelectR5();
                                        break;
                                    case 5:
                                        onSelectR6();
                                        break;
                                    case 6:
                                        onSelectDone();
                                        break;
                                    case 7:
                                        onselectCancel();
                                        break;
                                    case 8:
                                        onSelectSpace();
                                        break;
                                    case 9:
                                        onSelectComma();
                                        break;
                                    case 10:
                                        onSelectDot();
                                        break;
                                    case 11:
                                        onSelectDelete();
                                        break;
                                }
                            }
                        }
                    }

                }
            };
            Log.i("[BLUETOOTH]","Creating and Running Thread");
            btt = new ConnectedBtThread(mmSocket,mHandler);
            btt.start();
        }
    }

    public void onClickDoneBtn(View view) {
        int speechStatus = textToSpeech.speak(tv1.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
        if(speechStatus == TextToSpeech.ERROR){
            Toast.makeText(this, "ERROR CODE : 5431", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{mmSocket.close();}catch(IOException e){e.printStackTrace();}
        if (textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public void onClickCancelBtn(View view) {
        switch (columnValue){
            case 1:
                c1ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 2:
                c2ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 3:
                c3ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 4:
                c4ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 5 :
                c5ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            case 6:
                c6ColorDef();
                count = 1;var = 0; vertical = 0; columnValue = 0; st = 0;
                break;
            default:
                Toast.makeText(this, "ERROR! CODE : 1155", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDeleteBtn(View view) {
        if(strfortv1.length()<1){
            strfortv1 ="";
            tv1.setText(strfortv1);
        }else{
            strfortv1 = strfortv1.substring(0,strfortv1.length()-1);
            tv1.setText(strfortv1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            if(btt!= null){
                btt.cancel();
            }
            if (mmSocket!= null){
                mmSocket.close();
            }
        }catch(Exception e){
            Log.i("[AC : - 1]",""+e);
        }
    }
}