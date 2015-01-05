package com.example.simpleui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.simpleui.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerFragment extends Fragment {

	private Spinner spinner;
	
	public SpinnerFragment() {
	}

	private void loadDeviceId() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceId");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				List<String> ids = new ArrayList<String>();
				for (ParseObject object : objects) {
					if (object.containsKey("deviceId"))
						ids.add(object.getString("deviceId"));
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_spinner_item, ids);
				spinner.setAdapter(adapter);
			}
		});
	}
	
	public String getSelectedDeviceId() {
		return (String) spinner.getSelectedItem();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_spinner, container, false);
		spinner = (Spinner) rootView.findViewById(R.id.spinner1);
		
		loadDeviceId();
		
		return rootView;
	}
}
