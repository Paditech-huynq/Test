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
    private Paint mPaintDrawBackground;
    private Paint mPaintDrawDegree;
    private int background;
    private int displayDegree;
    private long maxvalue;
    private long value;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Context context;
    private static final float SCALE_FACTOR_FOR_SMALLER_HEIGHT = 6;
    private static final float SCALE_FACTOR_FOR_SMALLER_WIDTH = 12;
    private static final int DEGREE_BALANCE = 180;
    private static final int SCALE_FACTOR_FOR_BIG_WIDTH = 2;
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
        int text_size;
        float coordinateXToDraw;
        float coordinateYToDraw;
        float radius;
        if (width >= height * SCALE_FACTOR_FOR_BIG_WIDTH) {
            radius = height;
            coordinateXToDraw = width / 2;
            coordinateYToDraw = height;
            text_size = (int) (height / SCALE_FACTOR_FOR_SMALLER_HEIGHT);
        } else {
            radius = width / 2;
            coordinateXToDraw = width / 2;
            coordinateYToDraw = height / 2;
            text_size = (int) (width * SCALE_FACTOR_FOR_SMALLER_WIDTH
);
        }
        final RectF oval = new RectF();
        int sweepAngle = (int) ((DEGREE_BALANCE * value) / maxvalue);
        String text = String.valueOf(value * 100 / maxvalue) + " %";
        oval.set(coordinateXToDraw - radius,
                coordinateYToDraw - radius,
                coordinateXToDraw + radius,
                coordinateYToDraw + radius);
        mCanvas.drawArc(oval, DEGREE_BALANCE, DEGREE_BALANCE, true, mPaintDrawBackground);
        mCanvas.drawArc(oval, DEGREE_BALANCE, sweepAngle, true, mPaintDrawDegree);
        Paint paintDrawText = new Paint();
        paintDrawText.setAntiAlias(true);
        paintDrawText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintDrawText.setTextSize(text_size);
        paintDrawText.setStrokeWidth(1);
        paintDrawText.setTextAlign(Paint.Align.CENTER);
        mCanvas.drawText(text, coordinateXToDraw, coordinateYToDraw - (text_size / 2), paintDrawText);
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
            mPaintDrawBackground.setColor(context.getResources().getColor(getBackground1()));
            mPaintDrawDegree.setColor(context.getResources().getColor(getDisplayDegree()));
            invalidate();
        }
    }

    public void reset() {
        setBackground1(R.color.white);
        setDisplayDegree(R.color.colorPrimaryDark);
        setValue(0);
        setMaxvalue(100);
        mPaintDrawBackground = new Paint();
        mPaintDrawDegree = new Paint();
        mPaintDrawBackground.setAntiAlias(true);
        mPaintDrawDegree.setAntiAlias(true);
        mPaintDrawBackground.setColor(context.getResources().getColor(getBackground1()));
        mPaintDrawDegree.setColor(context.getResources().getColor(getDisplayDegree()));
        mPaintDrawBackground.setStyle(Paint.Style.FILL);
        mPaintDrawDegree.setStyle(Paint.Style.FILL);
    }
}
