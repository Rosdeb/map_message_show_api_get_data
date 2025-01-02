package com.example.coffee.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffee.fragments.Home;
import com.example.coffee.fragments.Map;
import com.example.coffee.fragments.Message;

public class Viewpager_2 extends FragmentStateAdapter {
    public Viewpager_2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                Fragment home = new Home();
                return home;
            case 1:
                Fragment message=new Message();
                return message;
            case 2:
                Fragment location=new Map();
                return location;
            default:
                return null;

        }



    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
