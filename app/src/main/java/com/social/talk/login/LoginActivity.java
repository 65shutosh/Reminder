package com.social.talk.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.social.talk.MainActivity;
import com.social.talk.databinding.ActivityLoginBinding;
import com.social.talk.otp.OTPActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        auth = FirebaseAuth.getInstance();

        //open keyboard with login screen
        loginBinding.loginEditTextPhoneNumber.requestFocus();
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(loginBinding.loginEditTextPhoneNumber,InputMethodManager.SHOW_FORCED);

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginBinding.loginEditTextPhoneNumber.getText().toString().length() != 10)
                    Toast.makeText(LoginActivity.this, "Please Provide a Valid Phone Number", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                    intent.putExtra("phoneNumber", loginBinding.loginEditTextPhoneNumber.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }


}