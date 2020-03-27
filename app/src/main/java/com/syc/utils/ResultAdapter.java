package com.syc.utils;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.syc.DetailActivity;
import com.syc.R;
import com.syc.models.TopResult;
import java.util.List;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.syc.utils.Utils.setSharedArticlesViewed;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    //list of news
    private List<TopResult> myNews;
    //For WebView
    private Context context;
    //Glide object
    private RequestManager glide;
    /**
     * Glide image traitement
     */
    private RequestOptions options = new RequestOptions()
                .override(75,75)
                .placeholder(R.drawable.baseline_error_outline_black_48)
                .error(R.drawable.baseline_error_outline_black_48);
    private String imgUrl;

    /**
     * constructor
     * @param myNews
     * @param glide
     * @param context
     */
    public ResultAdapter(List<TopResult> myNews, RequestManager glide, Context context ) {
        this.myNews = myNews;
        this.glide = glide;
        this.context = context;
    }

    //TODO : comprendre le but du MyViewHolder et mettre un commentaire
    /**
     *
     */
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
        TopResult n = myNews.get(position);
        // binding ItemView
        //picture
        try{
            //Multimedia can be empty
            imgUrl = n.getMultimedia().get(0).getUrl();
        }catch ( Exception e){
            //Multimedia is Null : load replacement image
            imgUrl = "R.drawable.baseline_error_outline_black_48";
        }finally {
            glide
                .applyDefaultRequestOptions(options)
                .load(imgUrl)
                .into(holder.itemImg);
        }
        holder.itemTitle.setText(n.getTitle());
        holder.itemDate.setText(n.getDate());
        holder.itemCategory.setText(n.getCategory());

        /**
         * Intercept click on img for read article with webView
         * prepare add in ArticlesViewed
         */
        holder.itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("articleUrl", n.getUrl());
                // get id part of uri for example "https://nyti.ms/2GQvc0A" like "2GQvc0A"

                String maVar = n.getUri() ;
                maVar = n.getUri().substring(n.getUri().lastIndexOf("/")) ;


                setSharedArticlesViewed(n.getUri().substring(n.getUri().lastIndexOf("/")));

                //intent.putExtra("articleId",n.getUri().substring(n.getUri().lastIndexOf(":")));
                ContextCompat.startActivity(context,intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.myNews.size();
    }
}