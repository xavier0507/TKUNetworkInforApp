package com.example.tkunetworkapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tkunetworkapp.beans.MyDataResult;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Xavier on 2015/11/3.
 */
public class LandscapeActivity extends AppCompatActivity {
	private MyDataResult.ResultItem resultItem;

	private ImageView landscapeImageView;
	private TextView parkNameText;
	private TextView landscapeNameText;
	private TextView openTimeText;
	private TextView introText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landscape);
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
	private void receiveDateFromActivity() {
		this.resultItem = (MyDataResult.ResultItem) this.getIntent().getExtras().get("DATA");
	}

	private void execute() {
		this.receiveDateFromActivity();
		this.prepareUI();
	}

	private void prepareUI() {
		this.landscapeImageView = (ImageView) findViewById(R.id.imgv_landscape);
		this.parkNameText = (TextView) findViewById(R.id.text_park_name);
		this.landscapeNameText = (TextView) findViewById(R.id.text_landscape_name);
		this.openTimeText = (TextView) findViewById(R.id.text_open_time);
		this.introText = (TextView) findViewById(R.id.text_intro);
		this.showUI();
	}

	private void showUI() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		ViewGroup.LayoutParams params = this.landscapeImageView.getLayoutParams();
		params.height = width;
		this.landscapeImageView.setLayoutParams(params);
		Picasso.with(this).load(this.resultItem.getImage()).placeholder(R.drawable.ic_loading).into(this.landscapeImageView);

		this.parkNameText.setText(this.getString(R.string.custom_text_park_name, resultItem.getParkName()));
		this.landscapeNameText.setText(this.getString(R.string.custom_text_landscape_name, resultItem.getName()));
		this.openTimeText.setText(this.getString(R.string.custom_text_open_time, resultItem.getOpenTime()));
		this.introText.setText(this.getString(R.string.custom_text_intro, resultItem.getIntroduction()));
	}
}
