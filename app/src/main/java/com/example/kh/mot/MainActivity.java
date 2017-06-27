package com.example.kh.mot;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kh.mot.Service.MyService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private Intent intentService ;
    private boolean isBound;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intentService = new Intent(this, MyService.class);
    }



    @OnClick(R.id.btnStartService)
    public void btnStartService(){
        startService(intentService);
    }
    @OnClick(R.id.btnStopService)
    public void btnStopService(){
        stopService(intentService);
    }
    @OnClick(R.id.btnBind)
    public void btnBind(){
        if (serviceConnection==null)
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    isBound=true;

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isBound=false;
                }
            };
        bindService(intentService,serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @OnClick(R.id.btnUnbind)
    public void btnUnbind(){
       if(isBound){
           unbindService(serviceConnection);
           isBound=false;
       }
    }
}
