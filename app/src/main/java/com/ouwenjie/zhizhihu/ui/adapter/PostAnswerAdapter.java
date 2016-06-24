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
import com.ouwenjie.zhizhihu.model.entity.Answer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by 文杰 on 2015/10/18.
 */
public class PostAnswerAdapter extends RecyclerView.Adapter<PostAnswerAdapter.ViewHolder> {

    List<Answer> answersList;
    List<Answer> favoriteList;
    Context context;
    OnItemClickListener onItemClickListener;

    public PostAnswerAdapter(Activity activity, List<Answer> answersList) {
        this.context = activity;
        this.answersList = answersList;
        favoriteList = Realm.getDefaultInstance().allObjects(Answer.class);
    }

    public PostAnswerAdapter(Fragment fragment, List<Answer> answersList) {
        this.context = fragment.getContext();
        this.answersList = answersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_answer_list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, (Integer) itemView.getTag());
                }
            }
        });
        itemView.findViewById(R.id.favorite_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onFavoriteClick(v, (Integer) itemView.getTag());
                }
            }
        });
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Answer answer = answersList.get(position);

        String title = answer.getTitle();
        String time = answer.getTime();
        String summary = answer.getSummary();
        String questionid = answer.getQuestionid();
        String answerid = answer.getAnswerid();
        String authorName = answer.getAuthorname();
        String authorhash = answer.getAuthorhash();
        String avatar = answer.getAvatar();
        String vote = answer.getVote();

        holder.questionTxt.setText(title);
        holder.answerTxt.setText(summary);
        holder.authorNameTxt.setText(authorName);
        holder.likesTxt.setText(vote);

        Glide.with(context)
                .load(avatar)
                .fitCenter()
                .crossFade()
                .into(holder.authorAvatarImg);

        boolean isFavorite = false;
        for (Answer favorite : favoriteList) {
            if (favorite.getAnswerid().equals(answerid)) {
                isFavorite = true;
                break;
            }
        }
        if (isFavorite) {
            Glide.with(context)
                    .load(R.drawable.ic_favorite_light)
                    .into(holder.favoriteImg);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_favorite_normal)
                    .into(holder.favoriteImg);
        }
    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.question_txt)
        TextView questionTxt;
        @Bind(R.id.author_avatar_img)
        CircleImageView authorAvatarImg;
        @Bind(R.id.answer_txt)
        TextView answerTxt;
        @Bind(R.id.author_name_txt)
        TextView authorNameTxt;
        @Bind(R.id.likes_txt)
        TextView likesTxt;
        @Bind(R.id.favorite_img)
        ImageView favoriteImg;
        @Bind(R.id.layout_item_root)
        CardView layoutItemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onFavoriteClick(View view, int position);
    }
}
