package com.bw.mycircledemo;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.midi.MidiDevice;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Space;

/**
 * Created by 贾秀坤 on 2017/6/7.
 */

public class DuoDianView extends Activity implements View.OnTouchListener{
    //放大缩小
    Matrix matrix=new Matrix();
    Matrix savedMatrix=new Matrix();

    PointF start=new PointF();
    PointF mid=new PointF();
    float oldDist;

    private ImageView myImageView;

    //模式
    static final int NONE=0;
    static final int DRAG=1;
    static final int ZOOM=2;
    int mode=NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duodian);

        myImageView=(ImageView) findViewById(R.id.image);
        myImageView.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        ImageView myImageView=(ImageView) view;
        switch (motionEvent.getAction()&MotionEvent.ACTION_MASK){
            //设置拖拉模式
            case MotionEvent.ACTION_DOWN:
                matrix.set(myImageView.getImageMatrix());
                savedMatrix.set(matrix);
                start.set(motionEvent.getX(),motionEvent.getY());
                mode=DRAG;
                break;
            //手指放开事件
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode=NONE;
                break;
            //设置多点触摸模式
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist= Spacing(motionEvent);
                if (oldDist>10f){
                    savedMatrix.set(matrix);
                    midPoint(mid,motionEvent);
                    mode=ZOOM;
                }
                break;
            //若为DRAG模式，则点击移动图片
            case MotionEvent.ACTION_MOVE:
                if (mode==DRAG){
                    matrix.set(savedMatrix);
                    matrix.postTranslate(motionEvent.getX()-start.x,motionEvent.getY()-start.y);
                }
          //若为zoom模式，则点击触摸缩放
            else if(mode==ZOOM){
                float newDist=Spacing(motionEvent);
                    if (newDist>10f){
                        matrix.set(savedMatrix);
                        float scale=newDist/oldDist;
                        //设置缩放比例和图片的中点位置
                        matrix.postScale(scale,scale,mid.x,mid.y);
                    }
            }
                break;
        }
        myImageView.setImageMatrix(matrix);

        return true;
    }

    //计算移动距离
    private float Spacing(MotionEvent event){

        float x=event.getX(0)-event.getX(1);
        float y=event.getY(0)-event.getY(1);
        return (float) Math.sqrt(x*x+y*y);

    }
    //计算中点位置
    private void midPoint(PointF point,MotionEvent event){
        float x=event.getX(0)+event.getX(1);
        float y=event.getY(0)+event.getY(1);
        point.set(x/2,y/2);
    }
}
