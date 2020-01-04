package com.syc.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.syc.R;
import com.syc.models.SearchDoc;
import com.syc.models.SearchMultimedium;
import java.util.Iterator;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    //list of news
    private List<SearchDoc> myNews;
    //Declare Glide object
    private RequestManager glide;
    private RequestOptions options = new RequestOptions()
            .override(75,75)
            .placeholder(R.drawable.baseline_error_outline_black_48)
            .error(R.drawable.baseline_error_outline_black_48);
    private String imgUrl;


    public SearchAdapter(List<SearchDoc> myNews, RequestManager glide) {
        this.myNews = myNews;
        this.glide = glide;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rvItemImg) ImageView itemImg;
        @BindView(R.id.rvItemCategory) TextView itemCategory;
        @BindView(R.id.rvItemDate) TextView itemDate;
        @BindView(R.id.rvItemTitle) TextView itemTitle;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
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
        SearchDoc n = myNews.get(position);
        imgUrl = "";
        // binding ItemView
        //picture
        try{
            imgUrl = "https://www.nytimes.com/" + n.getMultimedia().get(0).getUrl();
        }catch ( Exception e){
            //Multimedia is Null : load replacement image
            imgUrl = "R.drawable.baseline_error_outline_black_48";
        }finally {
            glide
                    .applyDefaultRequestOptions(options)
                    .load(imgUrl)
                    .into(holder.itemImg);
        }
        holder.itemTitle.setText(n.getLeadParagraph());
        holder.itemDate.setText(n.getDate());
        holder.itemCategory.setText(n.getCategory());
    }

    @Override
    public int getItemCount() {
        return this.myNews.size();
    }
}