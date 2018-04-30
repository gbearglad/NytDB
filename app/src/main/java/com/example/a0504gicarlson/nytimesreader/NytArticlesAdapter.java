package com.example.a0504gicarlson.nytimesreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a0504gicarlson.nytimesreader.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 0504gicarlson on 23/04/2018.
 */

public class NytArticlesAdapter extends RecyclerView.Adapter<NytArticlesAdapter.ArticleViewHolder> {
    private ArrayList<Article> articleArrayList;
    public void setArticleArrayList(ArrayList<Article> articles){
        this.articleArrayList = articles;
        this.notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{
        private ArticleViewHolder(View itemView){
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (articleArrayList != null){
            return articleArrayList.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View articleView = layoutInflater.inflate(R.layout.item_article, parent, false);

        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = articleArrayList.get(position);
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView titleView = itemViewGroup.findViewById(R.id.titleView);
        TextView abstractView = itemViewGroup.findViewById(R.id.abstractView);
        ImageView imageView = itemViewGroup.findViewById(R.id.imageView);
        titleView.setText(article.getTitle());
        abstractView.setText(article.getArticleAbstract());
        Picasso.get().load(article.getImageURL()).into(imageView);

    }


}
