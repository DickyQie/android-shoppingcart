package com.zhangqie.shoppingcart.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * 使用BitmapShader类实现圆形、圆角，椭圆等自定义图片View。
 * @author zhangqie
 *
 */
public class ZQRoundOvalImageView extends ImageView {

	
	 private Paint mPaint;

	    private int mWidth;

	    private int mHeight;

	    private int mRadius;//圆半径

	    private RectF mRect;

	    private int mRoundRadius;// 圆角大小


	    private BitmapShader mBitmapShader;//图形渲染

	    private Matrix mMatrix;

	    private int mType;// 记录是圆形还是圆角矩形

	    public static final int TYPE_CIRCLE = 0;// 圆形
	    public static final int TYPE_ROUND = 1;// 圆角矩形
	    public static final int TYPE_OVAL = 2;//椭圆形
	    public static final int DEFAUT_ROUND_RADIUS = 10;//默认圆角大小

	    public ZQRoundOvalImageView(Context context) {
	        this(context, null);
	        // TODO Auto-generated constructor stub
	    }

	    public ZQRoundOvalImageView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	        // TODO Auto-generated constructor stub
	    }

	    public ZQRoundOvalImageView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        initView();
	    }

	    private void initView() {
	        mPaint = new Paint();
	        mPaint.setAntiAlias(true);
	        mMatrix = new Matrix();
	        mRoundRadius = DEFAUT_ROUND_RADIUS;
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        // TODO Auto-generated method stub
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        // 如果是绘制圆形，则强制宽高大小一致
	        if (mType == TYPE_CIRCLE) {
	            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
	            mRadius = mWidth / 2;
	            setMeasuredDimension(mWidth, mWidth);
	        }

	    }

	    @Override
	    protected void onDraw(Canvas canvas) {

	        if (null == getDrawable()) {
	            return;
	        }
	        setBitmapShader();
	        if (mType == TYPE_CIRCLE) {
	            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
	        } else if (mType == TYPE_ROUND) {
	            mPaint.setColor(Color.RED);
	            canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mPaint);
	        }else if(mType == TYPE_OVAL){
	            canvas.drawOval(mRect, mPaint);
	        }
	    }

	    @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        // TODO Auto-generated method stub
	        super.onSizeChanged(w, h, oldw, oldh);
	        mRect = new RectF(0, 0, getWidth(), getHeight());
	    }

	    /**
	     * 设置BitmapShader
	     */
	    private void setBitmapShader() {
	        Drawable drawable = getDrawable();
	        if (null == drawable) {
	            return;
	        }
	        Bitmap bitmap = drawableToBitmap(drawable);
	        // 将bitmap作为着色器来创建一个BitmapShader
	        mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
	        float scale = 1.0f;
	        if (mType == TYPE_CIRCLE) {
	            // 拿到bitmap宽或高的小值
	            int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
	            scale = mWidth * 1.0f / bSize;

	        } else if (mType == TYPE_ROUND || mType == TYPE_OVAL) {
	            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
	            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
	        }
	        // shader的变换矩阵，我们这里主要用于放大或者缩小
	        mMatrix.setScale(scale, scale);
	        // 设置变换矩阵
	        mBitmapShader.setLocalMatrix(mMatrix);
	        mPaint.setShader(mBitmapShader);

	    }

	    /**
	     * drawable转bitmap
	     * 
	     * @param drawable
	     * @return
	     */
	    private Bitmap drawableToBitmap(Drawable drawable) {
	        if (drawable instanceof BitmapDrawable) {
	            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
	            return bitmapDrawable.getBitmap();
	        }
	        int w = drawable.getIntrinsicWidth();
	        int h = drawable.getIntrinsicHeight();
	        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	        Canvas canvas = new Canvas(bitmap);
	        drawable.setBounds(0, 0, w, h);
	        drawable.draw(canvas);
	        return bitmap;
	    }
	    /**
	     * 单位dp转单位px
	     */
	    public int dpTodx(int dp){

	        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
	                dp, getResources().getDisplayMetrics());

	    }
	    
	    public int getType() {
	        return mType;
	    }
	    /**
	     * 设置图片类型：圆形、圆角矩形、椭圆形
	     * @param mType
	     */
	    public void setType(int mType) {
	        if(this.mType != mType){
	            this.mType = mType;
	            invalidate();
	        }
	        
	    }
	    public int getRoundRadius() {
	        return mRoundRadius;
	    }
	    /**
	     * 设置圆角大小
	     * @param mRoundRadius
	     */
	    public void setRoundRadius(int mRoundRadius) {
	        if(this.mRoundRadius != mRoundRadius){
	            this.mRoundRadius = mRoundRadius;
	            invalidate();
	        }
	        
	    }
}
