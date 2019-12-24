package com.github.mywheellibrary.touchEvent

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs
import kotlin.math.min

/**
 * 这个类是为了展示如何拦截上下左右滑动
 *
 * ****/
class DragLayout : RelativeLayout {
    companion object {
        private const val TAG = "DragLayout"
    }

    private lateinit var mDragHelper: ViewDragHelper
    private var mDragView: View? = null

    private var canHorizontalDrag = true
    private var canVerticalDrag = true
    private var canOverstepBoundaryInHor = false
    private var canOverstepBoundaryInVer = false
    private var slideCloseListener: OnSlideCloseListener? = null
    private var slideDistance = 0
    private var canInterceptTouchEvent = true
    private var yVelocity = 0F
    private var mActivePointerId = ViewDragHelper.INVALID_POINTER
    private var touchSlop: Int = 0
    private var direction = Direction.NONE
    private var mLastMotionX: Int = 0
    private var mLastMotionY: Int = 0

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mDragHelper = ViewDragHelper.create(this, 0.8f, ViewDragCallBack())
        val configuration = ViewConfiguration.get(context)
        touchSlop = configuration.scaledTouchSlop
    }

    fun setDragView(dragView: View) {
        mDragView = dragView
    }

    /**
     * 设置是否可以水平拖动
     */
    fun setCanHorizontalDrag(canHorizontalDrag: Boolean) {
        this.canHorizontalDrag = canHorizontalDrag
    }

    /**
     * 设置是否可以上下拖动
     */
    fun setCanVerticalDrag(canVerticalDrag: Boolean) {
        this.canVerticalDrag = canVerticalDrag
    }

    /**
     * 设置水平拖动是否可以越界
     */
    fun setCanOverstepBoundaryInHor(canOverstepBoundaryInHor: Boolean) {
        this.canOverstepBoundaryInHor = canOverstepBoundaryInHor
    }

    /**
     * 设置上下拖动是否可以越界
     */
    fun setCanOverstepBoundaryInVer(canOverstepBoundaryInVer: Boolean) {
        this.canOverstepBoundaryInVer = canOverstepBoundaryInVer
    }

    /**
     * 设置滑动关闭监听
     */
    fun setOnSlideCloseListener(listener: OnSlideCloseListener) {
        this.slideCloseListener = listener
    }

    fun setCanInterceptTouchEvent(canInterceptTouchEvent: Boolean) {
        this.canInterceptTouchEvent = canInterceptTouchEvent
    }

    private inner class ViewDragCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return mDragView == null || mDragView?.id == child.id
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return if (!canHorizontalDrag) 0 else measuredWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return measuredHeight
        }

        /**
         * 处理水平方向上的拖动
         * @param child 拖动的View
         * @param left  移动到x轴的距离
         * @param dx  建议的移动的x距离
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            if (canHorizontalDrag) {
                if (left < paddingLeft) {
                    return paddingLeft
                }
                if (canOverstepBoundaryInHor) {
                    return left
                } else {
                    return min(left, width - child.measuredWidth)
                }
            } else {
                return 0
            }
        }

        /**
         * 处理垂直方向上的拖动
         * @param child 拖动的View
         * @param top  移动到y轴的距离
         * @param dy  建议的移动的y距离
         */
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            if (canVerticalDrag) {
                slideDistance = top
                if (top < 0) {
                    slideDistance = 0
                }
                if (slideDistance < paddingTop) {
                    return paddingTop
                }
                if (canOverstepBoundaryInVer) {
                    return slideDistance
                } else {
                    return min(slideDistance, height - child.measuredHeight)
                }
            } else {
                return 0
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            yVelocity = yvel
            mDragHelper.settleCapturedViewAt(releasedChild.left, slideDistance)
            postInvalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val maxWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val maxHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0), View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0))

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mDragView?.layout(0, slideDistance, r, slideDistance + b)
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val ret = super.dispatchTouchEvent(ev)
        Log.d(TAG, "dispatchTouchEvent end() : ret =$ret  ev =$ev")
        return ret
    }

    /**
     *onInterceptTouchEvent　会调用　onTouchEnvent
     *layout本身是上下滑动的，但又支持左右滑动，如果子view支持左右左右滑动
     *为了避免子view响应左右滑动问题，需要在这里拦住
     * **/

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var ret = false
        when (ev.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mDragHelper.cancel()
        }

        //　这里判断了左右上下滑动
        when (ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                if (direction == Direction.NONE) {
                    val activePointerId = mActivePointerId
                    // If we don't have a valid id, the touch down wasn't on content.
                    if (activePointerId != ViewDragHelper.INVALID_POINTER) {
                        val pointerIndex = ev.findPointerIndex(activePointerId)
                        if (pointerIndex != -1) {
                            val x = ev.getX(pointerIndex).toInt()
                            val xDiff = abs(x - mLastMotionX)
                            val y = ev.getY(pointerIndex).toInt()
                            val yDiff = abs(y - mLastMotionY)
                            if (yDiff > touchSlop) {
                                direction = Direction.VERTICAL
                            }else if(xDiff > touchSlop){
                                direction = Direction.HORIZENTAL
                            }
                        }
                    }
                }
            }

            MotionEvent.ACTION_DOWN -> {
                direction = Direction.NONE
                mActivePointerId = ev.getPointerId(0)
                mLastMotionX = ev.x.toInt()
                mLastMotionY = ev.y.toInt()
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mActivePointerId = ViewDragHelper.INVALID_POINTER
                direction = Direction.NONE
            }
        }

        //　这里拦截了左右滑动
        if (!canHorizontalDrag && direction == Direction.HORIZENTAL) {
            return false
        }
        if (canInterceptTouchEvent) {
            ret = mDragHelper.shouldInterceptTouchEvent(ev)
        }
        Log.d(TAG, "onInterceptTouchEvent() ret = $ret, ev =$ev, direction = $direction")
        return ret
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG, "onTouchEvent() : event =$event")

        mDragHelper.processTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            if (slideDistance > height / 2 || yVelocity >= 2000F) {
                slideCloseListener?.onClose()
                mDragHelper.cancel()
            } else {
                slideDistance = 0
                recoverSlideTop()
            }
        }
        return true
    }

    private fun recoverSlideTop() {
        if (mDragHelper.smoothSlideViewTo(mDragView!!, mDragView!!.left, paddingTop)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private enum class Direction{
        NONE,
        VERTICAL,
        HORIZENTAL
    }
}

interface OnSlideCloseListener {
    fun onClose()
}