package com.example.cuibowen.animatior;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common_dialogs.dialog.childDialog.DialogClickListener;
import com.example.common_dialogs.dialog.childDialog.ListDialog;
import com.example.common_dialogs.dialog.childDialog.ProgressDialog;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private ImageView imageView2;
    private static final int FIRST_ANIMATOR=1;


    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case FIRST_ANIMATOR:
                    animator2(imageView2);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(this);

        animator2(imageView);



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView:
                animator1(imageView);

                final ProgressDialog dialogs=new ProgressDialog(MainActivity.this,true);
                dialogs.show();
                dialogs.setProgress(100);
                dialogs.setProgressListener(new ProgressDialog.OnProgressListener() {
                    @Override
                    public void onProgress(int progress) {

                        if (progress==100){
                            dialogs.dismiss();
                        }
                    }
                });

                break;
            case R.id.imageView2:
                animator3(imageView2);



                List< HashMap<String,String>> list=new ArrayList();

                HashMap<String,String> map=new HashMap<>();
                map.put("content","同仁堂撒哒哒哒哒哒");
                map.put("canBuyCount","5");
                HashMap<String,String> map2=new HashMap<>();
                map2.put("content","同仁堂撒哒哒哒哒哒");
                map2.put("canBuyCount","10");
                list.add(map);
                list.add(map2);

                final ListDialog dialog=new ListDialog(MainActivity.this);
                dialog.show();
                dialog.setListAdapter(list);
                dialog.setClickListener(new DialogClickListener() {
                    @Override
                    public void doCommit() {
                        dialog.dismiss();
                    }

                    @Override
                    public void cancal() {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }


    private void animator1(View view){
        ObjectAnimator animator1=ObjectAnimator.ofFloat(view,"scaleX",0.8f,1.0f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(view,"scaleY",0.8f,1.0f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(700);
        animatorSet.play(animator1).with(animator2);
        animatorSet.start();
    }

    private void animator2(View view){

        view.setVisibility(View.VISIBLE);
        float y=view.getTranslationY();
        ObjectAnimator animator1=ObjectAnimator.ofFloat(view,"TranslationY",y,y-100f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(view,"alpha",0f,1.0f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(animator1).with(animator2);
        animatorSet.start();

        if (view.getId()==R.id.imageView){
            handler.sendEmptyMessageDelayed(FIRST_ANIMATOR,100);
        }else{
            handler.removeCallbacksAndMessages(this);
        }
    }


    private void animator3(View view){

        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator1=ObjectAnimator.ofFloat(view,"rotation",360f,180f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(view,"alpha",0.6f,1.0f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(animator1).with(animator2);
        animatorSet.start();

    }


}
