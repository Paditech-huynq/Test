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
    public DegreeView(Context context) {
        super(context);
    }

        private Paint mPaint1;
    private Paint mPaint2;
    private int background;
    private int displayDegree;
    private long maxvalue;
    private long value;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context context;
    private static final float SCALE_NUMBER_DEGREE_WITH_VIEW = 2/25;
    private static final int DEGREE_180 = 180;
    private static final float HALF = 1/2;
    private static final int SCALE_NUMBER_IF_WIDTH_TOO_BIG_FOR_HEIGHT = 2;
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
        int text_size;
        float coordinateXToDraw;
        float coordinateYToDraw;
        float radius;
        if (width >= height * SCALE_NUMBER_IF_WIDTH_TOO_BIG_FOR_HEIGHT) {
            radius = height;
            coordinateXToDraw = (int) width * HALF;
            coordinateYToDraw = height;
            text_size = (int) (height * SCALE_NUMBER_DEGREE_WITH_VIEW);
        } else {
            radius = width * HALF;
            coordinateXToDraw = (int) width *HALF;
            coordinateYToDraw = (int) height *HALF;
            text_size = (int) (width * SCALE_NUMBER_DEGREE_WITH_VIEW);
        }
        final RectF oval = new RectF();
        int sweepAngle = (int) ((DEGREE_180 * value) / maxvalue);
        String text = String.valueOf(value * 100 / maxvalue) + " %";
        oval.set(coordinateXToDraw - radius,
                coordinateYToDraw - radius,
                coordinateXToDraw + radius,
                coordinateYToDraw + radius);
        mCanvas.drawArc(oval, DEGREE_180, DEGREE_180, true, mPaint1);
        mCanvas.drawArc(oval, DEGREE_180, sweepAngle, true, mPaint2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(text_size);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text, coordinateXToDraw, coordinateYToDraw - text_size / 2, paint);
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
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint2.setAntiAlias(true);
        mPaint1.setColor(context.getResources().getColor(getBackground1()));
        mPaint2.setColor(context.getResources().getColor(getDisplayDegree()));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setStyle(Paint.Style.FILL);
    }
}
