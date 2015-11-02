package com.example.tkunetworkapp;

import android.content.Intent;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tkunetworkapp.adapters.BaseGridAdapter;
import com.example.tkunetworkapp.adapters.BaseListAdapter;
import com.example.tkunetworkapp.adapters.LandscapeGridAdapter;
import com.example.tkunetworkapp.adapters.LandscapeListAdapter;
import com.example.tkunetworkapp.beans.MyDataResult;
import com.example.tkunetworkapp.listeners.CustomOnGlobalLayoutListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	// UI Components
	private ProgressBar progressLoading;
	private Button listContentButton;
	private Button gridContentButton;
	private Button largeContentButton;
	private ListView contentListView;
	private GridView contentGridView;
	private GridView contentLargeGridView;
	private BaseListAdapter<MyDataResult.ResultItem> resultItemBaseListAdapter;
	private BaseGridAdapter<MyDataResult.ResultItem> resultItemBaseGridAdapter;
	private BaseGridAdapter<MyDataResult.ResultItem> resultItemBaseLargeAdapter;

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
			Intent intent = new Intent(this, RawDataActivity.class);
			startActivity(intent);
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
		this.gridContentButton = (Button) findViewById(R.id.btn_show_grid_content);
		this.largeContentButton = (Button) findViewById(R.id.btn_show_large_content);
		this.contentListView = (ListView) findViewById(R.id.list_content);
		this.contentGridView = (GridView) findViewById(R.id.grid_content);
		this.contentLargeGridView = (GridView) findViewById(R.id.grid_large_content);
		this.resultItemBaseListAdapter = new LandscapeListAdapter(this);
		this.resultItemBaseGridAdapter = new LandscapeGridAdapter(this);
		this.resultItemBaseLargeAdapter = new LandscapeGridAdapter(this);
		this.contentListView.setAdapter(this.resultItemBaseListAdapter);
		this.contentGridView.setAdapter(this.resultItemBaseGridAdapter);
		this.contentLargeGridView.setAdapter(this.resultItemBaseLargeAdapter);

	}

	private void prepareEvents() {
		this.listContentButton.setOnClickListener(this.buttonOnClickListener);
		this.gridContentButton.setOnClickListener(this.buttonOnClickListener);
		this.largeContentButton.setOnClickListener(this.buttonOnClickListener);
		this.contentGridView.getViewTreeObserver().addOnGlobalLayoutListener(new CustomOnGlobalLayoutListener(this.resultItemBaseGridAdapter, this.contentGridView));
		this.contentLargeGridView.getViewTreeObserver().addOnGlobalLayoutListener(new CustomOnGlobalLayoutListener(this.resultItemBaseLargeAdapter, this.contentLargeGridView).setLargeImageMode(true));
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
						final MyDataResult myDataResult = gson.fromJson(response, MyDataResult.class);

						resultItemBaseListAdapter.clear();
						resultItemBaseGridAdapter.clear();
						resultItemBaseLargeAdapter.clear();

						resultItemBaseListAdapter.addAll(myDataResult.getResult().getResults());
						resultItemBaseGridAdapter.addAll(myDataResult.getResult().getResults());
						resultItemBaseLargeAdapter.addAll(myDataResult.getResult().getResults());


						resultItemBaseListAdapter.notifyDataSetChanged();
						resultItemBaseGridAdapter.notifyDataSetChanged();
						resultItemBaseLargeAdapter.notifyDataSetChanged();

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
					contentGridView.setVisibility(View.GONE);
					contentLargeGridView.setVisibility(View.GONE);
					break;

				case R.id.btn_show_grid_content:
					contentListView.setVisibility(View.GONE);
					contentGridView.setVisibility(View.VISIBLE);
					contentLargeGridView.setVisibility(View.GONE);
					break;

				case R.id.btn_show_large_content:
					contentListView.setVisibility(View.GONE);
					contentGridView.setVisibility(View.GONE);
					contentLargeGridView.setVisibility(View.VISIBLE);
					break;
			}
		}
	};
}
