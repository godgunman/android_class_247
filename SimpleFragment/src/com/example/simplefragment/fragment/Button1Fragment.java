package com.example.simplefragment.fragment;

import com.example.simplefragment.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Button1Fragment extends Fragment {

	public Button1Fragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_button1, container, false);
        return rootView;
    }

}
