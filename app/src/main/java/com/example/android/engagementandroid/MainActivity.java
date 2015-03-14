package com.example.android.engagementandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.android.engagementandroid.data.ReplyDto;
import com.google.gson.Gson;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide both the navigation bar and the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
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

    public void sendReply(View v){
        Log.d(LOG_TAG, "call send reply");

        ReplyDto replyDto = new ReplyDto();
        replyDto.setQuestionId(1);
        replyDto.setAnswerId(1);
        replyDto.setCreated(new Date().getTime());

        RequestTask requestTask = new RequestTask();
        requestTask.execute("http://192.168.1.126:8080/engagement-services/rest/persistreply", replyDto);
    }

    class RequestTask extends AsyncTask<Object, String, String> {

        @Override
        protected String doInBackground(Object... objects) {
            Log.d(LOG_TAG, "call RequestTask.doInBackground");

            String httpResponse = null;
            String path = (String)objects[0];
            ReplyDto reply = (ReplyDto)objects[1];

            try {
                //instantiates httpclient to make request
                DefaultHttpClient httpclient = new DefaultHttpClient();

                //url with the post data
                HttpPost httpPost = new HttpPost(path);

                //convert parameters into JSON object
                Gson gson = new Gson();

                // and returned as JSON formatted string
                String json = gson.toJson(reply);

                StringEntity entity = new StringEntity(json);

                //sets the post request as the resulting string
                httpPost.setEntity(entity);
                //sets a request header so the page receiving the request
                //will know what to do with it
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                //Handles what is returned from the page
                ResponseHandler responseHandler = new BasicResponseHandler();
                httpResponse = (String) httpclient.execute(httpPost, responseHandler);
            }
            catch (Exception e) {
                Log.e(LOG_TAG, null, e);
                //TODO Handle problems..
            }

            return httpResponse;
        }
    }
}
