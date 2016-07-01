package com.ouwenjie.zhizhihu.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.api.TopUserType;
import com.ouwenjie.zhizhihu.model.entity.TopUser;
import com.ouwenjie.zhizhihu.presenter.TopUserListPresenter;
import com.ouwenjie.zhizhihu.ui.activity.UserProfileActivity;
import com.ouwenjie.zhizhihu.ui.viewInterface.TopUserListViewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class TopUserListFragment extends SwipeRefreshFragment implements TopUserListViewInterface {

    private static final String TOP_USER_TYPE = "TopUserType";

    @Bind(R.id.top_user_list_view)
    RecyclerView mTopUserListView;

    private List<TopUser> mTopUsers = new ArrayList<>();
    private TopUserType mTopUserType;

    private TopUserListPresenter mPresenter;

    public TopUserListFragment() {
        // Required empty public constructor
    }

    public static TopUserListFragment newInstance(TopUserType type) {
        TopUserListFragment fragment = new TopUserListFragment();
        Bundle args = new Bundle();
        args.putString(TOP_USER_TYPE, type.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTopUserType = TopUserType.valueOf(getArguments().getString(TOP_USER_TYPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_user_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String reqType = mTopUserType.toString().toLowerCase();
        Log.e("reqTpye=>", reqType);

        mPresenter = new TopUserListPresenter(this);
        mPresenter.create();
        mPresenter.loadTopUserData(reqType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }


    @Override
    public void createUserListView(final List<TopUser> users) {
        mTopUsers = users;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Adapter adapter = new Adapter(getContext(), mTopUsers);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(UserProfileActivity
                        .getStartIntent(getContext(), users.get(position).getHash()));
            }
        });

        mTopUserListView.setLayoutManager(layoutManager);
        mTopUserListView.setAdapter(adapter);

    }


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        List<TopUser> topUserList;
        Context context;
        OnItemClickListener onItemClickListener;

        public Adapter(Context context, List<TopUser> topUserList) {
            this.context = context;
            this.topUserList = topUserList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_top_user_item, parent, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, (Integer) v.getTag());
                    }
                }
            });
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setTag(position);

            TopUser topUser = topUserList.get(position);

            Glide.with(context)
                    .load(topUser.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatarImg);

            holder.topUserNameTxt.setText(topUser.getName());
            holder.topUserSignatureTxt.setText(topUser.getSignature());

            @DrawableRes int drawableLeft = R.drawable.ic_rise_blue_64;
            String textRight = "";
            String value = "";
            switch (mTopUserType) {
                case agreeiw:       // 赞数飙升
                    textRight = "赞";
                    value = topUser.getAgreeiw();
                    drawableLeft = R.drawable.ic_rise_blue_64;
                    break;
                case followeriw:    // 粉丝飙升
                    textRight = "粉丝";
                    value = topUser.getFolloweriw();
                    drawableLeft = R.drawable.ic_rise_red_64;
                    break;
                case agree:         // 最多赞
                    textRight = "个赞";
                    value = topUser.getAgree();
                    drawableLeft = R.drawable.ic_like_64;
                    break;
                case follower:      // 最多粉
                    textRight = "个粉丝";
                    value = topUser.getFollower();
                    drawableLeft = R.drawable.ic_fans_64;
                    break;
                case ask:           // 问题最多
                    textRight = "次提问";
                    value = topUser.getAsk();
                    drawableLeft = R.drawable.ic_ask_64;
                    break;
                case answer:        // 懂得最多
                    textRight = "次回答";
                    value = topUser.getAnswer();
                    drawableLeft = R.drawable.ic_answer_64;
                    break;
                case post:          // 专栏榜
                    textRight = "篇";
                    value = topUser.getPost();
                    drawableLeft = R.drawable.ic_post_64;
                    break;
                case thanks:        // 收到感谢
                    textRight = "次感谢";
                    value = topUser.getThanks();
                    drawableLeft = R.drawable.ic_thanks_64;
                    break;
                case fav:           // 人人收藏
                    textRight = "次收藏";
                    value = topUser.getFav();
                    drawableLeft = R.drawable.ic_fav_64;
                    break;
                default:
                    break;
            }

            holder.topValueTxt.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
            holder.topValueTxt.setText(String.format("%s %s", value, textRight));

        }

        @Override
        public int getItemCount() {
            return topUserList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.avatar_img)
            CircleImageView avatarImg;
            @Bind(R.id.top_value_txt)
            TextView topValueTxt;
            @Bind(R.id.top_user_name_txt)
            TextView topUserNameTxt;
            @Bind(R.id.top_user_signature_txt)
            TextView topUserSignatureTxt;
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

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
