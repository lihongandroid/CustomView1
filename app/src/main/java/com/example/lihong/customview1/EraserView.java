package com.example.lihong.customview1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lihong on 2017/11/13.
 */

public class EraserView extends View {
    private static final int MIN_MOV_DIS=5;//最小的移动距离：如果手指在屏幕上的移动距离小于此值则不会绘制
    private Bitmap fgBitmap,bgBitmap;//前景橡皮擦的Bitmap和底图的bitMap背景图
    private Canvas mCanvas;//绘制橡皮擦路径的画笔
    private Paint mPaint;//橡皮擦路径画笔
    private Path mPath;//橡皮擦绘制路径

    private int screenW,screenH;//屏幕高和宽
    private float preX,preY;//记录一个触摸事件的具体位置

    public  EraserView(Context context, AttributeSet attrs){
        super(context,attrs);

        cal(context);

        init(context);
    }

    /**
    计算参数：屏幕的宽高
     */
    private  void cal(Context context){
        int[] screnSize=MeasureUtil.getScreenSize((Activity)context);//获取屏幕尺寸数组
        screenW=screnSize[0];//获取屏幕宽
        screenH=screnSize[1];//获取屏幕高
    }

    /**
     * 初始化对象
     */
    private void init(Context context){
        mPath=new Path();//实例化路径对象
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);//实例化画笔并开启抗拒齿合抗抖动
        mPaint.setARGB(128,255,0,0);//设置画笔透明度为0是关键，让绘制的路径是透明，然后让路径与前景的底色混合“抠”出绘制路径
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//设置混合模式为DST_IN
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔风格为描边
        mPaint.setStrokeJoin(Paint.Join.ROUND);//设置画笔路径结合出为圆
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的笔触类型为圆
        mPaint.setStrokeWidth(50);//这只画笔描边宽带为50

        fgBitmap=Bitmap.createBitmap(screenW,screenH, Bitmap.Config.ARGB_8888);//生成前景图Bitmap
        mCanvas=new Canvas(fgBitmap);//将生成的前景图注入画布
        mCanvas.drawColor(0xff808080);//绘制画布背景为中性灰

        bgBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.a4);//获取背景地图

        bgBitmap=Bitmap.createScaledBitmap(bgBitmap,screenW,screenH,true);//缩放背景图片至屏幕大小
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(bgBitmap,0,0,null);//绘制背景
        canvas.drawBitmap(fgBitmap,0,0,null);//绘制前景
        /*
        canvas与mCanvas是两个不同的画布对象
        当画布上移动手指绘制路径时，会把路径通过mCanvas绘制到fgBitmap上
        每当手指移动一次均会将路径mPath作为目标图像绘制到mCanvas上，而我们已经将mCanvas上绘制了中性灰色
        两者会因为DST_IN模式计算只显示中性灰，但因路径为透明，计算生成的混合图像也会是透明的
        所以就会得到“橡皮擦“的效果
         */
        mCanvas.drawPath(mPath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //获取当前事件的位置坐标
        float x=event.getX();
        float y=event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN://手指触摸屏幕重置路径
                mPath.reset();
                mPath.moveTo(x,y);
                preX=x;
                preY=y;
                break;
            case MotionEvent.ACTION_MOVE://手指移动时连接路径
                float dx=Math.abs(x-preX);
                float dy=Math.abs(y-preY);
                if(dx>=MIN_MOV_DIS||dy>=MIN_MOV_DIS){
                    mPath.quadTo(preX,preY,(x+preX)/2,(y+preY)/2);
                    preX=x;
                    preY=y;
                }
                break;
        }
        invalidate();
        return true;
    }
}
