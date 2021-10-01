package com.atharva.atharvatpo.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atharva.atharvatpo.AddJobActivity;
import com.atharva.atharvatpo.Company;
import com.atharva.atharvatpo.CompanyDetailsActivity;
import com.atharva.atharvatpo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyListFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    private View mMainView;
    private RecyclerView mCompanyList;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mUsers;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    public CompanyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_company_list, container, false);
        floatingActionButton = (FloatingActionButton) mMainView.findViewById(R.id.float_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddJobActivity.class));
            }
        });

        //init
        mCompanyList = (RecyclerView) mMainView.findViewById(R.id.need_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Company");
        mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mUsers.child(mCurrent_user_id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usertype = dataSnapshot.child("type").getValue().toString();
                if(usertype.equals("student")) {
                    floatingActionButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //
        mCompanyList.setHasFixedSize(true);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mCompanyList.setLayoutManager(linearVertical);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mCompanyList.getContext(),
                linearVertical.getOrientation()
        );
        mCompanyList.addItemDecoration(mDividerItemDecoration);
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Company, CompanyListFragment.CompanyViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Company, CompanyListFragment.CompanyViewHolder>(

                Company.class,
                R.layout.company_single_layout,
                CompanyListFragment.CompanyViewHolder.class,
                mUsersDatabase) {
            @Override
            protected void populateViewHolder(final CompanyListFragment.CompanyViewHolder companyViewHolder, Company company, int i) {

                companyViewHolder.setDate(company.getDate());

                final String list_user_id = getRef(i).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String companyName = dataSnapshot.child("company_name").getValue().toString();
                        String jobTitle = dataSnapshot.child("job_title").getValue().toString();
                        final String salary = dataSnapshot.child("salary").getValue().toString();
                        String address = dataSnapshot.child("place").getValue().toString();
                        String visitingDate = dataSnapshot.child("visiting_date").getValue().toString();
                        final String joburl = dataSnapshot.child("job_url").getValue().toString();


                        companyViewHolder.setCompanyName(companyName);
                        companyViewHolder.setJobTitle(jobTitle);
                        companyViewHolder.setAddress(address, visitingDate);
                        companyViewHolder.setPhone(salary);
                        companyViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mUsers.child(mCurrent_user_id).addValueEventListener(new ValueEventListener() {
                                    @SuppressLint("RestrictedApi")
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String usertype = dataSnapshot.child("type").getValue().toString();
                                        CharSequence [] options;
                                        if(usertype.equals("student")) {
                                            options = new CharSequence[]{"View Details", "Call TPO", "Apply Now"};
                                        } else {
                                            options = new CharSequence[]{"View Details", "Call Company"};
                                        }

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                        builder.setTitle("Select Options");
                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if(i == 0) {
                                                Intent companyDetailsIntent = new Intent(getContext(), CompanyDetailsActivity.class);
                                                companyDetailsIntent.putExtra("company_id", list_user_id);
                                                startActivity(companyDetailsIntent);
                                            }

                                            //Click Event for each item.
                                            if (i == 2) {
                                                Intent applyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(joburl));
                                                startActivity(applyIntent);
                                            }

                                            if (i == 1) {


                                                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                                    Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:02228574631"));
                                                    // callIntent.setData(Uri.parse("tel:"+uri));
                                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    getActivity().startActivity(callIntent);

                                                }

                                            }

                                        }
                                    });

                                        builder.show();
                                }

                                @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });





                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        mCompanyList.setAdapter(friendsRecyclerViewAdapter);


    }

    // viewholder class..

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public CompanyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setJobTitle(String college){
            TextView userStatusView = (TextView) mView.findViewById(R.id.company_job_title);
            userStatusView.setText(college.toUpperCase());
        }
        public void setCompanyName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.company_name);
            userNameView.setText(name.toUpperCase());
        }
        public void setPhone(String phone){

            TextView userNameView = (TextView) mView.findViewById(R.id.company_mobile);
            userNameView.setText(phone);
        }
        public void setAddress(String address, String visitingDate) {

            TextView userNameView = (TextView) mView.findViewById(R.id.company_place);
            address.toUpperCase();
            visitingDate.toUpperCase();
            userNameView.setText(address.toUpperCase()+" ("+visitingDate.toUpperCase()+ ")");
        }
        public void setDate(String date){


        }
    }

}


