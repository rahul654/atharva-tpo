package com.apsit.apsittpo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apsit.apsittpo.Fragment.AddTpoMemberFragment;
import com.apsit.apsittpo.Fragment.CompanyListFragment;
import com.apsit.apsittpo.Fragment.StudentListFragment;
import com.apsit.apsittpo.Fragment.TPOMemberListFragment;
import com.apsit.apsittpo.Fragment.ViewUserProfileFragment;

/**
 * Created by MANIKANDAN on 13-08-2017.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{
    private String user_type;

    public SectionsPagerAdapter(FragmentManager fm, String usertype) {
        super(fm);
        user_type = usertype;

    }

    @Override
    public Fragment getItem(int position) {
        System.out.println(user_type);
        if(user_type.equals("student")) {
            switch (position) {
                case 0: CompanyListFragment companyListFragment = new CompanyListFragment();
                    return companyListFragment;

                case 1:
                    TPOMemberListFragment tpoMemberListFragment = new TPOMemberListFragment();
                    return tpoMemberListFragment;

                case 2:
                    ViewUserProfileFragment viewUserProfileFragment = new ViewUserProfileFragment();
                    return viewUserProfileFragment;

                default:
                    return null;
            }
        } else if(user_type.equals("tpomember")) {
            switch (position) {

                case 0:
                    StudentListFragment studentListFragment = new StudentListFragment();
                    return studentListFragment;
                case 1:
                    CompanyListFragment companyListFragment = new CompanyListFragment();
                    return companyListFragment;
                case 2:
                    TPOMemberListFragment tpoMemberListFragment = new TPOMemberListFragment();
                    return tpoMemberListFragment;

                default:
                    return null;
            }
        } else {
            switch (position) {

                case 0:
                    StudentListFragment studentListFragment = new StudentListFragment();
                    return studentListFragment;
                case 1:
                    CompanyListFragment companyListFragment = new CompanyListFragment();
                    return companyListFragment;
                case 2:
                    TPOMemberListFragment tpoMemberListFragment = new TPOMemberListFragment();
                    return tpoMemberListFragment;
                case 3:
                    AddTpoMemberFragment addTpoMemberFragment = new AddTpoMemberFragment();
                    return addTpoMemberFragment;

                default:
                    return null;
            }
        }

//        switch (position) {
//
//            case 0:
//                StudentListFragment studentListFragment = new StudentListFragment();
//                return studentListFragment;
//            case 1:
//                CompanyListFragment companyListFragment = new CompanyListFragment();
//                return companyListFragment;
//
//            default:
//                return null;
//        }
    }

    @Override
    public int getCount() {
        if(user_type.equals("student")) {
            return 3;
        } else if(user_type.equals("tpomember")) {
            return 3;
        } else {
            return 4;
        }

       // return 2;

    }

    public CharSequence getPageTitle(int position){
        if(user_type.equals("student")) {
            switch (position) {
                case 0:
                    return "COMPANY LIST";

                case 1:
                    return "TPO MEMBERS LIST";

                case 2:
                    return "VIEW PROFILE";

                default: return null;
            }
        } else if(user_type.equals("tpomember")) {
            switch (position) {
                case 0:
                    return "STUDENTS LIST";
                case 1:
                    return "COMPANY LIST";
                case 2:
                    return "TPO MEMBERS LIST";
                default:
                    return null;

            }
        } switch (position) {
            case 0:
                return "STUDENTS LIST";
            case 1:
                return "COMPANY LIST";
            case 2:
                return "TPO MEMBERS LIST";
            case 3:
                return "ADD TPO MEMBER";
            default:
                return null;

        }

//        switch (position) {
//            case 0:
//                return "STUDENTS LIST";
//            case 1:
//                return "COMPANY LIST";
//            default:
//                return null;
//
//        }

    }
}
