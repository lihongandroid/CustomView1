# CustomView1
自定义控件其实很简单之教程学习

Paint中的大量setter方法为画笔设置属性

1 set(Paint src)：将另外支画笔的属性设置赋值给当前的画笔
  
2 setARGB(int a,int r,int g,int b)设置颜色,颜色为自定义颜色
  
3 setAlpha（int a）设置颜色
  
4 setAntiAlias（boolean aa）当绘制棱角分明的的图形时（如矩形，位图等），就不打开；当绘制圆等图形就打开设置为true
  
5 setColor（int color）
  
6 setColorFilter（ColorFilter filter）设置颜色过滤

ColorFilter的子类：ColorMatrixColorFilter，LightingColorFilter，PorterDuffColorFilter

	1 ColorMatrixColorFilter：色彩矩阵颜色过滤器。在Android中图片是以RGBA像素点的形式加载到内存中的，
    修改这些像素信息需要一个叫做ColorMatrix类的支持，该类定义了一个4×5的float[]类型的矩阵 。
    修改矩阵的值，就可实现颜色的过滤效果
  
	2 LightingColorFilter，光照颜色过滤，该类只有一个构造方法如下：
		LightingColorFilte（int colorMultiply，int colorAdd），两个参数值分别指色彩倍增与色彩添加，
    都是16进制的色彩值0xAARRGGBB
		该类可实现点击一个图片改变它的颜色而不是多为它准备另一张点击效果的图片
    
	3 PorterDuffColorFilter也只有一个构造方法，如下：
		PorterDuffColorFilter（int color，PorterDuff.Mode mode）,参数为：一个是16进制表示的颜色值，
    另一个是PorterDuff内部类Mode中的一个常量值，该值表示混合模式

7 setXfermode（Xfermode xfermoe）过渡模式或图像混合模式
  
		Xfermode的子类：AvoidXfermode，PixelXorXfermode，PorterDuffXfermode
    
		1 AvoidXfermode因其不支持硬件加速在API16中被摒弃
    
		2 PixelXorXfermode同上
    
		3 PorterDuffXfermode，即是图形混合模式，该类有且只有一个含参的构造方法
    
			PorterDuffXfermode（PorterDuff.Mode mode），当要要画一个View的图形是，
      可以通过基本的集合图形混合生成，即使用PorterDuffXfermode的混合模式
