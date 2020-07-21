package com.apsit.apsittpo.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apsit.apsittpo.R;
import com.apsit.apsittpo.StudentDetailsActivity;
import com.apsit.apsittpo.Students;
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
public class StudentListFragment extends Fragment {

    private RecyclerView mStudentList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;

    public StudentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_student_list, container, false);

        mStudentList = (RecyclerView)mMainView.findViewById(R.id.student_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        mUsersDatabase.keepSynced(true);


        mStudentList.setHasFixedSize(true);
        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
       mStudentList.setLayoutManager(linearVertical);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mStudentList.getContext(),
                linearVertical.getOrientation()
        );
        mStudentList.addItemDecoration(mDividerItemDecoration);

        // Inflate the layout for this fragment
        return mMainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Students, StudentsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Students, StudentsViewHolder>(

                Students.class,
                R.layout.users_single_layout,
                StudentsViewHolder.class,
                mUsersDatabase


        ) {
            @Override
            protected void populateViewHolder(final StudentsViewHolder studentsViewHolder, Students students, int i) {

                studentsViewHolder.setDate(students.getDate());

                final String list_user_id = getRef(i).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String college=dataSnapshot.child("college_name").getValue().toString();
                        String phone=dataSnapshot.child("mobile").getValue().toString();
                        String address=dataSnapshot.child("place").getValue().toString();
                            studentsViewHolder.setName(userName);
                            studentsViewHolder.setCollege(college);
                            studentsViewHolder.setAddress(address);
                            studentsViewHolder.setPhone(phone);
                            final String uri = phone;
                            studentsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    CharSequence options[] = new CharSequence[]{"View Details", "Call"};

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                    builder.setTitle("Select Options");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            //Click Event for each item.
                                            if (i == 0) {
                                            Intent profileIntent = new Intent(getContext(), StudentDetailsActivity.class);
                                            profileIntent.putExtra("user_id", list_user_id);
                                            startActivity(profileIntent);

                                            }

                                            if (i == 1) {
                                                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + uri));
                                                // callIntent.setData(Uri.parse("tel:"+uri));
                                                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getActivity().startActivity(callIntent);

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

        mStudentList.setAdapter(friendsRecyclerViewAdapter);


    }

    // viewholder class..

    public static class StudentsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public StudentsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setCollege(String college){
            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_college);
            userStatusView.setText(college.toUpperCase());
        }
        public void setName(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name.toUpperCase());
        }
        public void setPhone(String phone){

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_phone);
            userNameView.setText(phone);
        }
        public void setAddress(String address) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_address);
            address.toUpperCase();
            userNameView.setText(address.toUpperCase());
        }
        public void setDate(String date){


        }
    }

}
