package com.brayooteck.youssef;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragmentbridge extends Fragment {
    public static ViewPager viewPager;
    Fragmentadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentbridge, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.fragmentcontainer);
        adapter = new Fragmentadapter(getChildFragmentManager(), 5);
        viewPager.setAdapter(adapter);
     //   viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {


                switch (viewPager.getCurrentItem()){

                    case 0:
                        mymainactivity.mainbottomNav.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        mymainactivity.mainbottomNav.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        mymainactivity.mainbottomNav.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        mymainactivity.notificationdot.setVisibility(View.INVISIBLE);
                        mymainactivity.mainbottomNav.getMenu().getItem(3).setChecked(true);
                        break;
                    case 4:
                        mymainactivity.mainbottomNav.getMenu().getItem(4).setChecked(true);
                        Log.i("Info", "account");
                        break;
                }
                Log.i("Info","pagechanged");

            }
        });

        return view;
    }

////////////////////////////////////////////////////////////////////////////////////Extras////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

}
