package com.example.xiechunlei.javahook;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.xiechunlei.javahook.util.UnzipFromAssets;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startWrappedTopAct();
//                startWrappedWPS();

//                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        File filesDir = getFilesDir();
        File file = new File(filesDir, "test.ini");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test();

        try {
            UnzipFromAssets.unZip(this, "/sdcard/javahookLauncher.apk", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWrappedTopAct() {
        //                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.willme.topactivity",
                "com.willme.topactivity.MainActivity"));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startWrappedWPS() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.kingsoft.moffice_pro",
                "cn.wps.moffice.documentmanager.PreStartActivity"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void test() {
        ActivityManager activityManager = (ActivityManager) getSystemService("activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
