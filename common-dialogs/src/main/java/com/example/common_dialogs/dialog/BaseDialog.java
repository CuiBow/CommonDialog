package com.example.common_dialogs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.common_dialogs.R;

/**
 * Created by cuibowen on 2017/9/18.
 */

public abstract class BaseDialog extends Dialog {
    protected Context context;
    private LinearLayout firstView;
    private LinearLayout secondView;
    private LinearLayout thirdView;



    public BaseDialog(@NonNull Context context) {
        super(context, R.style.CommonDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.base_item, null);
        setContentView(view);

        firstView=findViewById(R.id.first_item);
        secondView=findViewById(R.id.second_item);
        thirdView=findViewById(R.id.third_item);

    }

    protected void addFirstView(View view){
        firstView.addView(view);
    }
    protected void addSecondView(View view){
        secondView.addView(view);
    }
    protected void addThirdView(View view){
        thirdView.addView(view);
    }


}
