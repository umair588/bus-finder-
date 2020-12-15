package com.example.busfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullname,email,password,phone;
    TextView fullnameLabel,usernameLabel;
    DatabaseReference reference;
    String _USERNAME,_PASSWORD,_NAME,_PHONE,_EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reference = FirebaseDatabase.getInstance().getReference("users");

        fullname=findViewById(R.id.fullname_profile);
        email=findViewById(R.id.email_profile);
        password=findViewById(R.id.password_profile);
        phone=findViewById(R.id.phone_profile);
        fullnameLabel=findViewById(R.id.fullname_field);
        usernameLabel=findViewById(R.id.username_field);

        showAllUserData();

    }

    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONE = intent.getStringExtra("phone");
        _PASSWORD = intent.getStringExtra("password");

        fullnameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        fullname.getEditText().setText(_NAME);
        email.getEditText().setText(_EMAIL);
        phone.getEditText().setText(_PHONE);
        password.getEditText().setText(_PASSWORD);

    }

    public void update(View view) {

        if(isNameChanged() || isPasswordChanged()){
            Toast.makeText(this, "Your Data Has Been Updated", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
    }

    private boolean isPasswordChanged() {
        if(!_PASSWORD.equals(password.getEditText().getText().toString()))
        {
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD=password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!_NAME.equals(fullname.getEditText().getText().toString())){
            reference.child(_USERNAME).child("name").setValue(fullname.getEditText().getText().toString());
            _NAME=fullname.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }

    public void showlocation(View view) {
        Intent intent = new Intent(UserProfile.this,UserLocation.class);
        startActivity(intent);
        finish();
    }

    public void select_route(View view) {
        Intent intent = new Intent(UserProfile.this,RouteSelection.class);
        startActivity(intent);
        finish();
    }
}