package com.alway.lequ_kotlin.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

/**
 * 手势图片控件
 */
class PinchImageView : ImageView {


    //外界点击事件
    private var mOnClickListener: View.OnClickListener? = null

    //外界长按事件
    private var mOnLongClickListener: View.OnLongClickListener? = null


    //外层变换矩阵，如果是单位矩阵，那么图片是fit center状态
    private var mOuterMatrix = Matrix()

    //手势状态，值为PINCH_MODE_FREE，PINCH_MODE_SCROLL，PINCH_MODE_SCALE
    //获取当前手势状态
    var pinchMode = PINCH_MODE_FREE
        private set


    //在单指模式下是上次手指触碰的点
    //在多指模式下两个缩放控制点的中点
    private var mLastMovePoint = PointF()

    //缩放模式下图片的缩放中点，这个点是在原图进行内层变换后的点
    private var mScaleCenter = PointF()

    //缩放模式下的缩放比例，为 外层缩放值 / 开始缩放时两指距离
    private var mScaleBase = 0f


    //矩阵动画，缩放模式把图片的位置大小超出限制之后触发；双击图片放大或缩小时触发
    private var mScaleAnimator: ScaleAnimator? = null

    //滑动产生的惯性动画
    private var mFlingAnimator: FlingAnimator? = null


    //获取外部矩阵
    val outerMatrix: Matrix
        get() = Matrix(mOuterMatrix)

    //获取内部矩阵，换了图之后如果图片大小不一样，会重新计算个新的从而保证fit center状态
    //返回的是copy值
    //控件大小
    //原图大小
    //如果计算fit center状态所需的scale大小
    //设置fit center状态的scale和位置
    val innerMatrix: Matrix
        get() {
            val result = Matrix()
            if (drawable != null) {
                val displayWidth = measuredWidth.toFloat()
                val displayHeight = measuredHeight.toFloat()
                if (displayWidth > 0 && displayHeight > 0) {
                    val imageWidth = drawable.intrinsicWidth.toFloat()
                    val imageHeight = drawable.intrinsicHeight.toFloat()
                    if (imageWidth > 0 && imageHeight > 0) {
                        val scale: Float
                        if (imageWidth / imageHeight > displayWidth / displayHeight) {
                            scale = displayWidth / imageWidth
                        } else {
                            scale = displayHeight / imageHeight
                        }
                        result.postScale(scale, scale, imageWidth / 2f, imageHeight / 2f)
                        result.postTranslate((displayWidth - imageWidth) / 2f, (displayHeight - imageHeight) / 2f)
                    }
                }
            }
            return result
        }

    //获取图片总变换矩阵
    val currentImageMatrix: Matrix
        get() {
            val result = innerMatrix
            result.postConcat(mOuterMatrix)
            return result
        }

