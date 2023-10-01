package com.brayooteck.youssef;

import androidx.fragment.app.Fragment;

public class Rootfragment extends Fragment implements OnBackPressListener {
    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }

}
