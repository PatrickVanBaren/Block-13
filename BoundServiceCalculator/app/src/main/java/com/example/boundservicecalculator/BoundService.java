package com.example.boundservicecalculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BoundService extends Service {

    final IBinder mBinder = new LocalBinder();

    class LocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int sum(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    public int diff(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }
}
