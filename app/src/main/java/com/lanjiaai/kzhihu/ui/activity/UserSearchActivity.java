package com.lanjiaai.kzhihu.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.common.Constant;
import com.lanjiaai.kzhihu.common.LLog;
import com.lanjiaai.kzhihu.model.PreferencesUtil;
import com.lanjiaai.kzhihu.model.entity.SearchUser;
import com.lanjiaai.kzhihu.model.mImp.ApiImp;
import com.lanjiaai.kzhihu.ui.activity.base.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

public class UserSearchActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar_container)
    FrameLayout toolbarContainer;
    @Bind(R.id.search_user_list_view)
    RecyclerView searchUserListView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    private List<SearchUser> searchUsers;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        setTitle("");
        setSupportActionBar(toolbar);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LLog.e("onQueryTextSubmit = " + query);
                searchUsers.clear();
                adapter.notifyDataSetChanged();
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LLog.e("onQueryTextChange = " + newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                LLog.e("onSearchViewShown");
            }

            @Override
            public void onSearchViewClosed() {
                LLog.e("onSearchViewClosed");
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchUsers = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(), searchUsers);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SearchUser user = searchUsers.get(position);
                String userString = new Gson().toJson(user);
                PreferencesUtil.putString(getApplicationContext(), Constant.KEY_BIND_USER, userString);

                finish();
            }
        });
        searchUserListView.setLayoutManager(linearLayoutManager);
        searchUserListView.setAdapter(adapter);
    }

    public void search(String name) {
        new ApiImp().searchUser(name)
                .subscribe(new Subscriber<List<SearchUser>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<SearchUser> users) {
                        searchUsers.addAll(users);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.showSearch();
            }
        }, 400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private Context context;
        private List<SearchUser> objectList;
        private OnItemClickListener onItemClickListener;

        public Adapter(Context context, List<SearchUser> objectList) {
            this.context = context;
            this.objectList = objectList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_search_item, parent, false);
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
            SearchUser user = objectList.get(position);
            Glide.with(context)
                    .load(user.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.searchUserImg);

            holder.searchUserNameTxt.setText(user.getName());
            holder.searchUserSignatureTxt.setText(user.getSignature());

        }

        @Override
        public int getItemCount() {
            return objectList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.search_user_img)
            CircleImageView searchUserImg;
            @Bind(R.id.search_user_name_txt)
            TextView searchUserNameTxt;
            @Bind(R.id.search_user_signature_txt)
            TextView searchUserSignatureTxt;
            @Bind(R.id.search_item)
            LinearLayout searchItem;

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
