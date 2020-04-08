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
import com.syc.models.BusinessDoc;
import java.util.List;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.syc.utils.Utils.addSharedArticlesViewed;
import static com.syc.utils.Utils.isArticleViewed;

/**
 * Created by Chazette Sylvain
 * Adapter of BusinessFragment, with Business model,
 * Click on image launch DetailActivity
 * save the link of the article seen
 *
 */
public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyViewHolder> {
    //list of news
    private List<BusinessDoc> myNews;
    //Declare Glide object
    private RequestManager glide;
    private RequestOptions options = new RequestOptions()
            .override(75,75)
            .placeholder(R.drawable.baseline_error_outline_black_48)
            .error(R.drawable.baseline_error_outline_black_48);
    private String imgUrl;
    //For WebView
    private Context context;

    public BusinessAdapter(List<BusinessDoc> myNews, RequestManager glide, Context context) {
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
        BusinessDoc n = myNews.get(position);
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
        //Boolean bOk = isArticleViewed(monArticle);
        if (isArticleViewed(article)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#dbdce0"));
        }

        /**
         * Intercept click on img for read article with webView
         */
        holder.itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("articleUrl", n.getWebUrl());
                addSharedArticlesViewed(n.getUri().substring(n.getUri().lastIndexOf("/")));
                ContextCompat.startActivity(context,intent,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.myNews.size();
    }
}