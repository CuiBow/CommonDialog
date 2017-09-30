package com.example.common_dialogs.dialog.childDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;


import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.common_dialogs.R;
import com.example.common_dialogs.adapter.ListAdapter;
import com.example.common_dialogs.dialog.BaseDialog;
import com.example.common_dialogs.dialog.HeadFactory;
import com.example.common_dialogs.dialog.HeaderType;


import java.util.HashMap;
import java.util.List;

/**
 * Created by cuibowen on 2017/9/18.
 */

public class ListDialog<T> extends BaseDialog implements View.OnClickListener{

    private ListView listView;
    private TextView cancal;
    private TextView click;
    private ListAdapter adapter;


    private DialogClickListener clickListener;

    public void setClickListener(DialogClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }
    private void init(){
        setCancelable(false);

        View headView= HeadFactory.create(HeaderType.BUY_ID,context);
        addFirstView(headView);

        View contentView=LayoutInflater.from(context).inflate(R.layout.list_item,null);

        addSecondView(contentView);

        View footView=LayoutInflater.from(context).inflate(R.layout.foot_view,null);
        addThirdView(footView);

        cancal= (TextView) findViewById(R.id.cancal);
        click= (TextView) findViewById(R.id.click);
        cancal.setOnClickListener(this);
        click.setOnClickListener(this);


        listView= (ListView) findViewById(R.id.list_view);
    }

    public void setListAdapter(List<HashMap<String,String>> list){

        adapter=new ListAdapter(context,R.layout.medicine_item,list);
        listView.setAdapter(adapter);



    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.click) {
            clickListener.doCommit();

        } else if (i == R.id.cancal) {
            clickListener.cancal();

        } else {
        }
    }


}
