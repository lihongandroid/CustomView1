package com.example.lihong.customview1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lihong on 2017/11/9.
 */

public class CustomView2 extends View{

    private Paint mPaint;//画笔
    private Context mContext;
    private int maxRadiu;

    private Bitmap bitmap;
    private int x,y;//位图绘制时右上角的坐标


    public CustomView2(Context context){
        this(context,null);
    }

    public CustomView2(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext=context;//通过构造函数将上下文传递进来

        initPaint();
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);//画笔的样式之一：描边
        mPaint.setStrokeWidth(10);//设置面包的粗细，即为10个px

        mPaint.setColor(Color.argb(255,255,128,103));//设置画笔为自定义颜色

        //生成色彩矩阵
        ColorMatrix colorMatrix=new ColorMatrix(new float[]{
                0,5f,0,0,0,
                0,0,5f,0,0,
                0,0,0,5f,0,
                0,0,0,0,5f,
        });

        //设置颜色过滤
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

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

        canvas.drawCircle(paddingLeft+width/2,paddingTop+height/2,maxRadiu,mPaint);
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
