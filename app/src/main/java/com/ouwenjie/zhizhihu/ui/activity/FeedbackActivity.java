package com.ouwenjie.zhizhihu.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ouwenjie.zhizhihu.R;
import com.ouwenjie.zhizhihu.ui.activity.base.SwipeBackActivity;
import com.ouwenjie.zhizhihu.utils.DimenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends SwipeBackActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.feedback_type_txt)
    TextView feedbackTypeTxt;
    @Bind(R.id.feedback_content_edit)
    EditText feedbackContentEdit;
    @Bind(R.id.feedback_content_layout)
    FrameLayout feedbackContentLayout;
    @Bind(R.id.contact_txt)
    EditText contactTxt;
    @Bind(R.id.commit_btn)
    Button commitBtn;
    @Bind(R.id.feedback_type_item)
    LinearLayout feedbackTypeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initTitleBar();

        feedbackTypeTxt.setText("产品意见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initTitleBar() {
        setSupportActionBar(toolbar);
        setTitle("意见反馈");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FeedbackActivity.this.onBackPressed();
                    }
                }
        );
    }


    @OnClick(R.id.commit_btn)
    public void commitAdvice() {
        String key = feedbackTypeTxt.getText().toString();
        String content = feedbackContentEdit.getText().toString();
        String contact = contactTxt.getText().toString();
        // // TODO: 2016/4/10 0010 commit
    }

    @OnClick(R.id.feedback_type_item)
    public void seleteFeedbackType() {
        // todo 需要重新确认 反馈的类型
        final String[] typeList = new String[]{"产品意见", "用户体验", "程序崩溃", "其它"};
        final String[] type = {"产品意见"};

        FrameLayout frameLayout = createRadioGroupLayout(typeList, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        type[0] = rb.getText().toString();
                    }
                }
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择反馈类型")
                .setView(frameLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        feedbackTypeTxt.setText(type[0]);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();

    }

    @NonNull
    private FrameLayout createRadioGroupLayout(final String[] typeList, RadioGroup.OnCheckedChangeListener listener) {
        final List<RadioButton> rbList = new ArrayList<>();
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setPadding(24, 24, 24, 24);
        for (int i = 0; i < typeList.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(typeList[i]);
            rbList.add(rb);
            radioGroup.addView(rb);
        }
        String curType = feedbackTypeTxt.getText().toString();
        for (RadioButton btn : rbList) {
            if (btn.getText().toString().equals(curType)) {
                radioGroup.check(btn.getId());
            }
        }
        radioGroup.setOnCheckedChangeListener(listener);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.leftMargin = DimenUtil.dip2px(this, 24);
        lp.rightMargin = lp.leftMargin;
        frameLayout.addView(radioGroup, lp);
        return frameLayout;
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(feedbackContentEdit.getText().toString())) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("您有正在编辑的内容，退出将不保存") // todo 可自动保存上一次编辑的内容
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            feedbackContentEdit.setText("");
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        } else {
            super.onBackPressed();
        }
    }

}
