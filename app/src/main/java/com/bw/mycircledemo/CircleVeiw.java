package com.bw.mycircledemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 贾秀坤 on 2017/6/7.
 */

public class CircleVeiw extends View{
    private float x=100;
    private float y=100;
    private Paint paint;
    private float x2;
    private float y2;
    private boolean flag=false;
    private  boolean b;

    public CircleVeiw(Context context){
        super(context);
    }

    public CircleVeiw(Context context,AttributeSet attrs){
        super(context,attrs);
        //初始化画笔
        paint=new Paint();
        //设置颜色
        paint.setColor(Color.BLUE);
        //抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
    }

    public CircleVeiw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,100,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x2=event.getX();
                y2=event.getY();
                b=initOf();
                break;

            case MotionEvent.ACTION_MOVE:
                //在拖动圆形时获得x值，y值
                x=event.getX();
                y=event.getY();

                //刷新
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return b;

    }

    private boolean initOf() {
        if (100>Math.abs(x-x2)&&100>Math.abs(y-y2)){
            flag=true;
        }else {
            flag=false;
        }
        return flag;
    }


}
