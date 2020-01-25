package com.example.boundservicecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    BoundService mService;
    boolean mIsServiceBound;
    Button mSumButton, mDiffButton;
    EditText firstNumber, secondNumber;
    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSumButton = findViewById(R.id.sum_button);
        mDiffButton = findViewById(R.id.diff_button);
        mResult = findViewById(R.id.text_view);
        firstNumber = findViewById(R.id.first_number_view);
        secondNumber = findViewById(R.id.second_number_view);

        mSumButton.setOnClickListener(v -> {
            if (mIsServiceBound) {
                mResult.setText(String.format(Locale.US, "Result: %d", mService.sum(getFirstNumber(), getSecondNumber())));
            }
        });

        mDiffButton.setOnClickListener(v -> {
            if (mIsServiceBound) {
                mResult.setText(String.format(Locale.US, "Result: %d", mService.diff(getFirstNumber(), getSecondNumber())));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, BoundService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsServiceBound) {
            unbindService(mConnection);
            mIsServiceBound = false;
        }
    }

    final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            mService = binder.getService();
            mIsServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsServiceBound = false;
        }
    };

    int getFirstNumber() {
        return Integer.valueOf(firstNumber.getText().toString());
    }

    int getSecondNumber() {
        return Integer.valueOf(secondNumber.getText().toString());
    }
}
