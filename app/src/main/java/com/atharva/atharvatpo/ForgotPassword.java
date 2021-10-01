package com.atharva.atharvatpo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atharva.atharvatpo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button mSendLink;
    EditText mResetEmail;
    private ProgressDialog mResetProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mSendLink = (Button)findViewById(R.id.sendResetLink);
        mResetEmail = (EditText)findViewById(R.id.reset_email);

        mSendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mResetEmail.getText().toString();
                if(!TextUtils.isEmpty(email))
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "Reset Link has been sent to your email", Toast.LENGTH_LONG).show();
                                        Intent mainIntent=new Intent(ForgotPassword.this,LoginActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(ForgotPassword.this, "Email Not Found", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ForgotPassword.this, "Enter Email Id", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
