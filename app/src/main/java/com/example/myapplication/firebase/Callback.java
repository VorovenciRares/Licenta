package com.example.myapplication.firebase;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
