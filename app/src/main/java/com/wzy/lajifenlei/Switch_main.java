package com.wzy.lajifenlei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Switch_main extends AppCompatActivity
{

    private Button btn_server;
    private Button btn_client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_switch_main );
        btn_server=(Button)findViewById ( R.id.btn_server );
        btn_client=(Button)findViewById ( R.id.btn_client );

        btn_server.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                startActivity ( new Intent ( Switch_main.this,MainSocketServer.class ) );
            }
        } );
        btn_client.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                startActivity ( new Intent ( Switch_main.this,MainSocketClient.class ) );
            }
        } );

    }
}