package com.soradesona.shiba

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaddingItemDecoration : RecyclerView.ItemDecoration() {
    private val paddingSpace = 8

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(paddingSpace, paddingSpace, paddingSpace, paddingSpace)
    }
}
