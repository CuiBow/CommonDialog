package com.example.common_dialogs.adapter;

import android.content.Context;
import android.support.annotation.StringDef;

import com.example.common_dialogs.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cuibowen on 2017/9/18.
 */

public class ListAdapter extends CommonAdapter<HashMap<String,String>> {

    public ListAdapter(Context context, int layoutId, List<HashMap<String,String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HashMap<String,String> item, int position) {
        String content=item.get("content");

        viewHolder.setText(R.id.content,content);

    }
}
