package com.atharva.atharvatpo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.atharva.atharvatpo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentDetailsActivity extends AppCompatActivity {
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mStudent_user_id;

    TextView tvStudentName, tvCollegeName, tvMobile, tvAddress, tvEmail,
            tv10thPercent, tv10thBoard,tv10thPassingYear,
            tv12thPercent, tv12thBoard,tv12thPassingYear,tv12thDetail,tv12thPercentTitle, tv12thBoardTitle,tv12thPassingYearTitle,
            tvDiplomaPercent, tvDiplomaBoard, tvDiplomaPassingYear,tvDiplomaDetail,tvDiplomaPercentTitle, tvDiplomaBoardTitle, tvDiplomaPassingYearTitle,
            tvDegreeCGPA, tvDegreeUniversity, tvDegreeType, tvDegreeDepartment, tvDegreePassingYear;

    Button btnCallStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        mAuth = FirebaseAuth.getInstance();

        tvStudentName = (TextView)findViewById(R.id.sd_student_name);
        tvCollegeName=(TextView)findViewById(R.id.sd_college_name);
        tvMobile=(TextView)findViewById(R.id.sd_mobile);
        tvAddress=(TextView)findViewById(R.id.sd_address);
        tvEmail=(TextView)findViewById(R.id.sd_email);
        tv10thPercent=(TextView)findViewById(R.id.sd_10th_percent);
        tv10thBoard=(TextView)findViewById(R.id.sd_10th_board);
        tv10thPassingYear=(TextView)findViewById(R.id.sd_10th_passing_year);
        tv12thPercent=(TextView)findViewById(R.id.sd_12th_percent);
        tv12thBoard=(TextView)findViewById(R.id.sd_12th_board);
        tv12thPassingYear=(TextView)findViewById(R.id.sd_12th_passing_year);
        tv12thDetail=(TextView)findViewById(R.id.sd_12th_detail);
        tv12thPercentTitle=(TextView)findViewById(R.id.sd_12th_percent_title);
        tv12thBoardTitle=(TextView)findViewById(R.id.sd_12th_board_title);
        tv12thPassingYearTitle=(TextView)findViewById(R.id.sd_12th_passing_year_title);
        tvDiplomaPercent=(TextView)findViewById(R.id.sd_diploma_percent);
        tvDiplomaBoard=(TextView)findViewById(R.id.sd_diploma_board);
        tvDiplomaPassingYear=(TextView)findViewById(R.id.sd_diploma_passing_year);
        tvDiplomaDetail=(TextView)findViewById(R.id.sd_diploma_detail);
        tvDiplomaPercentTitle=(TextView)findViewById(R.id.sd_diploma_percent_title);
        tvDiplomaBoardTitle=(TextView)findViewById(R.id.sd_diploma_board_title);
        tvDiplomaPassingYearTitle=(TextView)findViewById(R.id.sd_diploma_passing_year_title);
        tvDegreeCGPA=(TextView)findViewById(R.id.sd_degree_cgpa);
        tvDegreeUniversity=(TextView)findViewById(R.id.sd_degree_university);
        tvDegreeType=(TextView)findViewById(R.id.sd_degree_type);
        tvDegreeDepartment=(TextView)findViewById(R.id.sd_degree_department);
        tvDegreePassingYear=(TextView)findViewById(R.id.sd_degree_passing_year);

        btnCallStudent=(Button)findViewById(R.id.btn_call_student);

        mStudent_user_id = getIntent().getStringExtra("user_id");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Students");

        mUsersDatabase.child(mStudent_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String userName = dataSnapshot.child("name").getValue().toString();
                String college = dataSnapshot.child("college_name").getValue().toString();
                final String phone = dataSnapshot.child("mobile").getValue().toString();
                String address = dataSnapshot.child("place").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String percent10th = dataSnapshot.child("10th_percentage").getValue().toString();
                String board10th = dataSnapshot.child("10th_board").getValue().toString();
                String passingYear10th = dataSnapshot.child("10th_passing_year").getValue().toString();
                String percent12th = dataSnapshot.child("12th_percentage").getValue().toString();
                String board12th = dataSnapshot.child("12th_board").getValue().toString();
                String passingYear12th = dataSnapshot.child("12th_passing_year").getValue().toString();
                String percentDiploma = dataSnapshot.child("diploma_percentage").getValue().toString();
                String boardDiploma = dataSnapshot.child("diploma_board").getValue().toString();
                String passingYearDiploma = dataSnapshot.child("diploma_passing_year").getValue().toString();
                String degreeCGPA = dataSnapshot.child("degree_cgpa").getValue().toString();
                String degreeUniversity = dataSnapshot.child("university_name").getValue().toString();
                String degreeType = dataSnapshot.child("degree").getValue().toString();
                String degreeDepartment = dataSnapshot.child("department").getValue().toString();
                String degreePassingYear = dataSnapshot.child("degree_passing_year").getValue().toString();

                tvStudentName.setText(userName.toUpperCase());
                tvCollegeName.setText(college.toUpperCase());
                tvMobile.setText(phone);
                tvAddress.setText(address.toUpperCase());
                tvEmail.setText(email.toLowerCase());
                tv10thPercent.setText(percent10th+"%");
                tv10thBoard.setText(board10th.toUpperCase());
                tv10thPassingYear.setText(passingYear10th);
                if(!percent12th.isEmpty() || !board12th.isEmpty() || !passingYear12th.isEmpty()) {
                    tv12thPercent.setText(percent12th+"%");
                    tv12thBoard.setText(board12th.toUpperCase());
                    tv12thPassingYear.setText(passingYear12th);
                } else {
                    tv12thDetail.setVisibility(View.GONE);
                    tv12thPercent.setVisibility(View.GONE);
                    tv12thBoard.setVisibility(View.GONE);
                    tv12thPassingYear.setVisibility(View.GONE);
                    tv12thPercentTitle.setVisibility(View.GONE);
                    tv12thBoardTitle.setVisibility(View.GONE);
                    tv12thPassingYearTitle.setVisibility(View.GONE);
                }

                if(!percentDiploma.isEmpty() || !boardDiploma.isEmpty() || !passingYearDiploma.isEmpty()) {
                    tvDiplomaPercent.setText(percentDiploma+"%");
                    tvDiplomaBoard.setText(boardDiploma.toUpperCase());
                    tvDiplomaPassingYear.setText(passingYearDiploma);
                } else {
                    tvDiplomaDetail.setVisibility(View.GONE);
                    tvDiplomaPercent.setVisibility(View.GONE);
                    tvDiplomaBoard.setVisibility(View.GONE);
                    tvDiplomaPassingYear.setVisibility(View.GONE);
                    tvDiplomaPercentTitle.setVisibility(View.GONE);
                    tvDiplomaBoardTitle.setVisibility(View.GONE);
                    tvDiplomaPassingYearTitle.setVisibility(View.GONE);
                }
                tvDiplomaPercent.setText(percentDiploma);
                tvDiplomaBoard.setText(boardDiploma.toUpperCase());
                tvDiplomaPassingYear.setText(passingYearDiploma);
                tvDegreeCGPA.setText(degreeCGPA);
                tvDegreeUniversity.setText(degreeUniversity.toUpperCase());
                tvDegreePassingYear.setText(degreePassingYear);
                tvDegreeType.setText(degreeType.toUpperCase());
                tvDegreeDepartment.setText(degreeDepartment.toUpperCase());

                btnCallStudent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        // callIntent.setData(Uri.parse("tel:"+uri));
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}