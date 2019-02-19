package com.example.evaaherne.fypfoodhive;


import android.content.Intent;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.evaaherne.fypfoodhive.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    ListView listViewUsers;
    DatabaseReference databaseUsers;



    private static final String TAG = "EmailPassword"; //Tag used to log into the system

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //DATABASE REFERENCE
        databaseUsers = FirebaseDatabase.getInstance().getReference(getString(R.string.users));

       //VIEWS
        listViewUsers = findViewById(R.id.listViewUsers);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);


        //BUTTON
         buttonCreateA = findViewById(R.id.buttonCreateA);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        buttonCreateA.setOnClickListener(new View.OnClickListener() {

          //APPLIES VALUES TO THE DB IF CHECKED/NOT CHECKED
            @Override
            public void onClick(View view) {

                addUser();
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());

                Intent i = new Intent(getApplicationContext(), User_detail.class);
                startActivity(i);
                setContentView(R.layout.activity_user_detail);



            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email); //Logs to system
        if (!validateForm()) {
            return;
        }

        //extends to base activity
        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user); //Updates the ui on what user is signed in
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
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

        return valid;
    }

    //Updates the data
    private void updateUI(FirebaseUser user) {
        hideProgressDialog();

    }

    private void addUser () {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();


        //IF THE VIEW IS NOT EMPTY FOR NAME
        if (!TextUtils.isEmpty(name)) {

            String id = databaseUsers.push().getKey(); //Get a unique key for the id
            Users users = new Users(id, name, email); //OBJ INSTANSTIATION

            //SETS DB VALUES
            databaseUsers.child(id).setValue(users);


            //Toast is a short message that pops up on screen
            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
        }
    }


}

