package com.example.simplefragment.fragment;

import com.example.simplefragment.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Button1Fragment extends Fragment {

	private Button button;

	public Button1Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_button1, container,
				false);
		button = (Button) rootView.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("debug", "[Button1Fragment] buton1");
				Toast.makeText(getActivity(), "[Button1Fragment] buton1",
						Toast.LENGTH_SHORT).show();
			}
		});

		return rootView;
	}

}
