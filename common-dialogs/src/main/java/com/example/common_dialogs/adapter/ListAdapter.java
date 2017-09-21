package com.example.common_dialogs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.StringDef;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.common_dialogs.R;
import com.example.common_dialogs.view.ProgressUtils;
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
        TextView contents=viewHolder.getView(R.id.content);
        String content=item.get("content");
        String count=item.get("canBuyCount");

        String sumContent=content+" 最多购买 "+count+" 盒";
        SpannableStringBuilder ssb = new SpannableStringBuilder(sumContent);
        StyleSpan span1 = new StyleSpan(Typeface.BOLD);
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(ProgressUtils.sp2px(mContext,18));
        ssb.setSpan(span1,content.length()+6, content.length()+6+count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(span2,content.length()+6, content.length()+6+count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(0xff1aa98c), content.length()+6, content.length()+6+count.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contents.setText(ssb);


    }
}
