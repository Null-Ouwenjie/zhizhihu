package com.ouwenjie.zhizhihu.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.entity.Post;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 文杰 on 2015/10/18.
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<Post> mPostList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public PostListAdapter(Activity activity, List<Post> postList) {
        this.mContext = activity;
        this.mPostList = postList;
    }

    public PostListAdapter(Fragment fragment, List<Post> postList) {
        this.mContext = fragment.getContext();
        this.mPostList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post_list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        String date = post.getDate();
        String name = post.getName();
        String picUrl = post.getPic();
        String publishtime = post.getPublishtime();
        int count = post.getCount();
        String excerpt = post.getExcerpt();

        Glide.with(mContext)
                .load(picUrl)
                .into(holder.postPicImg);

        switch (name) {
            case "recent":
                name = "近日热门";
                break;
            case "yesterday":
                name = "昨日最新";
                break;
            case "archive":
                name = "历史精华";
                break;
            default:
                break;
        }

        String txt = date + "  " + name;
        holder.dateAndNameTxt.setText(txt);
        holder.excerptTxt.setText(excerpt);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.post_pic_img)
        ImageView postPicImg;
        @Bind(R.id.date_and_name_txt)
        TextView dateAndNameTxt;
        @Bind(R.id.excerpt_txt)
        TextView excerptTxt;
        @Bind(R.id.layout_item_root)
        CardView layoutItemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
