package com.example.simplemaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MapFragment mapFragment;
	private GoogleMap googleMap;
	private final static LatLng NTU_POSITION = new LatLng(25.017317, 121.539586);
	private final static LatLng YANG_MING_SHAN = new LatLng(25.194367,
			121.560957);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		googleMap = mapFragment.getMap();

		MarkerOptions markerOption = new MarkerOptions().position(NTU_POSITION)
				.title("NTU");
		googleMap.addMarker(markerOption);
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				String title = marker.getTitle();
				Toast.makeText(MainActivity.this, "title:" + title,
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		String result = fetch("https://api.foursquare.com/v2/venues/40a55d80f964a52020f31ee3");
		Log.d("debug", result);
	}

	public void goToYangMingShan(View view) {
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YANG_MING_SHAN,
				15));
	}

	private String fetch(String urlString) {

		try {
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
