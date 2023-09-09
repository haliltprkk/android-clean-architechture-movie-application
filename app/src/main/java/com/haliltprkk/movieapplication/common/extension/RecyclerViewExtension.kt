package com.haliltprkk.movieapplication.common.extension

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addSimpleVerticalDecoration(
    spaceInDp: Int = 10,
    includeFirstItem: Boolean,
    includeLastItem: Boolean
) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val itemCount = state.itemCount
            val pos = parent.getChildAdapterPosition(view)
            val isLastItem = (pos == itemCount - 1)
            val isFirstItem = pos == 0
            //
            if (isFirstItem && includeFirstItem) {
                outRect.top = spaceInDp.toPx
                outRect.bottom = spaceInDp.toPx
            } else if (isFirstItem && !includeFirstItem) {
                outRect.top = 0.toPx
                outRect.bottom = spaceInDp.toPx
            } else if (isLastItem && includeLastItem) {
                outRect.top = 0.toPx
                outRect.bottom = spaceInDp.toPx
            } else if (isLastItem && !includeLastItem) {
                outRect.top = 0.toPx
                outRect.bottom = 0.toPx
            } else {
                outRect.bottom = spaceInDp.toPx
                outRect.top = 0.toPx
            }
        }
    })
}
