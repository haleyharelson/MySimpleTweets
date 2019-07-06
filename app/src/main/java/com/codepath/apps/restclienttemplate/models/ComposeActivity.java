package com.codepath.apps.restclienttemplate.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wafflecopter.charcounttextview.CharCountTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client = TwitterApplication.getRestClient(ComposeActivity.this);
    TimelineActivity reference = new TimelineActivity(); //TODO I added this to use the showProgressBar() and hideProgressBar() methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        CharCountTextView charCountTextView = (CharCountTextView) findViewById(R.id.tvTextCounter);
        EditText editText = (EditText) findViewById(R.id.editText);

        charCountTextView.setEditText(editText);
        charCountTextView.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int i, boolean b) {
            }
        });
    }

//    EditText simpleEditText = (EditText) findViewById(R.id.editTextButton);
////    String strValue = simpleEditText.getText().toString();

    // ActivityNamePrompt.java -- launched for a result
    //This will launch the subactivity, and when the subactivity is complete then it can return the result to the parent
    public void onSubmit(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        client.sendTweet(editText.getText().toString(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    reference.showProgressBar(); //TODO added this
                    Tweet tweet = Tweet.fromJSON(response);
                    // Prepare data intent
                    Intent intent = new Intent();
                    // Pass relevant data back as a result
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    intent.putExtra("code", 200); // ints work too
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, intent); // set result code and bundle data for response
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish(); // closes the activity, pass data to parent
                reference.showProgressBar(); //TODO added this
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
