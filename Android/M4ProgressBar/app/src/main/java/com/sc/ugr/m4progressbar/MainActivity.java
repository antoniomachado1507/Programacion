package com.sc.ugr.m4progressbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    ProgressThread progThread;
    ProgressDialog progDialog;
    Button button1, button2;
    int typeBar;                        // Determines type progress bar: 0 = spinner, 1 = horizontal
    int delay = 40;                   // Milliseconds of delay in the update loop
    int maxBarValue = 200;      // Maximum value of horizontal progress bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();

    }
    protected void iniUI(){

        // Process button to start spinner progress dialog with anonymous inner class
        button1 = (Button) findViewById(R.id.Button01);
        button1.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                typeBar = 0;
                crearDialog(typeBar).show();
            }
        });

        // Process button to start horizontal progress bar dialog with anonymous inner class
        button2 = (Button) findViewById(R.id.Button02);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                typeBar = 1;
                crearDialog(typeBar).show();
            }
        });
    }
    protected Dialog crearDialog(int id) {
        switch(id) {
            case 0:                      // Spinner
                progDialog = new ProgressDialog(this);
                progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progDialog.setMessage("Cargando");
                progDialog.setCancelable(false);
                //Hace que cuando se ejecute la barra no se pueda cancelar hasta que termine
                // de completarse

                progThread = new ProgressThread(handler);
                progThread.start();
                return progDialog;
            case 1:                      // Horizontal
                progDialog = new ProgressDialog(this);
                progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progDialog.setMax(maxBarValue);
                progDialog.setCancelable(false);
                progDialog.setMessage("Cargando el comportamiento:");
                progThread = new ProgressThread(handler);
                progThread.start();
                return progDialog;
            default:
                return null;
        }
    }


    // Handler on the main (UI) thread that will receive messages from the
    // second thread and update the progress.

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // Get the current value of the variable total from the message data
            // and update the progress bar.
            int total = msg.getData().getInt("total");
            progDialog.setProgress(total);

            if (total >= maxBarValue){

                progDialog.dismiss();

                progThread.setState(ProgressThread.DONE);
            }
        }
    };

    // Inner class that performs progress calculations on a second thread.  Implement
    // the thread by subclassing Thread and overriding its run() method.  Also provide
    // a setState(state) method to stop the thread gracefully.

    private class ProgressThread extends Thread {

        // Class constants defining state of the thread
        final static int DONE = 0;
        final static int RUNNING = 1;

        Handler mHandler;
        int mState;
        int total;

        // Constructor with an argument that specifies Handler on main thread
        // to which messages will be sent by this thread.

        ProgressThread(Handler h) {
            mHandler = h;
        }

        // Override the run() method that will be invoked automatically when
        // the Thread starts.  Do the work required to update the progress bar on this
        // thread but send a message to the Handler on the main UI thread to actually
        // change the visual representation of the progress. In this example we count
        // the index total down to zero, so the horizontal progress bar will start full and
        // count down.

        @Override
        public void run() {
            mState = RUNNING;
            total = 0;
            while (mState == RUNNING) {
                // The method Thread.sleep throws an InterruptedException if Thread.interrupt()
                // were to be issued while thread is sleeping; the exception must be caught.
                try {
                    // Control speed of update (but precision of delay not guaranteed)
                    Thread.sleep(delay);
                } catch (InterruptedException e) {

                }

                // Send message (with current value of  total as data) to Handler on UI thread
                // so that it can update the progress bar.

                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);

                total++;      // Count down
            }
        }

        // Set current state of thread (use state=ProgressThread.DONE to stop thread)
        public void setState(int state) {
            mState = state;
        }


    }
}
