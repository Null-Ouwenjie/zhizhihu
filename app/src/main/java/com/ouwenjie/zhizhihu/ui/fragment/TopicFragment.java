package com.ouwenjie.zhizhihu.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.model.entity.TabData;
import com.ouwenjie.zhizhihu.model.entity.Topic;
import com.ouwenjie.zhizhihu.ui.activity.WebBrowserActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 精选 tab
 */
public class TopicFragment extends Fragment {

    public static final String KEY_TAB_TXT = "topic_tab_txt";

    @Bind(R.id.topic_list)
    RecyclerView mTopicList;

    private List<Topic> mTopics;

    public TopicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PostFragment.
     */
    public static TopicFragment newInstance(String tabTxt) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TAB_TXT, tabTxt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String tabTxt = getArguments().getString(KEY_TAB_TXT);

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        getContext().getAssets().open("topic.json"), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                String json = stringBuilder.toString();

                Gson gson = new Gson();
                List<TabData> tabDatas = gson.fromJson(json, new TypeToken<List<TabData>>() {
                }.getType());
                for (TabData tabData : tabDatas) {
                    if (tabData.getTabTxt().equals(tabTxt)) {
                        mTopics = tabData.getTopics();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTopics == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Adapter adapter = new Adapter(getContext(), mTopics);
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), WebBrowserActivity.class);
                intent.putExtra(WebBrowserActivity.URL, mTopics.get(position).getTargetUrl());
                startActivity(intent);
            }
        });
        mTopicList.setLayoutManager(layoutManager);
        mTopicList.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        Context context;
        List<Topic> topics;

        private OnItemClickListener onItemClickListener;

        public Adapter(Context context, List<Topic> topics) {
            this.context = context;
            this.topics = topics;
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_topic_list_item, parent, false);
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
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            Topic topic = topics.get(position);
            String img = topic.getTopicImg();
            Glide.with(context)
                    .load(img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.topicImg);

            holder.topicTxt.setText(topic.getTopicTxt());
        }

        @Override
        public int getItemCount() {
            return topics.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.topic_img)
            ImageView topicImg;
            @Bind(R.id.topic_txt)
            TextView topicTxt;

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
        }
    }
}
