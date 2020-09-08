package com.loviraj.fingerprintsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.loviraj.fingerprintsample.auth.FPStatusListener;
import com.loviraj.fingerprintsample.auth.LFingerPrint;

public class MainActivity extends AppCompatActivity implements FPStatusListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LFingerPrint mLFingerPrint;
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
            }
        });

        mLFingerPrint = new LFingerPrint(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");

        if(mLFingerPrint.getManager().isFingerPrintAvailable()) {
            Log.d(TAG, "Finger print available ");

            if(mLFingerPrint.getManager().hasFingerprintRegistered()) {
                Log.d(TAG, "Finger print registered");

                mLFingerPrint.getManager().startListening();
            } else {
                Log.d(TAG, "Finger print not registered ");
            }
        } else {
            Log.d(TAG, "Finger print not available ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mLFingerPrint.getManager().stopListening();
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

    @Override
    public void fpAuthSuccess() {
        Log.d(TAG, "fpAuthSuccess() called");
    }

    @Override
    public void fpAuthFailed(String errorMessage) {

        Log.d(TAG, "fpAuthFailed() called");
    }

}
