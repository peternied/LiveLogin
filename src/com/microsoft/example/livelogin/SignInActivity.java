package com.microsoft.example.livelogin;

import java.util.Arrays;
import java.util.List;

import com.microsoft.live.LiveAuthClient;
import com.microsoft.live.LiveAuthException;
import com.microsoft.live.LiveAuthListener;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.live.LiveStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignInActivity extends Activity {

	private static final List<String> SCOPES = Arrays.asList(new String[]{"wl.signin", "wl.offline_access"});
	private LiveAuthClient mAuthClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in_activity);
		
		mAuthClient = new LiveAuthClient(this, "0000000048122D4E");
		Button signInButton = (Button)findViewById(R.id.button1);

		signInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				mAuthClient.login(SignInActivity.this, SCOPES, new LiveAuthListener() {
					@Override
					public void onAuthError(final LiveAuthException exception, final Object userState) {
						Toast.makeText(SignInActivity.this, "Login: Auth Error! " + exception.getMessage(), Toast.LENGTH_SHORT).show();
						showSignIn();
					}
					
					@Override
					public void onAuthComplete(final LiveStatus status, final LiveConnectSession session,
							final Object userState) {
						Toast.makeText(SignInActivity.this, "Login: Auth Completed! " + status.name(), Toast.LENGTH_SHORT).show();
						
						if(status != LiveStatus.CONNECTED) {
							showSignIn();
						} else {
							startCoreApp();
						}
					}
				});
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mAuthClient.initialize(SCOPES, new LiveAuthListener() {
			@Override
			public void onAuthError(final LiveAuthException exception, final Object userState) {
				Toast.makeText(SignInActivity.this, "Startup: Auth Error! " + exception.getMessage(), Toast.LENGTH_SHORT).show();
				showSignIn();
			}
			
			@Override
			public void onAuthComplete(final LiveStatus status, final LiveConnectSession session,
					final Object userState) {
				Toast.makeText(SignInActivity.this, "Startup: Auth Completed! " + status.name(), Toast.LENGTH_SHORT).show();
				
				if(status != LiveStatus.CONNECTED) {
					showSignIn();
				} else {
					startCoreApp();
				}
			}
		});
	}
	
	private void showSignIn() {
		findViewById(R.id.button1).setVisibility(View.VISIBLE);
	}
	
	private void startCoreApp() {
		startActivity(new Intent(this, CoreAppFunctionalityActivity.class));
	}
}
