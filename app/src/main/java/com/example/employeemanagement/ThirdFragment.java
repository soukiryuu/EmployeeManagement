package com.example.employeemanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by watanabehiroaki on 2018/03/29.
 */

public class ThirdFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View thirdView = inflater.inflate(R.layout.activity_sample, container, false);
        TextView textView = (TextView) thirdView.findViewById(R.id.sample_tv);
        textView.setText("3");

        return thirdView;
    }
}
