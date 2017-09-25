package com.example.common_dialogs.dialog.childDialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.common_dialogs.R;
import com.example.common_dialogs.dialog.BaseDialog;
import com.example.common_dialogs.dialog.HeadFactory;
import com.example.common_dialogs.dialog.HeaderType;
import com.example.common_dialogs.view.ProgressView;

/**
 * Created by cuibowen on 2017/9/18.
 */

public class ProgressDialog extends BaseDialog implements View.OnClickListener{
    //是否强制更新
    private boolean isforced=false;
    private ProgressView progressView;
    private TextView cancal;
    private TextView click;
    private LinearLayout containerInfo;
    private OnProgressListener progressListener;
    private DialogClickListener clickListener;

    public void setClickListener(DialogClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public ProgressDialog(@NonNull Context context, boolean isForced) {
        super(context);
        this.isforced=isForced;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }
    private void init(){

        View headView= HeadFactory.create(HeaderType.UPDATE_ID,context);
        addFirstView(headView);

        View contentView=LayoutInflater.from(context).inflate(R.layout.progress_item,null);

        addSecondView(contentView);


        progressView= (ProgressView) findViewById(R.id.progress_view);
        containerInfo= (LinearLayout) findViewById(R.id.container_info);


        if (isforced){
            setCancelable(false);
            containerInfo.setBackgroundResource(R.drawable.shape_dialog_bg_border);

        }else{

            containerInfo.setBackgroundResource(R.drawable.shape_dialog_bg_noborder);

            setCancelable(true);
            View footView=LayoutInflater.from(context).inflate(R.layout.foot_view,null);
            addThirdView(footView);



            cancal= (TextView) findViewById(R.id.cancal);
            click= (TextView) findViewById(R.id.click);
            cancal.setOnClickListener(this);
            click.setOnClickListener(this);

            containerInfo.setBackgroundColor(Color.WHITE);


        }
    }

    public void setProgress(int progress){
        if (progressView!=null){
            progressView.setProgressText(progress);
            progressView.setOnProgressListener(new ProgressView.OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    if (progressListener!=null){
                        progressListener.onProgress(progress);
                    }
                }
            });

        }

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

    //向用户返回进度的回调接口
    public interface OnProgressListener {
        void onProgress(int progress);
    }


}
