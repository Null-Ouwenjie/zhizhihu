/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lanjiaai.kzhihu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.lanjiaai.kzhihu.R;


/**
 * Pick from Google io 2014
 * Created by drakeet on 1/3/15.
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {

    private CanChildScrollUpCallback mCanChildScrollUpCallback;
    private Drawable mForegroundDrawable;

    public MultiSwipeRefreshLayout(Context context) {
        this(context, null);
    }


    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.MultiSwipeRefreshLayout, 0, 0);

        mForegroundDrawable = a.getDrawable(R.styleable.MultiSwipeRefreshLayout_foreground);
        if (mForegroundDrawable != null) {
            mForegroundDrawable.setCallback(this);
            setWillNotDraw(false);
        }
        a.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mForegroundDrawable != null) {
            mForegroundDrawable.setBounds(0, 0, w, h);
        }
    }


    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }


    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }


    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}
