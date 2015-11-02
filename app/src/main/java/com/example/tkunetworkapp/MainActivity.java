package com.example.tkunetworkapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tkunetworkapp.adapters.BaseListAdapter;
import com.example.tkunetworkapp.adapters.LandscapeListAdapter;
import com.example.tkunetworkapp.beans.MyDataResult;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	// UI Components
	private ProgressBar progressLoading;
	private Button listContentButton;
	private ListView contentListView;
	private BaseListAdapter<MyDataResult.ResultItem> resultItemBaseListAdapter;

	// The working queue of Volley
	private RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("TKU簡易網路資料瀏覽App");
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		this.execute();
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_data) {
		} else if (id == R.id.nav_browse) {
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	/*
	 * Helper Methods
	 */
	private void execute() {
		this.initApiRequestQueue();
		this.prepareUI();
		this.prepareEvents();
		this.invokeStringRequest();
	}

	private void initApiRequestQueue() {
		this.requestQueue = Volley.newRequestQueue(this);
	}

	private void prepareUI() {
		this.progressLoading = (ProgressBar) findViewById(R.id.progress_loading);
		this.listContentButton = (Button) findViewById(R.id.btn_show_list_content);
		this.contentListView = (ListView) findViewById(R.id.list_content);
		this.resultItemBaseListAdapter = new LandscapeListAdapter(this);
	}

	private void prepareEvents() {
		this.listContentButton.setOnClickListener(this.buttonOnClickListener);
	}

	/*
	 * API Request Methods
	 */
	private void invokeStringRequest() {
		String requestURL = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=bf073841-c734-49bf-a97f-3757a6013812&limit=10";

		final StringRequest getDataRequest = new StringRequest(
				Request.Method.GET,
				requestURL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(final String response) {
						Gson gson = new Gson();
						MyDataResult myDataResult = gson.fromJson(response, MyDataResult.class);

						resultItemBaseListAdapter.clear();
						resultItemBaseListAdapter.addAll(myDataResult.getResult().getResults());
						contentListView.setAdapter(resultItemBaseListAdapter);

						progressLoading.setVisibility(View.GONE);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						progressLoading.setVisibility(View.GONE);
					}
				});
		new Thread(new Runnable() {
			@Override
			public void run() {
				progressLoading.setVisibility(View.VISIBLE);
			}
		}).start();
		requestQueue.add(getDataRequest);
	}

	/*
	 * Events
	 */
	private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			this.determineButton(v.getId());
		}

		private void determineButton(int viewId) {
			switch (viewId) {
				case R.id.btn_show_list_content:
					contentListView.setVisibility(View.VISIBLE);
					break;
			}
		}
	};
}
