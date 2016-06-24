package com.lanjiaai.kzhihu.common;

import android.content.Context;

import com.lanjiaai.kzhihu.R;
import com.lanjiaai.kzhihu.utils.PreferencesUtil;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class ThemeManager {

    private static final String KEY_CUR_THEME = "cur_theme";

    public static final int DEFAULT_THEME = R.style.AppTheme;

    public static final int PINK_THEME = R.style.PinkAppTheme;

    public static int getCurTheme(Context context) {
        return PreferencesUtil.getInt(context, KEY_CUR_THEME, DEFAULT_THEME);
    }

    public static void saveCurTheme(Context context, int theme) {
        PreferencesUtil.putInt(context, KEY_CUR_THEME, theme);
    }

    public static void changeTheme(Context context) {
        if (getCurTheme(context) == DEFAULT_THEME) {
            saveCurTheme(context, PINK_THEME);
        } else {
            saveCurTheme(context, DEFAULT_THEME);
        }
    }

}
