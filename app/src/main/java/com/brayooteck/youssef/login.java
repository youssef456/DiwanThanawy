package com.brayooteck.youssef;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    LoginButton loginButton;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String user_id;
 //   private StorageReference storageReference;
    String image_url;
    String name;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    //    storageReference = FirebaseStorage.getInstance().getReference();
        AppEventsLogger.activateApp(getApplication());
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                if(Profile.getCurrentProfile() == null ) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            name = currentProfile.getName();
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
                    name = profile.getName();


                }
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }









        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }


        @Override
        public void onStart () {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                updateUI(currentUser);
            }
        }

        private void updateUI (FirebaseUser currentUser){
            Toast.makeText(login.this, "you are logged in", Toast.LENGTH_LONG);
            Intent home = new Intent(login.this, mymainactivity.class);
            startActivity(home);

        }

        private void handleFacebookAccessToken (AccessToken token){
        Log.i("Info", "handleFacebookAccessToken");
        TextView text = (TextView) findViewById(R.id.txt);
        text.setVisibility(View.VISIBLE);
        signintofirebase(token);

        }
    private void storeFirestore() {

        image_url = "http://graph.facebook.com/" + Profile.getCurrentProfile().getId();
    //    image_url = "http://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=small";
    //    String largeimage = "http://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large";

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("image", image_url);

        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(login.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public  void signintofirebase(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("Info", "signInWithCredential:success");

                            user = mAuth.getCurrentUser();
                            user_id = firebaseAuth.getCurrentUser().getUid();
                            storeFirestore();
                            updateUI(user);
                        } else {

                            Log.i("Info", "signInWithCredential:failure");

                            Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
