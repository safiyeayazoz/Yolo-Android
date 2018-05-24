package org.tensorflow.online_shopping_demo;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by safiye ayazoz on 24.05.2018.
 */

public class firstFragment extends Fragment implements TextToSpeech.OnInitListener {

    private TextToSpeech myTTS;
    // status check code
    private int MY_DATA_CHECK_CODE = 100;

    private ProgressDialog progressDialog;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getContext(), "GELDI",
                Toast.LENGTH_LONG).show();

        Intent checkTTSIntent = new Intent();
        checkTTSIntent
                .setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        //new BackgroundTask.exute();
        Thread logoTimer = new Thread() {
            public void run() {

                    try {
                        sleep(300);
                        speakWords("APPLE");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }

        };
        logoTimer.start();
        return inflater.inflate(R.layout.first_fragment,container,false);
    }

    // speak the user text
    private void speakWords(String speech) {

        // speak straight away
        if(myTTS != null)
        {
            myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

        }
    }

    // act on result of TTS data check
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(getContext(), this);
            } else {
                // no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent
                        .setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    // setup TTS
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onInit(int initStatus) {

        // check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(getContext(), "Sorry! Text To Speech failed...",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myTTS != null) {
            myTTS.stop();
            myTTS.shutdown();
            myTTS = null;



        }
    }

 /*   private class BackgroundTask extends AsyncTask<Void, Integer,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading Speech to Text Engine");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
        }
        @Override
        protected Void doInBackground(Void... voids) {

            for (int i = 0; i < 101; i = i + 10) {
                try {
                    publishProgress(i);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer currentProgress = values[0];
            progressDialog.setProgress(currentProgress);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
            progressDialog.dismiss();
        }
    }*/
}
