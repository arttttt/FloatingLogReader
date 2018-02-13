package com.android.arttt.floatinglogreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter {

    ArrayList<LogItem> mStrings;

    public LogAdapter() {
        mStrings = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);

        return new LogViewHolder(v);
    }

    public void addItem(LogItem item) {
        if (mStrings.size() > 1024)
            mStrings.remove(0);
        mStrings.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LogViewHolder) holder).mTextView.setText(mStrings.get(position).message);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public LogViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.itemText);
        }
    }
}
