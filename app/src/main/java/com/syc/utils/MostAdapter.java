package com.syc.utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.syc.DetailActivity;
import com.syc.R;
import com.syc.models.MostResult;
import java.util.List;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.syc.utils.Utils.addSharedArticlesViewed;
import static com.syc.utils.Utils.getSharedArticlesViewed;
import static com.syc.utils.Utils.getnArticlesMax;
import static com.syc.utils.Utils.isArticleViewed;
import static com.syc.utils.Utils.setSharedArticlesViewed;

/**
 * Created by Chazette Sylvain
 * Adapter of MostPopularFragment, with Most model,
 * Click on image launch DetailActivity
 * save the link of the article seen
 *
 */
public class MostAdapter extends RecyclerView.Adapter<MostAdapter.MyViewHolder> {
    //list of news
    private List<MostResult> myNews;
    //For WebView
    private Context context;
    //Declare Glide object
    private RequestManager glide;
    private RequestOptions options = new RequestOptions()
                .override(75,75)
                .placeholder(R.drawable.baseline_error_outline_black_48)
                .error(R.drawable.baseline_error_outline_black_48);
    private String imgUrl;

    public MostAdapter(List<MostResult> myNews, RequestManager glide, Context context) {
        this.myNews = myNews;
        this.glide = glide;
        this.context = context;
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
        MostResult n = myNews.get(position);
        // binding ItemView
        //picture
        try{
            //Multimedia can be empty
            imgUrl = n.getMedia().get(0).getMediaMetadata().get(0).getUrl();
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

        String article = n.getUri().substring(n.getUri().lastIndexOf("/"));
        String articles = Utils.getSharedArticlesViewed();
        if (isArticleViewed(article,articles)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#dbdce0"));
        } else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        /**
         * Intercept click on img for read article with webView
         */
        holder.itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("articleUrl", n.getUrl());
                //addSharedArticlesViewed(n.getUri().substring(n.getUri().lastIndexOf("/")));
                Integer nbMaxArticlesViewed = getnArticlesMax();
                String articlesViewed = getSharedArticlesViewed() ;
                String sharedArticlesViewed = addSharedArticlesViewed( articlesViewed, n.getUri().substring(n.getUri().lastIndexOf("/")), nbMaxArticlesViewed);
                setSharedArticlesViewed(sharedArticlesViewed);
                ContextCompat.startActivity(context,intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.myNews.size();
    }
}