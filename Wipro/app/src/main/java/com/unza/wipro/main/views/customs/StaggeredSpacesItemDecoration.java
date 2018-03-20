package com.unza.wipro.main.views.customs;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class StaggeredSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public StaggeredSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space * 2;

        int currentPos = parent.getChildLayoutPosition(view);
        // Add top margin only for the first item to avoid double space between items
        if (currentPos == 0 || currentPos == 1) {
            outRect.top = space * 2;
        } else {
            outRect.top = 0;
        }

    }
}