package com.example.tkunetworkapp.listeners;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.example.tkunetworkapp.adapters.BaseGridAdapter;

/**
 * Created by Xavier on 2015/11/2.
 */
public class CustomOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
	private BaseGridAdapter adapter;
	private GridView gridView;

	private boolean isLargeImageMode;

	public CustomOnGlobalLayoutListener(BaseGridAdapter adapter, GridView gridView) {
		this.adapter = adapter;
		this.gridView = gridView;
		this.isLargeImageMode = false;
	}

	@Override
	public void onGlobalLayout() {
		if (this.adapter.getProductImageHeight() == 0) {
			final int columnWidth;

			if (!isLargeImageMode) {
				columnWidth = this.gridView.getWidth() / 2;
			} else {
				columnWidth = this.gridView.getWidth();
			}

			Log.d("onGlobalLayout", "onGlobalLayout::columnWidth: " + columnWidth);
			this.adapter.setProductImageHeight(columnWidth);
		}
	}

	public CustomOnGlobalLayoutListener setLargeImageMode(boolean isLargeImageMode) {
		this.isLargeImageMode = isLargeImageMode;
		return this;
	}
}
