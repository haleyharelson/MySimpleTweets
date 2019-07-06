package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.SampleModel;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	SampleModelDao sampleModelDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final SampleModel sampleModel = new SampleModel();
		sampleModel.setName("CodePath");

		sampleModelDao = ((TwitterApplication) getApplicationContext()).getMyDatabase().sampleModelDao();

		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				sampleModelDao.insertModel(sampleModel);
			}
		});
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		//Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();

		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to menu_main
	public void loginToRest(View view) {
		getClient().connect();
	}

}
