package com.example.evaaherne.fypfoodhive;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 */
/**Resources for this gathered from link below,
 * https://github.com/firebase/quickstart-android/blob/564601cb1da6435e92e53ff281f883d89d815e8e/database/app/src/main/java/com/google/firebase/quickstart/database/java/SignInActivity.java
 *
 *
 */
public class login extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword"; //Tag used to log into the system

    //Declaration of the views
    private TextView mStatusTextView;
    private EditText mEmailField;
    private EditText mPasswordField;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mStatusTextView = findViewById(R.id.status);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Buttons
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);
          findViewById(R.id.verifyEmailButton).setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }
    // [END on_start_check_user]

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                            if(user.isEmailVerified()) {
                                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                                startActivity(i);
                                setContentView(R.layout.activity_user_listings);
                            }
                            else{
                                Toast.makeText(login.this,
                                        "Please Verify your email before continuing.",
                                        Toast.LENGTH_SHORT).show();
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful()) {
                        mStatusTextView.setText(R.string.auth_failed);
                    }
                    hideProgressDialog();
                    // [END_EXCLUDE]
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.verifyEmailButton).setEnabled(false);

        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    // Re-enable button
                    findViewById(R.id.verifyEmailButton).setEnabled(true);

                    if (task.isSuccessful()) {
                        Toast.makeText(login.this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(login.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                    // [END_EXCLUDE]
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        //Sets errors if fields are not filled in correctly
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    //Updates the data
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        //If user is not null null then..
        if (user != null && user.isEmailVerified()) {
           mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                  user.getEmail(), user.isEmailVerified()));

           findViewById(R.id.emailSignInButton).setVisibility(View.GONE);
          findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
        findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());

            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(i);
            setContentView(R.layout.activity_menu);
        }
        else if (user != null && !user.isEmailVerified() ) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));

            findViewById(R.id.emailSignInButton).setVisibility(View.GONE);
            findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.verifyEmailButton).setVisibility(View.VISIBLE);

            Toast.makeText(login.this,
                    "Please Verify your email before continuing.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.emailSignInButton).setVisibility(View.VISIBLE);
           findViewById(R.id.verifyEmailButton).setVisibility(View.GONE);
            findViewById(R.id.signOutButton).setVisibility(View.GONE);
        }
    }

    @Override
    //WHEN BUTTONS ARE CLICKED AND THEIR ACTIONS
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.signOutButton) {
            signOut();}
        else if (i == R.id.verifyEmailButton) {
            sendEmailVerification();
        }
    }
}


