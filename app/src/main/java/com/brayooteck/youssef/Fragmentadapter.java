package com.brayooteck.youssef;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.brayooteck.youssef.account.account;
import com.brayooteck.youssef.home.HomeFragment;
import com.brayooteck.youssef.notifications.NotificationFragment;
import com.brayooteck.youssef.pdfs.MainActivity;
import com.brayooteck.youssef.videos.videosfragment;
import com.brayooteck.youssef.mcq.mcqFragment;

public class Fragmentadapter extends FragmentPagerAdapter {
    private HomeFragment homeFragment;
    private videosfragment videos;
    private NotificationFragment notificationFragment;
    private MainActivity pdfsfragment;
    private account accountfragment;
    private mcqFragment mcqfragment;
    int mNoOfTabs;


    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    public Fragmentadapter(FragmentManager fm,int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
        Log.i("Info", "fragmentadapter");

    }

    @Override
    public Fragment getItem(int position) {



        switch(position)
        {

            case 0:
                pdfsfragment = new MainActivity();
                return pdfsfragment;
            case 1:
                mcqfragment = new mcqFragment();
                return mcqfragment;
            case 2:
                videos = new videosfragment();
                return videos;
            case 3:
                homeFragment = new HomeFragment();
                return homeFragment;
            case 4:
                notificationFragment = new NotificationFragment();
                return notificationFragment;
            case 5:
                accountfragment = new account();
                return accountfragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }

////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
