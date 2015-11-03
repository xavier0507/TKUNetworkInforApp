package com.example.tkunetworkapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tkunetworkapp.R;
import com.example.tkunetworkapp.beans.MyDataResult;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by xavier_yin on 15/11/1.
 */
public class LandscapeListAdapter extends BaseListAdapter<MyDataResult.ResultItem> {
	public LandscapeListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.list_item_landscape, null);
		}

		final MyDataResult.ResultItem resultItem = this.getItem(position);

		ImageView landscapeImageView = (ImageView) convertView.findViewById(R.id.imgv_landscpae);
		TextView parkNameText = (TextView) convertView.findViewById(R.id.text_park_name);
		TextView landscapeNameText = (TextView) convertView.findViewById(R.id.text_landscape_name);
		TextView openTimeText = (TextView) convertView.findViewById(R.id.text_open_time);

		Picasso.with(convertView.getContext()).load(resultItem.getImage()).placeholder(R.drawable.ic_loading).into(landscapeImageView);

		parkNameText.setText(this.context.getString(R.string.custom_text_park_name, resultItem.getParkName()));
		landscapeNameText.setText(this.context.getString(R.string.custom_text_landscape_name, resultItem.getName()));
		openTimeText.setText(this.context.getString(R.string.custom_text_open_time, resultItem.getOpenTime()));

		convertView.setTag(resultItem);

		return convertView;
	}
}
