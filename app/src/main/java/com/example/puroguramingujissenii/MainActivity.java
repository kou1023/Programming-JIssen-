package com.example.puroguramingujissenii;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this)); // レイアウトXMLではなくこのViewを使う
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this, "アプリを終了しました", Toast.LENGTH_SHORT).show();
        finish();
    }

}