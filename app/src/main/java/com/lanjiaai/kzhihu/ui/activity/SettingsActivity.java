package com.lanjiaai.kzhihu.ui.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.common.LLog;
import com.lanjiaai.kzhihu.utils.DataCleanManager;

/**
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private Preference clearCache;
    public static final String PREF_KEY_CLEAR_CACHE = "pref_clear_cache";

    private ListPreference openType;
    public static final String PREF_KEY_OPEN_TYPE = "pref_open_type";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("com.ouwenjie.kzhihu_preferences", MODE_PRIVATE);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);
        root.addView(toolbar, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置中心");
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SettingsActivity.this.onBackPressed();
                    }
                }
        );
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);


        //Load the preference from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        clearCache = findPreference(PREF_KEY_CLEAR_CACHE);
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                LLog.e("onPreferenceClick " + preference.getTitle());
                cleanCache();
                return false;
            }
        });

        openType = (ListPreference) findPreference(PREF_KEY_OPEN_TYPE);
        /* 是显示默认值*/
        openType.setSummary(openType.getEntry());
        openType.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                LLog.e("OPEN TYPE = ", newValue + "");
                if (preference instanceof ListPreference) {
                    //把preference这个Preference强制转化为ListPreference类型
                    ListPreference listPreference = (ListPreference) preference;
                    //获取ListPreference中的实体内容
                    CharSequence[] entries = listPreference.getEntries();
                    //获取ListPreference中的实体内容的下标值
                    int index = listPreference.findIndexOfValue((String) newValue);
                    //把listPreference中的摘要显示为当前ListPreference的实体内容中选择的那个项目
                    listPreference.setSummary(entries[index]);
                }
                return true;
            }
        });

    }


    public void cleanCache() {
        progressDialog.setTitle("清理缓存...");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("清理缓存")
                .setCancelable(true)
                .setPositiveButton("清理", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        // clear cache
                        progressDialog.show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataCleanManager.clearAllCache(getApplicationContext());
                                try {
                                    Thread.sleep(1000); // 再转1秒，免得消失得太快
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    cleanCacheFinish();
                                }
                            }
                        }).start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }

    private void cleanCacheFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

            }
        });
    }


}
