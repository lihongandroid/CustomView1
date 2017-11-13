package com.example.lihong.customview1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lihong on 2017/11/10.
 */

public class DisInView extends View {
    private Paint mPaint;//画笔
    private Bitmap bitmapDis,bitmapSrc;
    private PorterDuffXfermode porterDuffXfermode;

    private int x,y;//位图绘制时右上角的坐标
    private int screenW,screenH;//屏幕尺寸


    public DisInView(Context context){
        this(context,null);
    }

    public DisInView(Context context, AttributeSet attrs){
        super(context,attrs);

        porterDuffXfermode=new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        initPaint();

        initRes(context);
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void initRes(Context context){
        //获取包含屏幕尺寸的数组
        int[] screenSize=MeasureUtil.getScreenSize((Activity)context);
        screenW=screenSize[0];
        screenH=screenSize[1];

        //获取位图
        bitmapDis= BitmapFactory.decodeResource(context.getResources(),R.drawable.a3);
        bitmapSrc=BitmapFactory.decodeResource(context.getResources(),R.drawable.a3_mask);
        bitmapDis=Bitmap.createScaledBitmap(bitmapDis,screenW,screenH,true);
        bitmapSrc=Bitmap.createScaledBitmap(bitmapSrc,screenW,screenH,true);


    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        int width;
        int height;

        //处理padding的情况
        final int paddingLeft=getPaddingLeft();
        final int paddingRight=getPaddingRight();
        final int paddingTop=getPaddingTop();
        final int paddingBottom=getPaddingBottom();
        width=getWidth()-paddingLeft-paddingRight;
       height=getHeight()-paddingBottom-paddingTop;
        /**
         * 计算位图绘制时左上角坐标，使整个位图位于屏幕中心
         */
        x=paddingLeft+width/2-bitmapDis.getWidth()/2;//屏幕x坐标向左偏移位图一半的高度
        y=paddingTop+height/2-bitmapDis.getHeight()/2;
       // x=screenW/2-bitmapDis.getWidth()/2;
       // y=screenH/2-bitmapDis.getHeight()/2;

        int sc=canvas.saveLayer(0,0,screenW,screenH,null,Canvas.ALL_SAVE_FLAG);

        canvas.drawColor(0xff006600);

        //绘制位图
        canvas.drawBitmap(bitmapDis,x,y,mPaint);

        mPaint.setXfermode(porterDuffXfermode);

        canvas.drawBitmap(bitmapSrc,x,y,mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }

    //重写该方法确定View的大小,自己处理wrap_content的情况
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int widthMode= View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize= View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode= View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize= View.MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if(widthMode== View.MeasureSpec.EXACTLY){
            width=widthSize;
        }else{
            width=widthSize*1/2;
        }
        if(heightMode== View.MeasureSpec.EXACTLY){
            height=heightMeasureSpec;
        }else{
            height=heightSize*1/2;
        }

        //设置子view的宽和高的测量值，在此处该测量值是有系统出测量后出入的
        setMeasuredDimension(width,height);

    }

}
