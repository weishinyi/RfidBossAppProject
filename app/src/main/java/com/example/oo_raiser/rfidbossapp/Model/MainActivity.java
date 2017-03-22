package com.example.oo_raiser.rfidbossapp.Model;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.oo_raiser.rfidbossapp.Helper.Util;
import com.example.oo_raiser.rfidbossapp.R;

public class MainActivity extends AppCompatActivity {
    private String TAG = Util.APPTAG;

    Handler hander;
    TextView tv;

    int count = 0;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //do something in background
            try{
                tv.setText(Integer.toString(count+1));
                count++;
                hander.postDelayed(runnable,1000);

            }catch(Exception e){
                Log.e(TAG,"[MainActivity_onCreate]: "+e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);
        tv.setText("Hello!");
        hander = new Handler();
        hander.post(runnable);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if(hander!=null)
        {
            hander.removeCallbacks(runnable);
        }
    }
}
