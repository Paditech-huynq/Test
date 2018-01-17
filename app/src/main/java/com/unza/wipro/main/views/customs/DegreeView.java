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

public class DegreeView extends View {
    private Paint mPaint1;
    private Paint mPaint2;
    private int background;
    private int displayDegree;
    private long maxvalue;
    private long value;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context context;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public int getBackground1() {
        return background;
    }

    public int getDisplayDegree() {
        return displayDegree;
    }

    public void setBackground1(int background1) {
        this.background = background1;
    }

    public void setDisplayDegree(int displayDegree) {
        this.displayDegree = displayDegree;
    }

    public void setMaxvalue(long maxvalue) {
        this.maxvalue = maxvalue;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public DegreeView(Context context) {
        super(context);
        this.context = context;
        reset();
    }

    public DegreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        reset();
    }

    public DegreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if (width >= height * 2) {
            radius = height;
            center_x = width / 2;
            center_y = height;
            text_size = (int) (2 * 2 * height / 25);
        } else {
            radius = width / 2;
            center_x = width / 2;
            center_y = height / 2;
            text_size = (int) (2 * width / 25);
        }
        final RectF oval = new RectF();
        int sweepAngle = (int) ((180 * value) / maxvalue);
        String text = String.valueOf(value * 100 / maxvalue) + " %";
        oval.set(center_x - radius,
                center_y - radius,
                center_x + radius,
                center_y + radius);
        mCanvas.drawArc(oval, 180, 180, true, mPaint1);
        mCanvas.drawArc(oval, 180, sweepAngle, true, mPaint2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(text_size);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text, center_x, center_y - text_size / 2, paint);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void setValue(int background, int displayDegree, long value, long maxvalue) {
        if (value <= maxvalue) {
            this.background = background;
            this.displayDegree = displayDegree;
            this.maxvalue = maxvalue;
            this.value = value;
            mPaint1.setColor(context.getResources().getColor(getBackground1()));
            mPaint2.setColor(context.getResources().getColor(getDisplayDegree()));
            invalidate();
        }
    }

    public void reset() {
        setBackground1(R.color.white);
        setDisplayDegree(R.color.colorPrimaryDark);
        setValue(0);
        setMaxvalue(100);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(context.getResources().getColor(getBackground1()));
        mPaint2.setColor(context.getResources().getColor(getDisplayDegree()));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setStyle(Paint.Style.FILL);
    }
}
