package com.apsit.apsittpo.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apsit.apsittpo.MainActivity;
import com.apsit.apsittpo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTpoMemberFragment extends Fragment {
    private View mMainView;
    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mAddress;
    private EditText mMobile;
    private EditText mRole;
    private EditText mQualification;

    private Button mCreateBtn;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private String tpouser;

    //progress dialog
    private ProgressDialog mRegProgress;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseTPOMember;

    String display_name;
    String email;
    String mobile;
    String password;
    String address;
    String role;
    String qualification;

    public AddTpoMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_add_tpo_member, container, false);

        mAuth = FirebaseAuth.getInstance();

        mRegProgress=new ProgressDialog(getActivity());

        mDisplayName=(EditText) mMainView.findViewById(R.id.tporeg_username);
        mEmail=(EditText)mMainView.findViewById(R.id.tporeg_email);
        mPassword=(EditText)mMainView.findViewById(R.id.tporeg_pwd);
        mMobile=(EditText)mMainView.findViewById(R.id.tporeg_mobile);
        mAddress=(EditText)mMainView.findViewById(R.id.tporeg_address);
        mRole=(EditText)mMainView.findViewById(R.id.tporeg_role);
        mQualification=(EditText)mMainView.findViewById(R.id.tporeg_qualification);

        mCreateBtn=(Button)mMainView.findViewById(R.id.register);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_name=mDisplayName.getText().toString();
                email=mEmail.getText().toString();
                password=mPassword.getText().toString();
                mobile=mMobile.getText().toString();
                address=mAddress.getText().toString();
                role=mRole.getText().toString();
                qualification=mQualification.getText().toString();

                if(!TextUtils.isEmpty(display_name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)||!TextUtils.isEmpty(mobile)||!TextUtils.isEmpty(address) || !TextUtils.isEmpty(role) || !TextUtils.isEmpty(qualification)){
                    mRegProgress.setTitle("Adding User");
                    mRegProgress.setMessage("Please wait");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_tpo_member(display_name,email,password);
                } else {
                    Toast.makeText(getActivity(),"Please Enter All Fields",Toast.LENGTH_LONG).show();
                }

            }
        });
        return mMainView;
    }

    private void register_tpo_member(final String display_name, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            tpouser = authResult.getUser().getUid();
                            //Log.d("Authuserid",tpouser);

//                    FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
//                    String uid=current_user.getUid();
//                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    //user.sendEmailVerification();
                    //mEmailverification.setTitle("Check your email and verify it");
                    //mEmailverification.setMessage("Verifying...");
                    // mEmailverification.show();
                    //Boolean emailVerfied=user.isEmailVerified();
                    //Log.e("Success", String.valueOf(emailVerfied));


                    mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(tpouser);
                    mDatabaseTPOMember = FirebaseDatabase.getInstance().getReference().child("TPO_Member").child(tpouser);

                    //String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("password",password);
                    userMap.put("type", "tpomember");


                    final HashMap<String, String> studentMap = new HashMap<>();
                    studentMap.put("name", display_name);
                    studentMap.put("email", email);
                    studentMap.put("mobile",mobile);
                    studentMap.put("place",address);
                    studentMap.put("role", role);
                    studentMap.put("qualification", qualification);

                    mRegProgress.dismiss();
                    mDatabaseTPOMember.setValue(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(),"Registered Successfully..!",Toast.LENGTH_LONG).show();
                        }
                    });
                    mDatabaseUsers.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mRegProgress.dismiss();
                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                            startActivity(mainIntent);
                            Toast.makeText(getActivity(),"TPO Member Added",Toast.LENGTH_LONG).show();


                        }

                    });
                        }
                    });

                }
                else{
                    mRegProgress.hide();
                    Toast.makeText(getActivity(),"Authentication failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
