package com.giaquino.sample.common.adapter.decorator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/26/16
 */
public class MarginItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalMargin;
    private final int horizontalMargin;

    public MarginItemDecorator(int verticalMargin, int horizontalMargin) {
        this.verticalMargin = verticalMargin;
        this.horizontalMargin = horizontalMargin;
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {
        outRect.top = verticalMargin;
        outRect.left = horizontalMargin;
        outRect.right = horizontalMargin;
        if (parent.getChildLayoutPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = verticalMargin;
        }
    }

    @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
