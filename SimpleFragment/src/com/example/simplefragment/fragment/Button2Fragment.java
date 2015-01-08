package com.example.simplefragment.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.simplefragment.R;

public class Button2Fragment extends Fragment {

	private Button button1;
	private Button button2;
	
    public Button2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_button2, container, false);
        button1 = (Button) rootView.findViewById(R.id.button1);
        button2 = (Button) rootView.findViewById(R.id.button2);
        
        button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("debug", "[Button2Fragment] buton1");
				Toast.makeText(getActivity(), "[Button2Fragment] buton1",
						Toast.LENGTH_SHORT).show();
	
			}
		});
        
        button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("debug", "[Button2Fragment] buton2");
				Toast.makeText(getActivity(), "[Button2Fragment] buton2",
						Toast.LENGTH_SHORT).show();
				
			}
		});

        
        return rootView;
    }

}
