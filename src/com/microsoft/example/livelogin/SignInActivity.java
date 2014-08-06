package com.microsoft.example.livelogin;

import java.util.Arrays;

import com.microsoft.live.LiveAuthClient;
import com.microsoft.live.LiveAuthException;
import com.microsoft.live.LiveAuthListener;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.live.LiveStatus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class SignInActivity extends Activity {

	private LiveAuthClient mAuthClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in_activity);
		
		mAuthClient = new LiveAuthClient(this, "0000000048122D4E");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mAuthClient.initialize(Arrays.asList(new String[]{"wl.signin"}), new LiveAuthListener() {
			
			@Override
			public void onAuthError(final LiveAuthException exception, final Object userState) {
				Toast.makeText(SignInActivity.this, "Auth Error! " + exception.getMessage(), Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onAuthComplete(final LiveStatus status, final LiveConnectSession session,
					final Object userState) {
				Toast.makeText(SignInActivity.this, "Auth Completed! " + status.name(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
