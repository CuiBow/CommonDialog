package com.example.common_dialogs.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.common_dialogs.R;

/**
 * Created by cuibowen on 2017/9/18.
 */

public class HeadFactory {
    public static View create(@HeaderType.LayoutType String type, Context context){
        View headView=null;
        switch (type){
            case HeaderType.UPDATE_ID:
                headView= LayoutInflater.from(context).inflate(R.layout.update_item,null);
            break;
            case HeaderType.BUY_ID:
                headView= LayoutInflater.from(context).inflate(R.layout.buy_item,null);
                break;
            default:
                headView= LayoutInflater.from(context).inflate(R.layout.buy_item,null);
                break;
        }
        return headView;
    }
}
