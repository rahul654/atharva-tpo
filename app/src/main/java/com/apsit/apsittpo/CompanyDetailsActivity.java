package com.apsit.apsittpo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyDetailsActivity extends AppCompatActivity {

    private DatabaseReference mCompanyDatabase;

    private FirebaseAuth mAuth;

    private String mCompany_user_id;

    TextView tvJobTitle, tvCompanyName, tvSalary, tvAddress, tvVisitingDate, tvJobDesc;

    Button btnApplyNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        mAuth = FirebaseAuth.getInstance();

        tvJobTitle = (TextView)findViewById(R.id.cd_job_title);
        tvCompanyName=(TextView)findViewById(R.id.cd_company_name);
        tvSalary=(TextView)findViewById(R.id.cd_salary);
        tvAddress=(TextView)findViewById(R.id.cd_address);
        tvVisitingDate=(TextView)findViewById(R.id.cd_visiting_date);
        tvJobDesc=(TextView)findViewById(R.id.cd_job_desc);

        btnApplyNow=(Button)findViewById(R.id.btn_apply_now);

        mCompany_user_id = getIntent().getStringExtra("company_id");
        mCompanyDatabase = FirebaseDatabase.getInstance().getReference().child("Company");

        mCompanyDatabase.child(mCompany_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String jobTitle = dataSnapshot.child("job_title").getValue().toString();
                String companyName = dataSnapshot.child("company_name").getValue().toString();
                String salary = dataSnapshot.child("salary").getValue().toString();
                String address = dataSnapshot.child("place").getValue().toString();
                String visitingDate = dataSnapshot.child("visiting_date").getValue().toString();
                String jobDesc = dataSnapshot.child("job_description").getValue().toString();
                final String jobUrl = dataSnapshot.child("job_url").getValue().toString();

                tvJobTitle.setText(jobTitle);
                tvCompanyName.setText(companyName);
                tvSalary.setText(salary);
                tvAddress.setText(address);
                tvVisitingDate.setText(visitingDate);
                tvJobDesc.setText(jobDesc);

                btnApplyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jobUrl));
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
