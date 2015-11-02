package com.example.tkunetworkapp.adapters;

import android.content.Context;

/**
 * Created by Xavier on 2015/11/2.
 */
public abstract class BaseGridAdapter<T> extends BaseListAdapter<T> {
	protected int productImageHeight = 0;

	public BaseGridAdapter(Context context) {
		super(context);
	}

	@Override
	public int getCount() {
		return (this.list == null) ? 0 : this.list.size();
	}

	public int getProductImageHeight() {
		return productImageHeight;
	}

	public void setProductImageHeight(int productImageHeight) {
		if (this.productImageHeight == productImageHeight) {
			return;
		}

		this.productImageHeight = productImageHeight;
		this.notifyDataSetChanged();
	}
}
