package com.example.lihong.customview1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lihong on 2017/11/9.
 */

public class CustomView4 extends View {
    private Paint mPaint;//画笔
    private Context mContext;

    private Bitmap bitmap;
    private int x,y;//位图绘制时右上角的坐标
    private boolean isClick;//用来标识控件是否被点击过


    public CustomView4(Context context){
        this(context,null);
    }

    public CustomView4(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext=context;//通过构造函数将上下文传递进来

        initPaint();
        initRes(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClick){
                    mPaint.setColorFilter(null);//如果被点击过，则设置过滤为空
                    isClick=false;
                }else{
                    mPaint.setColorFilter(new LightingColorFilter(0xffffffff,0x00ffff00));//如果为被点击过则设置为黄色
                    isClick=true;
                }

                invalidate();//重写绘制
            }
        });
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        //生成彩色矩阵
      /*  ColorMatrix colorMatrix=new ColorMatrix(new float[]{
                0,1,0,0,0,
                0,0,1,0,0,
                1,0,0,0,0,
                0,0,0,1,0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));//添加颜色过滤
       */
        mPaint.setColorFilter(new LightingColorFilter(0xffff00ff,0x000000000));

    }

    private void initRes(Context context){
        //获取位图
        bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.a2);
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
        x=paddingLeft+width/2-bitmap.getWidth()/2;//屏幕x坐标向左偏移位图一半的高度
        y=paddingTop+height/2-bitmap.getHeight()/2;

        //绘制位图
        canvas.drawBitmap(bitmap,x,y,mPaint);
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
