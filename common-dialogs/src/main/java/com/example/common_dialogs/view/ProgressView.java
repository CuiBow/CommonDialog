package com.example.common_dialogs.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.common_dialogs.R;


/**
 * Created by cuibowen on 2017/9/15.
 */

public class ProgressView extends View {
    //上下文
    private Context context;
    //文字大小
    private int textSize;
    //进度条颜色
    private static final int PROGRESS_COLOR = 0xff26D5A5;
    //背景颜色
    private static final int BACKGROUND_COLOR = 0xffEDEDED;
    //内边距
    private int padding;
    //进度文字
    private String progressText="0%";
    //view宽高
    private int mWidth;
    private int mHeight;
    //进度
    private float currertX=0f;
    //路径
    private float currentProgress=0f;
    //记录百分百
    private int valueInt=0;
    //火箭
    private Bitmap rocket;
    //画笔
    private Paint textPaint;
    private Paint backPaint;
    private Paint progressPaint;
    private Paint rocketPaint;
    //动画
    private ValueAnimator animator;
    //进度接口
    private OnProgressListener onProgressListener;

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    public ProgressView(Context context) {
        this(context, null);

    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        //关闭物理加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //基本设置
        padding = ProgressUtils.dp2px(context,0);
        textSize = ProgressUtils.sp2px(context,22);
        //画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);


        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setStrokeWidth(1);
        backPaint.setColor(BACKGROUND_COLOR);
        backPaint.setAntiAlias(true);
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setStrokeCap(Paint.Cap.ROUND);


        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(PROGRESS_COLOR);
        progressPaint.setStrokeWidth(1);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setShadowLayer(4, 0, 5, Color.rgb(199, 199, 199));

        rocketPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rocketPaint.setAntiAlias(true);
        rocketPaint.setStrokeWidth(1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        float[] point = new float[]{
                0f,0.05f,0.05f,1f
        };
        int[] color = new int[]{
                Color.parseColor("#c0c0c0"),
                Color.rgb(193, 193, 193),
                Color.rgb(237, 237, 237),
                Color.rgb(237, 237, 237)
        };

        Shader shader = new LinearGradient(
                0, 0,  0, mHeight,
                color,
                point,
                Shader.TileMode.CLAMP);
        backPaint.setShader(shader);

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawProgress(canvas);
        drawText(canvas);
        drawRocket(canvas);

    }
    //绘制火箭
    private void drawRocket(Canvas canvas) {
        if (valueInt == 0){

        }else if (valueInt >= 100) {

        } else if (valueInt > 0 && valueInt < 100) {
//            if ((currentProgress + padding + ProgressUtils.dp2px(context,10)+rocket.getWidth())<=mWidth - padding*2){
//
//               // canvas.drawBitmap(rocket, currentProgress + padding + ProgressUtils.dp2px(context,10), mHeight / 2-rocket.getHeight()/2, rocketPaint);
//
//            }else{
//                canvas.drawBitmap(rocket,null,new RectF(currentProgress + padding + ProgressUtils.dp2px(context,10),mHeight / 2-rocket.getHeight()/2,mWidth - padding*2,mHeight / 2-rocket.getHeight()/2+rocket.getHeight()),rocketPaint);
//            }

             canvas.drawBitmap(rocket,  currentProgress + padding + ProgressUtils.dp2px(context,10), mHeight / 2-rocket.getHeight()/2, rocketPaint);
        }
    }
    //绘制百分比
    private void drawText(Canvas canvas) {
        float textWidth = textPaint.measureText(progressText);
        float progress = currentProgress + padding;
        float progressRead=padding+ textWidth / 2+ProgressUtils.dp2px(context,5);
        Rect bounds =new Rect();
        textPaint.getTextBounds("80",0,2,bounds);
        int i = bounds.height() / 2;

        if (valueInt == 0) {
            canvas.drawText(progressText, progressRead, mHeight/2+i, textPaint);
        } else if (valueInt > 0 && valueInt <= 100&&currentProgress>=progressRead) {
            progress = progress - textWidth / 2 - ProgressUtils.dp2px(context,5);
            canvas.drawText(progressText, progress, mHeight/2+i, textPaint);
        }else if (valueInt > 0 && valueInt <= 100&&currentProgress<progressRead){
            canvas.drawText(progressText, progressRead, mHeight/2+i, textPaint);
        }


    }

    //绘制进度条
    private void drawProgress(Canvas canvas) {
        if (valueInt==0){

        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(padding + 2, 0 , currentProgress + padding - 2, mHeight - 4, mHeight, mHeight, progressPaint);
            }
        }

    }

    //绘制背景
    private void drawBackground(Canvas canvas) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(padding, 0, mWidth - padding, mHeight, mHeight, mHeight, backPaint);
        }
    }


    //设置进度
    public void setProgressText(int progress) {
        //火箭
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rocket);
        rocket = ProgressUtils.zoomImg(bitmap, bitmap.getWidth() - ProgressUtils.dp2px(context,20), mHeight -ProgressUtils.dp2px(context,20));
        bitmap.recycle();

        //进度条判断
        if (progress <= 0) {
            progress = 0;
        } else if (progress >= 100) {
            progress = 100;
        }
        currertX = progress;

        //创建动画
        animator = ValueAnimator.ofFloat(0, currertX);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                currentProgress = (int) (value * (mWidth - padding * 2) / 100);
                valueInt = ProgressUtils.format2Int(value);
                progressText = valueInt + "%";
                if (onProgressListener!=null){
                    if(valueInt==100){
                        //释放资源
                        cancel();
                    }
                    onProgressListener.onProgress(valueInt);
                }
                invalidate();
            }
        });

        animator.start();
    }


    //向用户返回的回调接口
    public interface OnProgressListener {
        void onProgress(int progress);
    }

    //释放资源
    private void cancel(){
        rocket.recycle();
    }

}
