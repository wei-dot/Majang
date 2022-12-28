package com.majang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majang.R;

import java.util.ArrayList;

public class FuiRecyclerViewAdapter extends RecyclerView.Adapter<FuiRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Integer> mDataSet;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull ViewGroup parent) {
            super(parent);
            textView = (TextView) parent.findViewById(R.id.text_row_item_textView1);
        }

        public TextView getTextView() {
            return textView;
        }

    }

    public FuiRecyclerViewAdapter(Context context, ArrayList<Integer> mDataSet) {
        mContext = context;
        this.mDataSet = mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder((ViewGroup) view);
    }


    @Override
    public void onBindViewHolder(@NonNull FuiRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
