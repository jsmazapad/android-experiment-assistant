package com.jsm.exptool.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jsm.exptool.R;
import com.jsm.exptool.ui.main.MainActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Thread mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(100);
                    } catch (InterruptedException e) {
                        Log.w(SplashActivity.class.getSimpleName(), e.getMessage(), e);
                        /*The throwing of the InterruptedException clears the interrupted state of the Thread, so if the exception is not handled properly
                        the information that the thread was interrupted will be lost. Instead, InterruptedExceptions should either be rethrown -
                        immediately or after cleaning up the method’s state - or the thread should be re-interrupted by calling Thread.interrupt() even
                        if this is supposed to be a single-threaded application. Any other course of action risks delaying thread shutdown and loses the
                        information that the thread was interrupted - probably without finishing its task.*/
                        Thread.currentThread().interrupt();
                    } finally {
                        SplashActivity.this.exitSplash();
                    }
                }
            }

        };
        mSplashThread.start();

    }

    private void exitSplash() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
