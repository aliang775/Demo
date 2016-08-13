package com.demo.aliang.commondemo.LanchMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.aliang.commondemo.R;

/**
 * Created by zhangxiaoliang on 16-8-13.
 */
public class ActivityTwo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("kkk", "ActivityOne onCreate");

        setContentView(R.layout.activity_two);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTwo.this, ActivityThree.class);
                startActivity(intent);
            }
        });
    }
}
