package com.example.tkunetworkapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xavier_yin on 15/11/1.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected final List<T> list;
    protected Context context;
    protected final LayoutInflater layoutInflater;

    public BaseListAdapter(Context context) {
        super();
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (this.list == null) ? 0 : this.list.size();
    }

    @Override
    public T getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        this.list.clear();
    }

    public boolean addAll(List<? extends T> data) {
        return this.list.addAll(data);
    }
}
