package com.android.arttt.floatinglogreader;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    ArrayList<LogItem> mMessages;

    public LogAdapter() {
        mMessages = new ArrayList<>();
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);

        return new LogViewHolder(v);
    }

    public void addItem(LogItem item) {
        mMessages.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        TextView messageTextView = holder.mMessageTextView;
        TextView ownerTextView = holder.mOwnerTextView;

        LogItem message = mMessages.get(position);

        switch (message.level) {
            case Log.DEBUG: {
                ownerTextView.setTextColor(Color.BLUE);
                break;
            }
            case Log.INFO: {
                ownerTextView.setTextColor(Color.GREEN);
                break;
            }
            case Log.WARN: {
                ownerTextView.setTextColor(Color.CYAN);
                break;
            }
            case 100:
            case Log.ERROR: {
                ownerTextView.setTextColor(Color.RED);
                break;
            }
        }

        ownerTextView.setText(message.owner);
        messageTextView.setText(message.message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView mMessageTextView;
        public TextView mOwnerTextView;

        public LogViewHolder(View v) {
            super(v);
            mMessageTextView = v.findViewById(R.id.message);
            mOwnerTextView = v.findViewById(R.id.messageOwner);
        }
    }
}
