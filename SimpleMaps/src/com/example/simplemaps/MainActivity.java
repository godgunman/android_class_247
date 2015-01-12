package com.example.simplemaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MapFragment mapFragment;
	private GoogleMap googleMap;
	private EditText editText;
	
	private final static LatLng NTU_POSITION = new LatLng(25.017317, 121.539586);
	private final static LatLng YANG_MING_SHAN = new LatLng(25.194367,
			121.560957);
	private static final String CLIENT_ID = "S1LC42PP1ZRDU5VWZIIZBIVOVACP4DXX0R5SVSXBQHJS3UP1";
	private static final String CLIENT_SECRET = "FCB500VP5QGJEH3GYNHRICNMLZMWXLEOOM0FK30ET5RHTIUC";

	private String currentLocation = "25.017317,121.539586";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		disableStrictMode();

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		googleMap = mapFragment.getMap();

		editText = (EditText) findViewById(R.id.editText1);
		
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

		initLocationManager();
	}

	public void search(View view) {
		
		String searchString = editText.getText().toString();
	
		String queryUrl = String
				.format("https://api.foursquare.com/v2/venues/search?client_id=%s&client_secret=%s&v=20130815&ll=%s&query=%s",
						CLIENT_ID, CLIENT_SECRET, currentLocation,
						searchString);

		task.execute(queryUrl);
	}
	
	public void goToYangMingShan(View view) {
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YANG_MING_SHAN,
				15));
	}

	private void initLocationManager() {
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		String provider = lm.getBestProvider(criteria, true);
		updateGoogleMap(lm.getLastKnownLocation(provider));
	
		lm.requestLocationUpdates(provider, 1000, 1, locationListener);
		
		updateGoogleMap(googleMap.getMyLocation());		
	}
	
	private void updateGoogleMap(Location location) {
		if (location == null) return;
		
		currentLocation = location.getLatitude() + "," + location.getLongitude();
		
		LatLng newLocation = new LatLng(location.getLatitude(), location.getLongitude());
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15));
	}
	
	private void disableStrictMode() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
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
	
	private AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String> () {

		private ProgressDialog progress;
		
		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(MainActivity.this);
			progress.setTitle("Loading");
			progress.show();
		};
		
		@Override
		protected String doInBackground(String... params) {
			return fetch(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {			
			try {
				JSONObject object = new JSONObject(result);
				JSONArray venuse = object.getJSONObject("response").getJSONArray(
						"venues");
				for (int i = 0; i < venuse.length(); i++) {
					String name = venuse.getJSONObject(i).getString("name");
					double lat = venuse.getJSONObject(i).getJSONObject("location")
							.getDouble("lat");
					double lng = venuse.getJSONObject(i).getJSONObject("location")
							.getDouble("lng");

					MarkerOptions place = new MarkerOptions().position(
							new LatLng(lat, lng)).title(name);
					googleMap.addMarker(place);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			progress.dismiss();
		};
		
	};
	
	private LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			updateGoogleMap(location);
		}

	};
}
