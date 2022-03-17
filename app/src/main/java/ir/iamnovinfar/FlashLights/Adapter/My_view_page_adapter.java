package ir.iamnovinfar.FlashLights.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ir.iamnovinfar.FlashLights.Fragment.Slide_1;
import ir.iamnovinfar.FlashLights.Fragment.Slide_2;
import ir.iamnovinfar.FlashLights.Fragment.Slide_3;

public class My_view_page_adapter extends FragmentStatePagerAdapter {


    public My_view_page_adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
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
    public int getCount() {
        return 3;
    }
}
