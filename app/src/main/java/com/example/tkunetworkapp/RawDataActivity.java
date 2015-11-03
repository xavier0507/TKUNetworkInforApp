package com.example.tkunetworkapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tkunetworkapp.beans.MyDataResult;
import com.google.gson.Gson;

/**
 * Created by Xavier on 2015/11/2.
 */
public class RawDataActivity extends AppCompatActivity {
	// UI Components
	private ProgressBar progressLoading;
	private Button jsonContentButton;
	private TextView jsonContentText;
	private Button parsedContentButton;
	private TextView parsedContentText;

	// The working queue of Volley
	private RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raw_data);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}

		return super.onOptionsItemSelected(item);
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
		this.jsonContentButton = (Button) findViewById(R.id.btn_json_content);
		this.jsonContentText = (TextView) findViewById(R.id.text_json_content);
		this.parsedContentButton = (Button) findViewById(R.id.btn_parsed_content);
		this.parsedContentText = (TextView) findViewById(R.id.text_parsed_content);
	}

	private void prepareEvents() {
		this.jsonContentButton.setOnClickListener(this.buttonOnClickListener);
		this.parsedContentButton.setOnClickListener(this.buttonOnClickListener);
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

						String tmpResult = "";
						for (MyDataResult.ResultItem resultItem : myDataResult.getResult().getResults()) {
							tmpResult += resultItem.get_id() + "\n" + resultItem.getParkName() + "\n" + resultItem.getIntroduction() + "\n\n";
						}

						jsonContentText.setText(response);
						parsedContentText.setText(tmpResult);

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
				case R.id.btn_json_content:
					jsonContentText.setVisibility(View.VISIBLE);
					parsedContentText.setVisibility(View.GONE);
					break;

				case R.id.btn_parsed_content:
					jsonContentText.setVisibility(View.GONE);
					parsedContentText.setVisibility(View.VISIBLE);
					break;
			}
		}
	};
}
