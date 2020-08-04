package com.example.amirnovinfar.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.amirnovinfar.Fragment.Slide_1;
import com.example.amirnovinfar.Fragment.Slide_2;
import com.example.amirnovinfar.Fragment.Slide_3;

public class My_view_page_adapter extends FragmentStateAdapter {


    public My_view_page_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
     switch (position){

         case 0:
             fragment=new Slide_1();
             break;
         case 1:
             fragment=new Slide_2();
             break;
             case 2:
             fragment=new Slide_3();
             break;



     }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }






}
