package com.example.busfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase rootNode;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    TextInputLayout regName, regUser, regEmail, regPhone, regPass;
    Button loginBtn, regBtn;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        regBtn = findViewById(R.id.reg_btn);
        regName = findViewById(R.id.fullname);
        regUser = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone);
        regPass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.log_in);

        firebaseAuth = FirebaseAuth.getInstance();
        regBtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn:
                registerUser();
                break;
            case R.id.log_in:
                startActivity(new Intent(this, login.class));
        }
    }
    private Boolean validateName() {

        String val = regName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUser.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUser.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUser.setError("Username is too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUser.setError("White spaces are not allowed");
            return false;
        } else {
            regUser.setError(null);
            regUser.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone() {
        String val = regPhone.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            regPhone.setError("Field cannot be empty");
            return false;
        } else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPass.getEditText().getText().toString().trim();
        String passwordPattern = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordPattern)) {
            regPass.setError("Password is too weak");
            return false;
        } else {
            regPass.setError(null);
            regPass.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser() {

        if (!validateName() | !validatePassword() | !validatePhone() | !validateEmail() | !validateUsername()) {
            return;
        }
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String userID = reference.push().getKey();

        String name = regName.getEditText().getText().toString().trim();
        String username = regUser.getEditText().getText().toString().trim();
        String email = regEmail.getEditText().getText().toString().trim();
        String phone = regPhone.getEditText().getText().toString().trim();
        String password = regPass.getEditText().getText().toString().trim();
        //Store data on firebase
        UserHelperClass helperClass = new UserHelperClass(name, username, email, password, phone);
        reference.child(userID).setValue(helperClass);

        Toast.makeText(this, "Your account has been created successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), login.class);
       // intent.putExtra("phoneNo", phone);
        startActivity(intent);

    }

    public void callLogin(View view) {

        Intent intent = new Intent(Registration.this, login.class);
        startActivity(intent);
    }

   /*private void registerUser() {
        final String Fullname = regName.getEditText().toString().trim();
        final String Username = regUser.getEditText().toString().trim();
        final String Email = regEmail.getEditText().toString().trim();
        final String Phone = regPhone.getEditText().toString().trim();
        final String Password = regPass.getEditText().toString().trim();
        if (Fullname.isEmpty()) {
            regName.setError("FullName Required");
            regName.requestFocus();
            return;
        }
        if (Username.isEmpty()) {
            regName.setError("UserName Required");
            regName.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            regName.setError("Email Required");
            regName.requestFocus();
            return;
        }
        if (Phone.isEmpty()) {
            regName.setError("FullName Required");
            regName.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            regName.setError("FullName Required");
            regName.requestFocus();
        }
       firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserHelperClass user =new UserHelperClass(Fullname, Username, Email, Password, Phone);
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Registration.this,"Register successfull",Toast.LENGTH_LONG).show();
                                Intent intent= new Intent(getApplicationContext(), login.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Registration.this, "Register denied", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Registration.this, "Register failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void callLogin(View view) {
            Intent intent = new Intent(Registration.this, login.class);
            startActivity(intent);
    }*/


}