    //获取当前图片变换后的矩形，如果没有图片则返回null
    val imageBound: RectF?
        get() {
            if (drawable == null) {
                return null
            } else {
                val matrix = currentImageMatrix
                val bound = RectF(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
                matrix.mapRect(bound)
                return bound
            }
        }


    //获取图片最大可放大的比例，如果放大大于这个比例则不被允许
    protected val maxScale: Float
        get() = MAX_SCALE


    //点击，双击，长按，滑动等手势处理
    private val mGestureDetector = GestureDetector(this@PinchImageView.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            fling(velocityX, velocityY)
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            if (mOnLongClickListener != null) {
                mOnLongClickListener!!.onLongClick(this@PinchImageView)
            }
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            doubleTap(e.x, e.y)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (mOnClickListener != null) {
                mOnClickListener!!.onClick(this@PinchImageView)
            }
            return true
        }
    })


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    private fun initView() {
        //强制设置图片scaleType为matrix
        super.setScaleType(ImageView.ScaleType.MATRIX)
    }


    override fun onDraw(canvas: Canvas) {
        //在绘制前设置变换矩阵
        if (drawable != null) {
            imageMatrix = currentImageMatrix
        }
        super.onDraw(canvas)
    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        //默认的click会在任何点击情况下都会触发，所以搞成自己的
        mOnClickListener = l
    }

    override fun setOnLongClickListener(l: View.OnLongClickListener?) {
        //默认的long click会在任何长按情况下都会触发，所以搞成自己的
        mOnLongClickListener = l
    }

    //不允许设置scaleType，只能用内部设置的matrix
    override fun setScaleType(scaleType: ImageView.ScaleType) {}

    //开始手势禁止模式
    fun startNoPinch() {
        pinchMode = PINCH_MODE_NO_PINCH
        mLastMovePoint = PointF()
        mScaleCenter = PointF()
        mScaleBase = 0f
    }

    fun animFromTo(matrixFrom: Matrix, matrixTo: Matrix): Boolean {
        if (drawable == null) {
            return false
        }
        if (mScaleAnimator != null) {
            mScaleAnimator!!.cancel()
            mScaleAnimator = null
        }
        if (mFlingAnimator != null) {
            mFlingAnimator!!.cancel()
            mFlingAnimator = null
        }
        mScaleAnimator = ScaleAnimator(matrixFrom, matrixTo)
        mScaleAnimator!!.start()
        return true
    }

    //停止手势禁止
    fun endNoPinch() {
        pinchMode = PINCH_MODE_FREE
    }

    //停止所有动画，重置位置到fit center状态
    fun reset() {
        mOuterMatrix = Matrix()
        onOuterMatrixChanged()
        pinchMode = PINCH_MODE_FREE
        mLastMovePoint = PointF()
        mScaleCenter = PointF()
        mScaleBase = 0f
        if (mScaleAnimator != null) {
            mScaleAnimator!!.cancel()
            mScaleAnimator = null
        }
        if (mFlingAnimator != null) {
            mFlingAnimator!!.cancel()
            mFlingAnimator = null
        }
        invalidate()
    }

    //计算双击之后图片应该被缩放的比例，如果值大于getMaxScale或者小于fit center尺寸，则取边界值
    protected fun calculateNextScale(innerScale: Float, outerScale: Float): Float {
        val currentScale = innerScale * outerScale
        return if (currentScale < MAX_SCALE) {
            MAX_SCALE
        } else {
            innerScale
        }
    }

    //当外层矩阵变换时触发
    protected fun onOuterMatrixChanged() {
        //用于超大图分片加载
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        if (pinchMode == PINCH_MODE_NO_PINCH) {
            return true
        }
        //无论如何都处理各种外部手势
        mGestureDetector.onTouchEvent(event)
        val action = event.action and MotionEvent.ACTION_MASK
        //最后一个点抬起或者取消，结束所有模式
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if (pinchMode == PINCH_MODE_SCALE) {
                scaleEnd()
            }
            pinchMode = PINCH_MODE_FREE
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            if (pinchMode == PINCH_MODE_SCALE) {
                //抬起的点如果大于2，那么缩放模式还有效，但是有可能初始点变了，重新测量初始点
                if (event.pointerCount > 2) {
                    //如果还没结束缩放模式，但是第一个点抬起了，那么让第二个点和第三个点作为缩放控制点
                    if (event.action shr 8 == 0) {
                        saveScaleContext(event.getX(1), event.getY(1), event.getX(2), event.getY(2))
                        //如果还没结束缩放模式，但是第二个点抬起了，那么让第一个点和第三个点作为缩放控制点
                    } else if (event.action shr 8 == 1) {
                        saveScaleContext(event.getX(0), event.getY(0), event.getX(2), event.getY(2))
                    }
                }
            }
            //第一个点按下，开启滚动模式，记录开始滚动的点
        } else if (action == MotionEvent.ACTION_DOWN) {
            //在矩阵动画过程中不允许启动滚动模式
            if (!(mScaleAnimator != null && mScaleAnimator!!.isRunning)) {
                //停止惯性滚动
                if (mFlingAnimator != null) {
                    mFlingAnimator!!.cancel()
                    mFlingAnimator = null
                }
                pinchMode = PINCH_MODE_SCROLL
                mLastMovePoint.set(event.x, event.y)
            }
            //非第一个点按下，关闭滚动模式，开启缩放模式，记录缩放模式的一些初始数据
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            //停止惯性滚动
            if (mFlingAnimator != null) {
                mFlingAnimator!!.cancel()
                mFlingAnimator = null
            }
            //停止矩阵动画
            if (mScaleAnimator != null) {
                mScaleAnimator!!.cancel()
                mScaleAnimator = null
            }
            pinchMode = PINCH_MODE_SCALE
            saveScaleContext(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (!(mScaleAnimator != null && mScaleAnimator!!.isRunning)) {
                //在滚动模式下移动
                if (pinchMode == PINCH_MODE_SCROLL) {
                    //每次移动产生一个差值累积到图片位置上
                    scrollBy(event.x - mLastMovePoint.x, event.y - mLastMovePoint.y)
                    //记录新的移动点
                    mLastMovePoint.set(event.x, event.y)
                    //在缩放模式下移动
                } else if (pinchMode == PINCH_MODE_SCALE && event.pointerCount > 1) {
                    //两个缩放点间的距离
                    val distance = MathUtils.getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
                    //保存缩放点中点
                    val lineCenter = MathUtils.getCenterPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1))
                    mLastMovePoint.set(lineCenter[0], lineCenter[1])
                    //处理缩放
                    scale(mScaleCenter, mScaleBase, distance, mLastMovePoint)
                }
            }
        }
        return true
    }

    //让图片移动一段距离，返回是否真的移动了
    private fun scrollBy(xDiff: Float, yDiff: Float): Boolean {
        var xDiff = xDiff
        var yDiff = yDiff
        if (drawable == null) {
            return false
        }
        //原图方框
        val bound = imageBound
        //控件大小
        val displayWidth = measuredWidth.toFloat()
        val displayHeight = measuredHeight.toFloat()
        //如果当前图片宽度小于控件宽度，则不能移动
        if (bound!!.right - bound.left < displayWidth) {
            xDiff = 0f
            //如果图片左边在移动后超出控件左边
        } else if (bound.left + xDiff > 0) {
            //如果在移动之前是没超出的，计算应该移动的距离
            if (bound.left < 0) {
                xDiff = -bound.left
                //否则无法移动
            } else {
                xDiff = 0f
            }
            //如果图片右边在移动后超出控件右边
        } else if (bound.right + xDiff < displayWidth) {
            //如果在移动之前是没超出的，计算应该移动的距离
            if (bound.right > displayWidth) {
                xDiff = displayWidth - bound.right
                //否则无法移动
            } else {
                xDiff = 0f
            }
        }
        //以下同理
        if (bound.bottom - bound.top < displayHeight) {
            yDiff = 0f
        } else if (bound.top + yDiff > 0) {
            if (bound.top < 0) {
                yDiff = -bound.top
            } else {
                yDiff = 0f
            }
        } else if (bound.bottom + yDiff < displayHeight) {
            if (bound.bottom > displayHeight) {
                yDiff = displayHeight - bound.bottom
            } else {
                yDiff = 0f
            }
        }
        //应用移动变换
        mOuterMatrix.postTranslate(xDiff, yDiff)
        onOuterMatrixChanged()
        //触发重绘
        invalidate()
        //检查是否有变化
        return if (xDiff != 0f || yDiff != 0f) {
            true
        } else {
            false
        }
    }

    //记录缩放前的一些信息
    private fun saveScaleContext(x1: Float, y1: Float, x2: Float, y2: Float) {
        mScaleBase = MathUtils.getMatrixScale(mOuterMatrix)[0] / MathUtils.getDistance(x1, y1, x2, y2)
        //获取缩放缩放点中点在第一层变换后的图片上的坐标
        val center = MathUtils.inverseMatrixPoint(MathUtils.getCenterPoint(x1, y1, x2, y2), mOuterMatrix)
        mScaleCenter.set(center[0], center[1])
    }

    /**
     * 对图片进行缩放
     *
     * @param scaleCenter 图片的缩放中心，是一层变换后的左边
     * @param scaleBase   缩放比例
     * @param distance    新的缩放点距离
     * @param lineCenter  缩放点中心
     */
    private fun scale(scaleCenter: PointF, scaleBase: Float, distance: Float, lineCenter: PointF) {
        if (drawable == null) {
            return
        }
        //计算第二层缩放值
        val scale = scaleBase * distance
        val matrix = Matrix()
        //按照图片缩放中心缩放，并且让缩放中心在缩放点中点上
        matrix.postScale(scale, scale, scaleCenter.x, scaleCenter.y)
        matrix.postTranslate(lineCenter.x - scaleCenter.x, lineCenter.y - scaleCenter.y)
        //应用变换
        mOuterMatrix = matrix
        onOuterMatrixChanged()
        //重绘
        invalidate()
    }

    //双击后放大或者缩小
    //当当前缩放比例大于等于1，那么双击放大到MaxScale
    //当当前缩放比例小于1，双击放大到1
    //当当前缩放比例等于MaxScale，双击缩小到屏幕大小
    private fun doubleTap(x: Float, y: Float) {
        //不允许动画过程中再触发
        if (mScaleAnimator != null && mScaleAnimator!!.isRunning || drawable == null) {
            return
        }
        //获取第一层变换矩阵
        val innerMatrix = innerMatrix
        //当前总的缩放比例
        val innerScale = MathUtils.getMatrixScale(innerMatrix)[0]
        val outerScale = MathUtils.getMatrixScale(mOuterMatrix)[0]
        val currentScale = innerScale * outerScale
        //控件大小
        val displayWidth = measuredWidth.toFloat()
        val displayHeight = measuredHeight.toFloat()
        //最大放大大小
        val maxScale = maxScale
        //接下来要放大的大小
        var nextScale = calculateNextScale(innerScale, outerScale)
        //如果接下来放大大于最大值或者小于fit center值，则取边界
        if (nextScale < innerScale) {
            nextScale = innerScale
        } else if (nextScale > maxScale) {
            nextScale = maxScale
        }
        //缩放动画初始矩阵为当前矩阵值
        val animStart = Matrix(mOuterMatrix)
        //开始计算缩放动画的结果矩阵
        val animEnd = Matrix(mOuterMatrix)
        //计算还需缩放的倍数
        animEnd.postScale(nextScale / currentScale, nextScale / currentScale, x, y)
        //将放大点移动到控件中心
        animEnd.postTranslate(displayWidth / 2 - x, displayHeight / 2 - y)
        //得到放大之后的图片方框
        val testMatrix = Matrix(innerMatrix)
        testMatrix.postConcat(animEnd)
        val testBound = RectF(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
        testMatrix.mapRect(testBound)
        //修正位置
        var postX = 0f
        var postY = 0f
        if (testBound.right - testBound.left < displayWidth) {
            postX = displayWidth / 2 - (testBound.right + testBound.left) / 2
        } else if (testBound.left > 0) {
            postX = -testBound.left
        } else if (testBound.right < displayWidth) {
            postX = displayWidth - testBound.right
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            postY = displayHeight / 2 - (testBound.bottom + testBound.top) / 2
        } else if (testBound.top > 0) {
            postY = -testBound.top
        } else if (testBound.bottom < displayHeight) {
            postY = displayHeight - testBound.bottom
        }
        //应用修正位置
        animEnd.postTranslate(postX, postY)
        //如果正在执行惯性动画，则取消掉
        if (mFlingAnimator != null) {
            mFlingAnimator!!.cancel()
            mFlingAnimator = null
        }
        //启动矩阵动画
        mScaleAnimator = ScaleAnimator(animStart, animEnd)
        mScaleAnimator!!.start()
    }

    //当缩放操作结束如果不在正确位置用动画恢复
    private fun scaleEnd() {
        //不允许动画过程中再触发
        if (mScaleAnimator != null && mScaleAnimator!!.isRunning || drawable == null) {
            return
        }
        //是否修正了位置
        var change = false
        //获取图片整体的变换矩阵
        val currentMatrix = currentImageMatrix
        //整体缩放比例
        val currentScale = MathUtils.getMatrixScale(currentMatrix)[0]
        //第二层缩放比例
        val outerScale = MathUtils.getMatrixScale(mOuterMatrix)[0]
        //控件大小
        val displayWidth = measuredWidth.toFloat()
        val displayHeight = measuredHeight.toFloat()
        val maxScale = maxScale
        //比例修正
        var scalePost = 1f
        //位置修正
        var postX = 0f
        var postY = 0f
        //如果整体缩放比例大于最大比例，进行缩放修正
        if (currentScale > maxScale) {
            scalePost = maxScale / currentScale
        }
        //如果缩放修正后整体导致第二层缩放小于1（就是图片比fit center状态还小），重新修正缩放
        if (outerScale * scalePost < 1) {
            scalePost = 1 / outerScale
        }
        //如果修正不为1，说明进行了修正
        if (scalePost != 1f) {
            change = true
        }
        //尝试根据缩放点进行缩放修正
        val testMatrix = Matrix(currentMatrix)
        testMatrix.postScale(scalePost, scalePost, mLastMovePoint.x, mLastMovePoint.y)
        val testBound = RectF(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
        //获取缩放修正后的图片方框
        testMatrix.mapRect(testBound)
        //检测缩放修正后位置有无超出，如果超出进行位置修正
        if (testBound.right - testBound.left < displayWidth) {
            postX = displayWidth / 2 - (testBound.right + testBound.left) / 2
        } else if (testBound.left > 0) {
            postX = -testBound.left
        } else if (testBound.right < displayWidth) {
            postX = displayWidth - testBound.right
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            postY = displayHeight / 2 - (testBound.bottom + testBound.top) / 2
        } else if (testBound.top > 0) {
            postY = -testBound.top
        } else if (testBound.bottom < displayHeight) {
            postY = displayHeight - testBound.bottom
        }
        //如果位置修正不为0，说明进行了修正
        if (postX != 0f || postY != 0f) {
            change = true
        }
        //只有有执行修正才执行动画
        if (change) {
            //如果up的时候触发惯性，这里需要取消掉，以修正动画为主
            if (mFlingAnimator != null) {
                mFlingAnimator!!.cancel()
                mFlingAnimator = null
            }
            //动画开始举证
            val animStart = Matrix(mOuterMatrix)
            //计算结束举证
            val animEnd = Matrix(mOuterMatrix)
            animEnd.postScale(scalePost, scalePost, mLastMovePoint.x, mLastMovePoint.y)
            animEnd.postTranslate(postX, postY)
            //启动矩阵动画
            mScaleAnimator = ScaleAnimator(animStart, animEnd)
            mScaleAnimator!!.start()
        }
    }

    private fun fling(vx: Float, vy: Float) {
        //以修正动画为大，遇到修正动画正在执行，就不执行惯性动画
        if (!(mScaleAnimator != null && mScaleAnimator!!.isRunning) && drawable != null) {
            if (mFlingAnimator != null) {
                mFlingAnimator!!.cancel()
                mFlingAnimator = null
            }
            mFlingAnimator = FlingAnimator(floatArrayOf(vx / 1000 * 16, vy / 1000 * 16))
            mFlingAnimator!!.start()
        }
    }

    //惯性动画
    private inner class FlingAnimator(private val mVector: FloatArray) : ValueAnimator(), ValueAnimator.AnimatorUpdateListener {

        init {
            setFloatValues(0f, 1f)
            duration = 1000000
            addUpdateListener(this)
        }

        override fun onAnimationUpdate(animation: ValueAnimator) {
            val result = scrollBy(mVector[0], mVector[1])
            mVector[0] *= FLING_DAMPING_FACTOR
            mVector[1] *= FLING_DAMPING_FACTOR
            if (!result || MathUtils.getDistance(0f, 0f, mVector[0], mVector[1]) < 1) {
                animation.cancel()
            }
        }
    }

    //缩放动画
    private inner class ScaleAnimator(start: Matrix, end: Matrix) : ValueAnimator(), ValueAnimator.AnimatorUpdateListener {

        private val mStart = FloatArray(9)
        private val mEnd = FloatArray(9)

        init {
            setFloatValues(0f, 1f)
            duration = SCALE_ANIMATOR_DURATION.toLong()
            addUpdateListener(this)
            start.getValues(mStart)
            end.getValues(mEnd)
        }

        override fun onAnimationUpdate(animation: ValueAnimator) {
            val value = animation.animatedValue as Float
            val result = FloatArray(9)
            for (i in 0..8) {
                result[i] = mStart[i] + (mEnd[i] - mStart[i]) * value
            }
            mOuterMatrix.setValues(result)
            onOuterMatrixChanged()
            invalidate()
        }
    }

    //数学计算工具类
    private object MathUtils {

        //获取两点距离
        fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            val x = x1 - x2
            val y = y1 - y2
            return Math.sqrt((x * x + y * y).toDouble()).toFloat()
        }

        //获取两点中间点
        fun getCenterPoint(x1: Float, y1: Float, x2: Float, y2: Float): FloatArray {
            return floatArrayOf((x1 + x2) / 2f, (y1 + y2) / 2f)
        }

        //获取矩阵的缩放值
        fun getMatrixScale(matrix: Matrix?): FloatArray {
            if (matrix != null) {
                val value = FloatArray(9)
                matrix.getValues(value)
                return floatArrayOf(value[0], value[4])
            } else {
                return FloatArray(2)
            }
        }

        //计算点除以矩阵之后的值
        fun inverseMatrixPoint(point: FloatArray?, matrix: Matrix?): FloatArray {
            if (point != null && matrix != null) {
                val dst = FloatArray(2)
                val inverse = Matrix()
                matrix.invert(inverse)
                inverse.mapPoints(dst, point)
                return dst
            } else {
                return FloatArray(2)
            }
        }
    }

    companion object {

        //图片缩放动画时间
        val SCALE_ANIMATOR_DURATION = 200

        //惯性动画衰减参数
        val FLING_DAMPING_FACTOR = 0.9f

        //图片最大放大尺寸
        private val MAX_SCALE = 4f


        //手势状态：自由状态
        val PINCH_MODE_FREE = 0

        //手势状态：单指滚动状态
        val PINCH_MODE_SCROLL = 1

        //手势状态：多指缩放状态
        val PINCH_MODE_SCALE = 2

        //手势状态：禁止手势
        val PINCH_MODE_NO_PINCH = 3
    }
}