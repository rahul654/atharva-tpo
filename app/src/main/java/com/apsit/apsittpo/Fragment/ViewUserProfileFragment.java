package com.apsit.apsittpo.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apsit.apsittpo.EditProfileActivity;
import com.apsit.apsittpo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUserProfileFragment extends Fragment {
    private View mMainView;
    private DatabaseReference mStudentDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    TextView tvStudentName, tvCollegeName, tvMobile, tvAddress, tvEmail,
            tv10thPercent, tv10thBoard,tv10thPassingYear,
            tv12thPercent, tv12thBoard,tv12thPassingYear,tv12thDetail,tv12thPercentTitle, tv12thBoardTitle,tv12thPassingYearTitle,
            tvDiplomaPercent, tvDiplomaBoard, tvDiplomaPassingYear,tvDiplomaDetail,tvDiplomaPercentTitle, tvDiplomaBoardTitle, tvDiplomaPassingYearTitle,
            tvDegreeCGPA, tvDegreeUniversity, tvDegreeType, tvDegreeDepartment, tvDegreePassingYear;

    Button btnEditProfile;

    public ViewUserProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_view_user_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        tvStudentName = (TextView)mMainView.findViewById(R.id.vp_student_name);
        tvCollegeName=(TextView)mMainView.findViewById(R.id.vp_college_name);
        tvMobile=(TextView)mMainView.findViewById(R.id.vp_mobile);
        tvAddress=(TextView)mMainView.findViewById(R.id.vp_address);
        tvEmail=(TextView)mMainView.findViewById(R.id.vp_email);
        tv10thPercent=(TextView)mMainView.findViewById(R.id.vp_10th_percent);
        tv10thBoard=(TextView)mMainView.findViewById(R.id.vp_10th_board);
        tv10thPassingYear=(TextView)mMainView.findViewById(R.id.vp_10th_passing_year);
        tv12thPercent=(TextView)mMainView.findViewById(R.id.vp_12th_percent);
        tv12thBoard=(TextView)mMainView.findViewById(R.id.vp_12th_board);
        tv12thPassingYear=(TextView)mMainView.findViewById(R.id.vp_12th_passing_year);
        tv12thDetail=(TextView)mMainView.findViewById(R.id.vp_12th_detail);
        tv12thPercentTitle=(TextView)mMainView.findViewById(R.id.vp_12th_percent_title);
        tv12thBoardTitle=(TextView)mMainView.findViewById(R.id.vp_12th_board_title);
        tv12thPassingYearTitle=(TextView)mMainView.findViewById(R.id.vp_12th_passing_year_title);
        tvDiplomaPercent=(TextView)mMainView.findViewById(R.id.vp_diploma_percent);
        tvDiplomaBoard=(TextView)mMainView.findViewById(R.id.vp_diploma_board);
        tvDiplomaPassingYear=(TextView)mMainView.findViewById(R.id.vp_diploma_passing_year);
        tvDiplomaDetail=(TextView)mMainView.findViewById(R.id.vp_diploma_detail);
        tvDiplomaPercentTitle=(TextView)mMainView.findViewById(R.id.vp_diploma_percent_title);
        tvDiplomaBoardTitle=(TextView)mMainView.findViewById(R.id.vp_diploma_board_title);
        tvDiplomaPassingYearTitle=(TextView)mMainView.findViewById(R.id.vp_diploma_passing_year_title);
        tvDegreeCGPA=(TextView)mMainView.findViewById(R.id.vp_degree_cgpa);
        tvDegreeUniversity=(TextView)mMainView.findViewById(R.id.vp_degree_university);
        tvDegreeType=(TextView)mMainView.findViewById(R.id.vp_degree_type);
        tvDegreeDepartment=(TextView)mMainView.findViewById(R.id.vp_degree_department);
        tvDegreePassingYear=(TextView)mMainView.findViewById(R.id.vp_degree_passing_year);

        btnEditProfile=(Button)mMainView.findViewById(R.id.btn_edit_profile);

        mStudentDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mStudentDatabase.child( mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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

                btnEditProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return mMainView;
    }

}
