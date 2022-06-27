package com.social.talk.otp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.social.talk.databinding.ActivityOtpBinding
import com.social.talk.profile.SetupProfileActivity
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private var otpBinding: ActivityOtpBinding? = null
    private var mAuth: FirebaseAuth? = null
    var verificationCode: String? = null
    var dialog: ProgressDialog? = null
    private var phone: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpBinding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(otpBinding!!.root)
        mAuth = FirebaseAuth.getInstance()

        //Dialog for sending otp
        dialog = ProgressDialog(this);
        dialog!!.setMessage("Sending otp...");
        dialog!!.setCancelable(false);
        dialog!!.show();
        val phoneNumber = intent.getStringExtra("phoneNumber")
        phone = countryCode(phoneNumber)
        otpBinding!!.otpTextView.text = "Verify $phone"
        var otp: String = ""
        otpBinding!!.otpView.setContent {
            AppCompatTheme {
                otp = SimplePinEntryView()
            }
        }

//          otpBinding.otpView.requestFocus();
//         showKeyBoard();
//
//        otpBinding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
//            @Override
//            public void onOtpCompleted(String otp) {

//            }
//        });
////    }


        otpBinding!!.button.setOnClickListener {
          //  Toast.makeText(this, otp, Toast.LENGTH_SHORT).show()
            var credential = verificationCode?.let { PhoneAuthProvider.getCredential(it, otp) };
            if (credential != null) {
                mAuth!!.signInWithCredential(credential)
                    .addOnCompleteListener(OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(OTPActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                        startProfileSetupActivity();
//                    } else {
//                        Toast.makeText(OTPActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
                        if (it.isComplete) {
                            if (it.isSuccessful)
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                            startProfileSetupActivity()
                        } else
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                    })
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phone!!) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}
                override fun onVerificationFailed(e: FirebaseException) {}
                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)
                    verificationCode = s
                    dialog?.dismiss();
                }
            }) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startProfileSetupActivity() {
        val intent = Intent(this@OTPActivity, SetupProfileActivity::class.java)
        intent.putExtra("phoneNumber", phone)
        startActivity(intent)
        finishAffinity()
    }

    private fun countryCode(phoneNumber: String?): String {
        return "+91$phoneNumber"
    } //    private void showKeyBoard() {
    //        otpBinding.otpView.requestFocus();
    //        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    //        inputMethodManager.showSoftInput(otpBinding.otpTextView, InputMethodManager.SHOW_IMPLICIT);
    //    }
}