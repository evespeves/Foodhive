package com.example.evaaherne.fypfoodhive;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.evaaherne.fypfoodhive.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/** Some code sources sourced from https://www.youtube.com/watch?v=EM2x33g4syY&t=5s&ab_channel=SimplifiedCoding
 *
 */

public class RegisterActivity extends BaseActivity  {

    //DECLARATIONS
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonCreateA;
    DatabaseReference databaseUsers;



    private static final String TAG = "EmailPassword"; //Tag used to log into the system

    // [START declare_auth]
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


       //VIEWS
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);


        //BUTTON
         buttonCreateA = findViewById(R.id.buttonCreateA);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.




        //APPLIES VALUES TO THE DB IF CHECKED/NOT CHECKED
        buttonCreateA.setOnClickListener(view -> createAccount(editTextEmail.getEditableText().toString(), editTextPassword.getEditableText().toString()));
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email); //Logs to system
        if (!validateForm()) {
            return;
        }

        //extends to base activity
        showProgressDialog();
        mAuth.signOut();
        updateUI(null);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            if (user != null) {
                                String uid = user.getUid();
                                addUser(uid);
                                startActivity(new Intent(getApplicationContext(), MenuActivity.class));

                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed. Please use another email",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }




    /**
     * Validates that all fields are filled in before continuing
     */
    private boolean validateForm() {
        boolean valid = true;

        //Sets errors if fields are not filled in correctly
        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        //Sets errors if fields are not filled in correctly
        String name = editTextName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Required.");
            valid = false;
        } else {
            editTextName.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        hideKeyboard(this);
        return valid;
    }

    //Updates the data
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();

    }

    /**
     * Adds user to database with details
     * @param id
     */
    private void addUser (String id) {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        databaseUsers = FirebaseDatabase.getInstance().getReference(getString(R.string.users));
        //IF THE VIEW IS NOT EMPTY FOR NAME
        if (!TextUtils.isEmpty(name)) {
            Users users = new Users(id, name, email); //OBJ INSTANSTIATION

            //SETS DB VALUES
            databaseUsers.child(id).setValue(users);

            //Toast is a short message that pops up on screen
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
        }
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

