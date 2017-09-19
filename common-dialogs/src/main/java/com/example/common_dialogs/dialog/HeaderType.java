package com.example.common_dialogs.dialog;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.example.common_dialogs.R;

/**
 * Created by cuibowen on 2017/9/18.
 */

public class HeaderType {
    public static final String UPDATE_ID= "update";
    public static final String BUY_ID= "buy";


    @StringDef({UPDATE_ID,BUY_ID})
    public @interface LayoutType{

    }
}
