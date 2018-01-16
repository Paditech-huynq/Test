package com.unza.wipro.main.views.customs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.unza.wipro.R;

/**
 * Created by bangindong on 1/10/2018.
 */

public class Degreeview extends View {

    private Paint mPaint1;
    private Paint mPaint2;
    private int background1;
    private int background2;
    private int maxvalue;
    private int value;
    private Bitmap mBitmap;
    private Canvas mcanvas;
    private Context context;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public int getBackground1() {
        return background1;
    }

    public int getBackground2() {
        return background2;
    }

    public void setBackground1(int background1) {
        this.background1 = background1;
    }

    public void setBackground2(int background2) {
        this.background2 = background2;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Degreeview(Context context) {
        super(context);
        this.context = context;
        reset();
    }

    public Degreeview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        reset();
    }

    public Degreeview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
            float width = (float) getWidth();
            float height = (float) getHeight();
            float radius;
            int text_size;
            float center_x, center_y;
            if (width >= height*2) {
                radius = height;
                center_x = width/2;
                center_y = height;
                text_size = (int) (2*height/25);
            } else {
                radius = width / 2;
                center_x = width / 2;
                center_y = height / 2;
                text_size = (int) (width/25);
            }
            final RectF oval = new RectF();
            int sweepAngle = (180 * value) / maxvalue;
            String text = String.valueOf(value * 100 / maxvalue) + " %";
            oval.set(center_x - radius,
                    center_y - radius,
                    center_x + radius,
                    center_y + radius);
            mcanvas.drawArc(oval, 180, 180, true, mPaint1);
            mcanvas.drawArc(oval, 180, sweepAngle, true, mPaint2);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setTextSize(text_size);
            paint.setStrokeWidth(1);
                paint.setTextAlign(Paint.Align.CENTER);
                mcanvas.drawText(text, center_x, center_y - text_size/2, paint);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.restore();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas(mBitmap);
    }

    public void setValue(int background1, int background2 , int maxvalue, int value){
        if(value<=maxvalue) {
        this.background1 = background1;
        this.background2 = background2;
        this.maxvalue = maxvalue;
        this.value = value;
        mPaint1.setColor(context.getResources().getColor(getBackground1()));
        mPaint2.setColor(context.getResources().getColor(getBackground2()));
        invalidate();
        }
    }

    public void reset() {
        setBackground1(R.color.white);
        setBackground2(R.color.colorPrimaryDark);
        setValue(0);
        setMaxvalue(100);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint1.setColor(context.getResources().getColor(getBackground1()));
        mPaint2.setColor(context.getResources().getColor(getBackground2()));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setStyle(Paint.Style.FILL);
    }
}
