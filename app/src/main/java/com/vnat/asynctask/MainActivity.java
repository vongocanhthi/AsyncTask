package com.vnat.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText edtButton;
    Button btnVe;
    TextView txtPhanTram;
    LinearLayout llButton;
    ButtonTask buttonTask;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
        event();
    }

    private void event() {
        btnVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(edtButton.getText().toString());
                buttonTask.execute(n);
            }
        });
    }

    private void init() {
        edtButton = findViewById(R.id.edtButton);
        btnVe = findViewById(R.id.btnVe);
        txtPhanTram = findViewById(R.id.txtPhanTram);
        llButton = findViewById(R.id.llButton);

        buttonTask = new ButtonTask();
        random = new Random();
    }

    class ButtonTask extends AsyncTask<Integer,Integer,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtPhanTram.setText("0%");
            llButton.removeAllViews();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtPhanTram.setText("100%");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int percent = values[0];
            int value = values[1];

            txtPhanTram.setText(percent + "%");
            Button btn = new Button(MainActivity.this);
            btn.setText(value + " ");
            llButton.addView(btn);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int n = integers[0];
            for (int i=0;i<n;i++){
                int percent = i*100/n;
                int value = random.nextInt(100);

                publishProgress(percent,value);
                SystemClock.sleep(100);
            }
            return null;
        }
    }
}