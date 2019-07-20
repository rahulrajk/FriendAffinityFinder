package com.example.friendaffinityfinder;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CustomProgressBarActivity extends AppCompatActivity {

    private TextView txtProgress;
    private ProgressBar progressBar,progressBar1;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progressbar);

        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int pStatus = 0;
                while (pStatus <= 0.7259218297664517*100) {
                    final int finalPStatus = pStatus;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalPStatus);
                            txtProgress.setText(finalPStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int pStatus1 = 0;
                while (pStatus1 <= 99) {
                    final int finalPStatus = pStatus1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setProgress(finalPStatus);
                            txtProgress.setText(finalPStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus1++;
                }
            }
        }).start();


    }
}
