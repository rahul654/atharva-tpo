package com.apsit.apsittpo.Fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apsit.apsittpo.Company;
import com.apsit.apsittpo.CompanyDetailsActivity;
import com.apsit.apsittpo.R;
import com.apsit.apsittpo.TPOMembers;
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
public class TPOMemberListFragment extends Fragment {

    private View mMainView;
    private RecyclerView mTPOMemberList;
    private DatabaseReference mTPODatabase;
    private DatabaseReference mUsers;

    private FirebaseAuth mAuth;
    private String mCurrent_user_id;


    public TPOMemberListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_tpomember_list, container, false);

        mTPOMemberList = (RecyclerView) mMainView.findViewById(R.id.tpo_member_list);
        mTPODatabase = FirebaseDatabase.getInstance().getReference().child("TPO_Member");

        mTPOMemberList.setHasFixedSize(true);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTPOMemberList.setLayoutManager(linearVertical);

        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<TPOMembers, TPOMemberListFragment.TPOMemberViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<TPOMembers, TPOMemberViewHolder>(

                TPOMembers.class,
                R.layout.tpomember_single_layout,
                TPOMemberListFragment.TPOMemberViewHolder.class,
                mTPODatabase) {
            @Override
            protected void populateViewHolder(final TPOMemberListFragment.TPOMemberViewHolder tpoMemberViewHolder, TPOMembers tpoMembers, int i) {

                tpoMemberViewHolder.setDate(tpoMembers.getDate());

                final String list_user_id = getRef(i).getKey();

                mTPODatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String tpoMemberName = dataSnapshot.child("name").getValue().toString();
                        String tpoDesignation = dataSnapshot.child("role").getValue().toString();
                        String tpoEmail = dataSnapshot.child("email").getValue().toString();
                        final String tpoMobile = dataSnapshot.child("mobile").getValue().toString();


                        tpoMemberViewHolder.setTPOMemberName(tpoMemberName);
                        tpoMemberViewHolder.setDesignation(tpoDesignation);
                        tpoMemberViewHolder.setEmail(tpoEmail);
                        tpoMemberViewHolder.setPhone(tpoMobile);
                        tpoMemberViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence [] options;
                                options = new CharSequence[]{"Call Member"};

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Select Options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        //Click Event for each item.

                                        if (i == 0) {


                                            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                                Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tpoMobile));
                                                // callIntent.setData(Uri.parse("tel:"+uri));
                                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getActivity().startActivity(callIntent);

                                            }

                                        }

                                    }
                                });

                                builder.show();
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        mTPOMemberList.setAdapter(friendsRecyclerViewAdapter);


    }

    public static class TPOMemberViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TPOMemberViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTPOMemberName(String name){
            TextView userStatusView = (TextView) mView.findViewById(R.id.tpo_member_name);
            userStatusView.setText(name.toUpperCase());
        }
        public void setDesignation(String designation){

            TextView userNameView = (TextView) mView.findViewById(R.id.tpo_designation);
            userNameView.setText(designation.toUpperCase());
        }
        public void setPhone(String phone){

            TextView userNameView = (TextView) mView.findViewById(R.id.tpo_mobile);
            userNameView.setText(phone);
        }
        public void setEmail(String email) {

            TextView userNameView = (TextView) mView.findViewById(R.id.tpo_email);
            userNameView.setText(email);
        }
        public void setDate(String date){


        }
    }

}
