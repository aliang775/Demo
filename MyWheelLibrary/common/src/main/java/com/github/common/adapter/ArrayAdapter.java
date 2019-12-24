package com.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ArrayAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected SparseArray<T> mData;

    public ArrayAdapter(Context context) {
        mContext = context;
        mData = null;
    }

    public void update(SparseArray<T> data, boolean append) {
        if (data == null) {
            return;
        }

        if (mData == null) {
            mData = data;
        }

        if (!append) {
            mData.clear();
            mData = data.clone();
        } else {
            for (int i = 0; i < data.size(); i++) {
                mData.append(mData.size(), data.get(i));
            }
        }
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        } else {
            return mData.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (position < 0 || position >= getCount()) {
            throw new IllegalStateException("couldn't get view at this position " + position);
        }
        if (mData != null) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
