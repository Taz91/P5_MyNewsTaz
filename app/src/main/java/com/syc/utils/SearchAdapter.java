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
import com.syc.models.SearchDoc;
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
 * Adapter of DetailActivity, with Search model,
 * launch a webview
 *
 */
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
    //For WebView
    private Context context;

    public SearchAdapter(List<SearchDoc> myNews, RequestManager glide, Context context) {
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
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);

        SearchAdapter.MyViewHolder vh = new SearchAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, int position) {
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("articleUrl", n.getWebUrl());
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