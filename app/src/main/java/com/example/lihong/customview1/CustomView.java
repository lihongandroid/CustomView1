package com.example.lihong.customview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lihong on 2017/11/8.
 */

public class CustomView extends View implements Runnable{
    private Paint mPaint;//画笔
    private int radiu;//半径
    private Context mContext;
    private int maxRadiu;//圆的最大半径

    public CustomView(Context context){
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext=context;//通过构造函数将上下文传递进来

        initPaint();
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);//画笔的样式之一：描边
        mPaint.setStrokeWidth(10);//设置面包的粗细，即为10个px
        mPaint.setColor(Color.LTGRAY);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width;
        int height;
        /*  //未处理padding的情况
        width=getWidth();
        height=getHeight();
        int radius=Math.min(width,height)/2;
        canvas.drawCircle(width/2,height/2,radiu,mPaint);*/

        //处理padding的情况
        final int paddingLeft=getPaddingLeft();
        final int paddingRight=getPaddingRight();
        final int paddingTop=getPaddingTop();
        final int paddingBottom=getPaddingBottom();

        width=getWidth()-paddingLeft-paddingRight;
        height=getHeight()-paddingBottom-paddingTop;
        maxRadiu=paddingLeft+width/2;//将圆的最大半径赋值为屏幕的宽的一半
        //int radius=Math.min(width,height)/2;
        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,radiu,mPaint);
    }

    //重写该方法确定View的大小,自己处理wrap_content的情况
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=widthSize*1/2;
        }
        if(heightMode==MeasureSpec.EXACTLY){
            height=heightMeasureSpec;
        }else{
            height=heightSize*1/2;
        }

        //设置子view的宽和高的测量值，在此处该测量值是有系统出测量后出入的
        setMeasuredDimension(width,height);

    }

    @Override
    public void run(){
            while(true){
                try{
                    if(radiu<maxRadiu){
                        radiu+=10;

                     // invalidate();//若用该方法刷新View，则会出错，原因是不能在子线程中更新UI
                        postInvalidate();//刷新View
                    }else{
                        radiu=0;
                    }

                    Thread.sleep(40);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
    }
}
