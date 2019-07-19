package com.schedule.record.app.function;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.schedule.record.app.fragment.Calendar1Fragment;
import com.schedule.record.app.fragment.Calendar2Fragment;
import com.schedule.record.app.fragment.Calendar3Fragment;

import java.util.ArrayList;

public class FragmentCalendarController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentCalendarController controller;

    public static FragmentCalendarController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new FragmentCalendarController(parentFragment, containerId);
        }
        return controller;
    }

    private FragmentCalendarController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new Calendar1Fragment());
        fragments.add(new Calendar2Fragment());
        fragments.add(new Calendar3Fragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

//    public void showFragment1() {
//        hideFragments();
//        Fragment fragment = new  Calendar1Fragment();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.show(fragment);
//        ft.commit();
//    }
    public void showFragment2() {
        Calendar2Fragment fragment = new Calendar2Fragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }
    public void showFragment3() {
        Calendar3Fragment fragment = new Calendar3Fragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
