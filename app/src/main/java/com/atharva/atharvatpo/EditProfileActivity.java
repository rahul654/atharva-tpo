package com.atharva.atharvatpo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atharva.atharvatpo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    private EditText mEPDisplayName;
    private EditText mEPEmail;
    private EditText mEPAddress;
    private EditText mEPCollege;
    private EditText mEPMobile;
    private EditText mEP10thPercent;
    private EditText mEP10thBoard;
    private EditText mEP10thPassingYear;
    private EditText mEP12thPercent;
    private EditText mEP12thBoard;
    private EditText mEP12thPassingYear;
    private EditText mEPDiplomaPercent;
    private EditText mEPDiplomaBoard;
    private EditText mEPDiplomaPassingYear;
    private EditText mEPUniversityName;
    private EditText mEPDegree;
    private EditText mEPDepartment;
    private EditText mEPDegreeCGPA;
    private EditText mEPDegreePassingYear;

    private Button mUpdateBtn;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabaseStudent;

    String ep_display_name;
    String ep_email;
    String ep_mobile;
    String ep_address;
    String ep_college;
    String ep_percent10th;
    String ep_board10th;
    String ep_passing_year10th;
    String ep_percent12th;
    String ep_board12th;
    String ep_passing_year12th;
    String ep_percent_diploma;
    String ep_board_diploma;
    String ep_passing_year_diploma;
    String ep_university_name;
    String ep_degree;
    String ep_department;
    String ep_degree_cgpa;
    String ep_degree_passing_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        mRegProgress=new ProgressDialog(this);

        mEPDisplayName=(EditText)findViewById(R.id.ep_username);
        mEPEmail=(EditText)findViewById(R.id.ep_email);
        mEPCollege=(EditText)findViewById(R.id.ep_college);
        mEPMobile=(EditText)findViewById(R.id.ep_mobile);
        mEPAddress=(EditText)findViewById(R.id.ep_address);
        mEP10thPercent=(EditText)findViewById(R.id.ep_10thpercent);
        mEP10thBoard=(EditText)findViewById(R.id.ep_10thboard);
        mEP10thPassingYear=(EditText)findViewById(R.id.ep_10th_passing_year);
        mEP12thPercent=(EditText)findViewById(R.id.ep_12thpercent);
        mEP12thBoard=(EditText)findViewById(R.id.ep_12thboard);
        mEP12thPassingYear=(EditText)findViewById(R.id.ep_12th_passing_year);
        mEPDiplomaPercent=(EditText)findViewById(R.id.ep_diploma_percent);
        mEPDiplomaBoard=(EditText)findViewById(R.id.ep_diploma_board);
        mEPDiplomaPassingYear=(EditText)findViewById(R.id.ep_diploma_passing_year);
        mEPUniversityName=(EditText)findViewById(R.id.ep_university_name);
        mEPDegree=(EditText)findViewById(R.id.ep_degree);
        mEPDepartment=(EditText)findViewById(R.id.ep_department);
        mEPDegreeCGPA=(EditText)findViewById(R.id.ep_degree_cgpa);
        mEPDegreePassingYear=(EditText)findViewById(R.id.ep_degree_passing_year);

        mDatabaseStudent = FirebaseDatabase.getInstance().getReference().child("Students");

        mDatabaseStudent.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ep_display_name=dataSnapshot.child("name").getValue().toString();
                ep_email=dataSnapshot.child("email").getValue().toString();
                ep_college=dataSnapshot.child("college_name").getValue().toString();
                ep_mobile=dataSnapshot.child("mobile").getValue().toString();
                ep_address=dataSnapshot.child("place").getValue().toString();
                ep_percent10th=dataSnapshot.child("10th_percentage").getValue().toString();
                ep_board10th=dataSnapshot.child("10th_board").getValue().toString();
                ep_passing_year10th=dataSnapshot.child("10th_passing_year").getValue().toString();
                ep_percent12th=dataSnapshot.child("12th_percentage").getValue().toString();
                ep_board12th=dataSnapshot.child("12th_board").getValue().toString();
                ep_passing_year12th=dataSnapshot.child("12th_passing_year").getValue().toString();
                ep_percent_diploma=dataSnapshot.child("diploma_percentage").getValue().toString();
                ep_board_diploma=dataSnapshot.child("diploma_board").getValue().toString();
                ep_passing_year_diploma=dataSnapshot.child("diploma_passing_year").getValue().toString();
                ep_university_name=dataSnapshot.child("university_name").getValue().toString();
                ep_degree=dataSnapshot.child("degree").getValue().toString();
                ep_department=dataSnapshot.child("department").getValue().toString();
                ep_degree_cgpa=dataSnapshot.child("degree_cgpa").getValue().toString();
                ep_degree_passing_year=dataSnapshot.child("degree_passing_year").getValue().toString();

                mEPDisplayName.setText(ep_display_name);
                mEPEmail.setText(ep_email);
                mEPEmail.setEnabled(false);
                mEPCollege.setText(ep_college);
                mEPMobile.setText(ep_mobile);
                mEPAddress.setText(ep_address);
                mEP10thPercent.setText(ep_percent10th);
                mEP10thBoard.setText(ep_board10th);
                mEP10thPassingYear.setText(ep_passing_year10th);
                mEP12thPercent.setText(ep_percent12th);
                mEP12thBoard.setText(ep_board12th);
                mEP12thPassingYear.setText(ep_passing_year12th);
                mEPDiplomaPercent.setText(ep_percent_diploma);
                mEPDiplomaBoard.setText(ep_board_diploma);
                mEPDiplomaPassingYear.setText(ep_passing_year_diploma);
                mEPUniversityName.setText(ep_university_name);
                mEPDegree.setText(ep_degree);
                mEPDepartment.setText(ep_department);
                mEPDegreeCGPA.setText(ep_degree_cgpa);
                mEPDegreePassingYear.setText(ep_degree_passing_year);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUpdateBtn=(Button)findViewById(R.id.update_profile_btn);



        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegProgress.setTitle("Updating Details");
                mRegProgress.setMessage("Please wait");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                ep_display_name=mEPDisplayName.getText().toString();
                ep_email=mEPEmail.getText().toString();
                ep_college=mEPCollege.getText().toString();
                ep_mobile=mEPMobile.getText().toString();
                ep_address=mEPAddress.getText().toString();
                ep_percent10th=mEP10thPercent.getText().toString();
                ep_board10th=mEP10thBoard.getText().toString();
                ep_passing_year10th=mEP10thPassingYear.getText().toString();
                ep_percent12th=mEP12thPercent.getText().toString();
                ep_board12th=mEP12thBoard.getText().toString();
                ep_passing_year12th=mEP12thPassingYear.getText().toString();
                ep_percent_diploma=mEPDiplomaPercent.getText().toString();
                ep_board_diploma=mEPDiplomaBoard.getText().toString();
                ep_passing_year_diploma=mEPDiplomaPassingYear.getText().toString();
                ep_university_name=mEPUniversityName.getText().toString();
                ep_degree=mEPDegree.getText().toString();
                ep_department=mEPDepartment.getText().toString();
                ep_degree_cgpa=mEPDegreeCGPA.getText().toString();
                ep_degree_passing_year=mEPDegreePassingYear.getText().toString();

                if(!TextUtils.isEmpty(ep_display_name)||!TextUtils.isEmpty(ep_email)||!TextUtils.isEmpty(ep_college)||!TextUtils.isEmpty(ep_mobile)||!TextUtils.isEmpty(ep_address)||
                        !TextUtils.isEmpty(ep_percent10th)||!TextUtils.isEmpty(ep_board10th)||!TextUtils.isEmpty(ep_passing_year10th)||
                        !TextUtils.isEmpty(ep_university_name)||!TextUtils.isEmpty(ep_degree)||!TextUtils.isEmpty(ep_department)||
                        !TextUtils.isEmpty(ep_degree_cgpa)||!TextUtils.isEmpty(ep_degree_passing_year)){

                    final HashMap<String, String> studentMap = new HashMap<>();
                    studentMap.put("name", ep_display_name);
                    studentMap.put("email", ep_email);
                    studentMap.put("college_name",ep_college);
                    studentMap.put("mobile",ep_mobile);
                    studentMap.put("place",ep_address);
                    studentMap.put("10th_percentage", ep_percent10th);
                    studentMap.put("10th_board", ep_board10th);
                    studentMap.put("10th_passing_year", ep_passing_year10th);
                    studentMap.put("12th_percentage", ep_percent12th);
                    studentMap.put("12th_board", ep_board12th);
                    studentMap.put("12th_passing_year", ep_passing_year12th);
                    studentMap.put("diploma_percentage", ep_percent_diploma);
                    studentMap.put("diploma_board", ep_board_diploma);
                    studentMap.put("diploma_passing_year", ep_passing_year_diploma);
                    studentMap.put("university_name",ep_university_name);
                    studentMap.put("degree",ep_degree);
                    studentMap.put("department",ep_department);
                    studentMap.put("degree_cgpa",ep_degree_cgpa);
                    studentMap.put("degree_passing_year",ep_degree_passing_year);

                    mRegProgress.dismiss();
                    mDatabaseStudent.child(mAuth.getCurrentUser().getUid()).setValue(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),"Updated Successfully!",Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    });
                } else {
                    mRegProgress.hide();
                    Toast.makeText(EditProfileActivity.this,"Please Enter Required Field",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
