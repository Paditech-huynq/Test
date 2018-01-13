package com.unza.wipro.main.views.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class DynamicHeightSquareImageView extends android.support.v7.widget.AppCompatImageView {

    public DynamicHeightSquareImageView(Context context) {
        super(context);
    }

    public DynamicHeightSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
        Log.e("height",heightMeasureSpec+"/"+widthMeasureSpec);
    }
}