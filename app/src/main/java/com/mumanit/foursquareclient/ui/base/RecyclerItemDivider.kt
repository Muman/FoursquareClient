package com.mumanit.foursquareclient.ui.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDivider : RecyclerView.ItemDecoration {

    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private val mDivider: Drawable

    constructor(dividerDrawable: Drawable) {
        this.mDivider = dividerDrawable
    }

    constructor(context : Context) {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft;
        val right = parent.width - parent.paddingRight;

        val childCount = parent.childCount;

        for(i: Int in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}