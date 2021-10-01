package com.atharva.atharvatpo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.atharva.atharvatpo.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mCollege;
    private EditText mMobile;
    private EditText m10thPercent;
    private EditText m10thBoard;
    private EditText m10thPassingYear;
    private EditText m12thPercent;
    private EditText m12thBoard;
    private EditText m12thPassingYear;
    private EditText mDiplomaPercent;
    private EditText mDiplomaBoard;
    private EditText mDiplomaPassingYear;
    private EditText mUniversityName;
    private EditText mDegree;
    private EditText mDepartment;
    private EditText mDegreeCGPA;
    private EditText mDegreePassingYear;

    private Button mCreateBtn;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseStudent;

    String display_name;
    String email;
    String mobile;
    String password;
    String address;
    String college;
    String percent10th;
    String board10th;
    String passing_year10th;
    String percent12th;
    String board12th;
    String passing_year12th;
    String percent_diploma;
    String board_diploma;
    String passing_year_diploma;
    String university_name;
    String degree;
    String department;
    String degree_cgpa;
    String degree_passing_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        mRegProgress=new ProgressDialog(this);

        mDisplayName=(EditText)findViewById(R.id.reg_username);
        mEmail=(EditText)findViewById(R.id.reg_email);
        mPassword=(EditText)findViewById(R.id.reg_pwd);
        mCollege=(EditText)findViewById(R.id.reg_college);
        mMobile=(EditText)findViewById(R.id.reg_mobile);
        mAddress=(EditText)findViewById(R.id.reg_address);
        m10thPercent=(EditText)findViewById(R.id.reg_10thpercent);
        m10thBoard=(EditText)findViewById(R.id.reg_10thboard);
        m10thPassingYear=(EditText)findViewById(R.id.reg_10th_passing_year);
        m12thPercent=(EditText)findViewById(R.id.reg_12thpercent);
        m12thBoard=(EditText)findViewById(R.id.reg_12thboard);
        m12thPassingYear=(EditText)findViewById(R.id.reg_12th_passing_year);
        mDiplomaPercent=(EditText)findViewById(R.id.reg_diploma_percent);
        mDiplomaBoard=(EditText)findViewById(R.id.reg_diploma_board);
        mDiplomaPassingYear=(EditText)findViewById(R.id.reg_diploma_passing_year);
        mUniversityName=(EditText)findViewById(R.id.reg_university_name);
        mDegree=(EditText)findViewById(R.id.reg_degree);
        mDepartment=(EditText)findViewById(R.id.reg_department);
        mDegreeCGPA=(EditText)findViewById(R.id.reg_degree_cgpa);
        mDegreePassingYear=(EditText)findViewById(R.id.reg_degree_passing_year);

        mCreateBtn=(Button)findViewById(R.id.register);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_name=mDisplayName.getText().toString();
                email=mEmail.getText().toString();
                password=mPassword.getText().toString();
                college=mCollege.getText().toString();
                mobile=mMobile.getText().toString();
                address=mAddress.getText().toString();
                percent10th=m10thPercent.getText().toString();
                board10th=m10thBoard.getText().toString();
                passing_year10th=m10thPassingYear.getText().toString();
                percent12th=m12thPercent.getText().toString();
                board12th=m12thBoard.getText().toString();
                passing_year12th=m12thPassingYear.getText().toString();
                percent_diploma=mDiplomaPercent.getText().toString();
                board_diploma=mDiplomaBoard.getText().toString();
                passing_year_diploma=mDiplomaPassingYear.getText().toString();
                university_name=mUniversityName.getText().toString();
                degree=mDegree.getText().toString();
                department=mDepartment.getText().toString();
                degree_cgpa=mDegreeCGPA.getText().toString();
                degree_passing_year=mDegreePassingYear.getText().toString();

                if(!TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)||
                        !TextUtils.isEmpty(college)||!TextUtils.isEmpty(mobile)||!TextUtils.isEmpty(address)||
                        !TextUtils.isEmpty(percent10th)||!TextUtils.isEmpty(board10th)||!TextUtils.isEmpty(passing_year10th)||
                        !TextUtils.isEmpty(university_name)||!TextUtils.isEmpty(degree)||!TextUtils.isEmpty(department)||
                        !TextUtils.isEmpty(degree_cgpa)||!TextUtils.isEmpty(degree_passing_year)){
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name,email,password);
                } else {
                    Toast.makeText(RegisterActivity.this,"Please Enter Required Field",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void register_user(final String display_name, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                    String uid=current_user.getUid();
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    //user.sendEmailVerification();
                    //mEmailverification.setTitle("Check your email and verify it");
                    //mEmailverification.setMessage("Verifying...");
                    // mEmailverification.show();
                    Boolean emailVerfied=user.isEmailVerified();
                    Log.e("Success", String.valueOf(emailVerfied));


                    mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    mDatabaseStudent = FirebaseDatabase.getInstance().getReference().child("Students").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("password",password);
                    userMap.put("device_token", device_token);
                    userMap.put("type", "student");


                    final HashMap<String, String> studentMap = new HashMap<>();
                    studentMap.put("name", display_name);
                    studentMap.put("email", email);
                    studentMap.put("college_name",college);
                    studentMap.put("mobile",mobile);
                    studentMap.put("place",address);
                    studentMap.put("10th_percentage", percent10th);
                    studentMap.put("10th_board", board10th);
                    studentMap.put("10th_passing_year", passing_year10th);
                    studentMap.put("12th_percentage", percent12th);
                    studentMap.put("12th_board", board12th);
                    studentMap.put("12th_passing_year", passing_year12th);
                    studentMap.put("diploma_percentage", percent_diploma);
                    studentMap.put("diploma_board", board_diploma);
                    studentMap.put("diploma_passing_year", passing_year_diploma);
                    studentMap.put("university_name",university_name);
                    studentMap.put("degree",degree);
                    studentMap.put("department",department);
                    studentMap.put("degree_cgpa",degree_cgpa);
                    studentMap.put("degree_passing_year",degree_passing_year);


                    mRegProgress.dismiss();
                    mDatabaseStudent.setValue(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Registered Successfully..!",Toast.LENGTH_LONG).show();
                        }
                    });
                    mDatabaseUsers.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //  mRegProgress.dismiss();
                                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();

                        }

                    });

                }
                else{
                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Authentication failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
