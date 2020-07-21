package com.apsit.apsittpo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddJobActivity extends AppCompatActivity {

    //UI
    Button btnRequest, btnDatePicker;
    EditText edtCompanyName,edtJobTitle,edtPlace,edtSalary,edtVisitingDate,edtJobDesc,edtJobUrl;
    private int mYear, mMonth, mDay;
    //DB
    DatabaseReference mCompanyer;
    FirebaseAuth mAuth;
    //progress
    ProgressDialog mProgress;
    Random rand = new Random();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjob);
        //initialisation
        edtJobTitle=(EditText)findViewById(R.id.aj_job_title);
        edtSalary=(EditText)findViewById(R.id.aj_salary);
        edtCompanyName=(EditText)findViewById(R.id.aj_company_name);
        edtPlace=(EditText)findViewById(R.id.aj_place);
        edtVisitingDate=(EditText)findViewById(R.id.aj_visiting_date);
        edtJobDesc=(EditText)findViewById(R.id.aj_job_description);
        edtJobUrl=(EditText)findViewById(R.id.aj_job_url);
        btnRequest=(Button)findViewById(R.id.button2);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        //firebase
        mCompanyer= FirebaseDatabase.getInstance().getReference();
        final String mCurrentUser=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        mAuth=FirebaseAuth.getInstance();
        //progress
        mProgress=new ProgressDialog(this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Please wait..");

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(btnDatePicker.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtVisitingDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();
                String jobTitle=edtJobTitle.getText().toString();
                String companyName=edtCompanyName.getText().toString();
                String salary=edtSalary.getText().toString();
                String place=edtPlace.getText().toString();
                String visitingDate=edtVisitingDate.getText().toString();
                String jobDesc=edtJobDesc.getText().toString();
                String jobUrl=edtJobUrl.getText().toString();
                String temp=jobTitle.toUpperCase();
                if(!TextUtils.isEmpty(jobTitle)||!TextUtils.isEmpty(companyName)||!TextUtils.isEmpty(salary)||
                        !TextUtils.isEmpty(place)){

                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("company_name", companyName);
                        userMap.put("job_title", jobTitle);
                        userMap.put("salary", salary);
                        userMap.put("place", place);
                        userMap.put("visiting_date", visitingDate);
                        userMap.put("job_description", jobDesc);
                        userMap.put("job_url", jobUrl);

                    int rand_cid = rand.nextInt(1000000000);
                    String uid = Integer.toString(rand_cid);
                    String hashtext;
                    try {

                        // Static getInstance method is called with hashing MD5
                        MessageDigest md = MessageDigest.getInstance("MD5");

                        // digest() method is called to calculate message digest
                        //  of an input digest() return array of byte
                        byte[] messageDigest = md.digest(uid.getBytes());

                        // Convert byte array into signum representation
                        BigInteger no = new BigInteger(1, messageDigest);

                        // Convert message digest into hex value
                        hashtext = no.toString(16);
                        while (hashtext.length() < 32) {
                            hashtext = "0" + hashtext;
                        }
                    }

                    // For specifying wrong message digest algorithms
                    catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    mCompanyer.child("Company").child(hashtext).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mProgress.dismiss();
                                Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

            }else{
                    Toast.makeText(getApplicationContext(),"Please enter the details in all fields",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}

