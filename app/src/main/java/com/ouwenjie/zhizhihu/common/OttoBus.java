package com.ouwenjie.zhizhihu.common;

import com.squareup.otto.Bus;

/**
 * Created by Jack on 2015/11/17.
 */
public class OttoBus {

    private static final Bus sBUS = new Bus();

    public static Bus getInstance() {
        return sBUS;
    }

    private OttoBus() {
        // No instances.
    }

}
