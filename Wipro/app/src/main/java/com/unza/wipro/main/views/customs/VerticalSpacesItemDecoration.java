package com.unza.wipro.main.views.customs;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public VerticalSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        int currentPos = parent.getChildLayoutPosition(view);
        // Add top margin only for the first item to avoid double space between items
        if (currentPos == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }

    }
}