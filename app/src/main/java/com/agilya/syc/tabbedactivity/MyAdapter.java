package com.agilya.syc.tabbedactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<New> myNews;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descTextView;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.textView);
            descTextView = v.findViewById(R.id.textView2);
        }
    }

    public MyAdapter(List<New> myNews) {

        this.myNews = myNews;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        New n = myNews.get(position);

        holder.titleTextView.setText(n.getTitle());
        holder.descTextView.setText(n.getDescription());

    }

    @Override
    public int getItemCount() {
        return myNews.size();
    }
}