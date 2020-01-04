package com.agilya.syc.tabbedactivity.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agilya.syc.tabbedactivity.R;
import com.agilya.syc.tabbedactivity.models.Result;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Result> myNews;

    public MyAdapter(List<Result> myNews) {
        this.myNews = myNews;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //public ImageView itemImg;
        public TextView itemCategory;
        public TextView itemDate;
        public TextView itemTitle;

        public MyViewHolder(View v) {
            super(v);
            //img
            itemCategory = v.findViewById(R.id.rvItemCategory);
            itemDate = v.findViewById(R.id.rvItemDate);
            itemTitle = v.findViewById(R.id.rvItemTitle);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Result n = myNews.get(position);

        holder.itemTitle.setText(n.getTitle());
        //holder.itemDate.setText(n.getPublishedDate());
        holder.itemDate.setText(n.getDate());
        holder.itemCategory.setText(n.getCategory());

    }

    @Override
    public int getItemCount() {
        return myNews.size();
    }
}