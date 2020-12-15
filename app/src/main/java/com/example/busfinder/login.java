package com.example.busfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    Button callsignup, btn_login;
    ImageView image;
    TextInputLayout username1, passowrd1;
    TextView logotext, slogantext;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callsignup = findViewById(R.id.sign_up);
        btn_login = findViewById(R.id.login_btn);
        image = findViewById(R.id.logo_image);
        username1 = findViewById(R.id.username);
        passowrd1 = findViewById(R.id.password);
        logotext = findViewById(R.id.logo_msg);
        slogantext = findViewById(R.id.slogan_msg);

    }

    private Boolean validateUsername() {
        String val = username1.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            username1.setError("Field cannot be empty");
            return false;
        } else {
            username1.setError(null);
            username1.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = passowrd1.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            passowrd1.setError("Field cannot be empty");
            return false;
        } else {
            passowrd1.setError(null);
            passowrd1.setErrorEnabled(false);
            return true;
        }
    }


    public void loginUser(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredUsername = username1.getEditText().getText().toString().trim();
        final String userEnteredPassword = passowrd1.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username1.setError(null);
                    username1.setErrorEnabled(false);

                    String passwordfromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordfromDB.equals(userEnteredPassword)) {

                        username1.setError(null);
                        username1.setErrorEnabled(false);

                        String namefromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernamefromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String emailfromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String phonefromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);


                        intent.putExtra("name", namefromDB);
                        intent.putExtra("username", usernamefromDB);
                        intent.putExtra("email", emailfromDB);
                        intent.putExtra("phone", phonefromDB);
                        intent.putExtra("password", passwordfromDB);

                        startActivity(intent);


                    } else {
                        passowrd1.setError("Wrong Password");
                        passowrd1.requestFocus();
                    }
                } else {
                    username1.setError("Username not found");
                    username1.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void callRegistraion(View view) {

        Intent intent = new Intent(login.this, Registration.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(image, "image_logo");
        pairs[1] = new Pair<View, String>(logotext, "logo_text");
        pairs[2] = new Pair<View, String>(slogantext, "start_msg");
        pairs[3] = new Pair<View, String>(btn_login, "button_sign");
        pairs[4] = new Pair<View, String>(callsignup, "next_btn");
        pairs[5] = new Pair<View, String>(username1, "user_tran");
        pairs[6] = new Pair<View, String>(passowrd1, "pass_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this, pairs);
        startActivity(intent, options.toBundle());
    }
}
