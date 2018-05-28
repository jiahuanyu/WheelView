package me.jiahuan.android.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;


public class WheelView extends View {
    private static final String TAG = WheelView.class.getSimpleName();

    private static final int INVALID_INDEX = -1;

    // 参数默认值
    private static final int DEFAULT_VALUE_DISPLAY_ITEM_COUNT = 5;
    //    private static final int DEFAULT_VALUE_UNSELECTED_TEXT_SIZE = 14;
    private static final int DEFAULT_VALUE_UNSELECTED_TEXT_COLOR = Color.parseColor("#7C7C7C");
    // 默认字体大小
    private static final int DEFAULT_VALUE_TEXT_SIZE = 16;
    //    private static final int DEFAULT_VALUE_SELECTED_TEXT_SIZE = 30;
    private static final int DEFAULT_VALUE_SELECTED_TEXT_COLOR = Color.parseColor("#65A6E2");

    // 默认控件背景色
    private static final int DEFAULT_VALUE_COMPONENT_BACKGROUND_COLOR = Color.parseColor("#F3F3F3");
    // 默认控件边框宽度
    private static final int DEFAULT_VALUE_COMPONENT_BOUND_WIDTH = 2; //dp
    // 默认控件选中项上下边框的宽度
    private static final int DEFAULT_VALUE_COMPONENT_SELECTED_ITEM_UP_DOWN_LINE_WIDTH = 2; //dp
    // 默认控件选中项上下边框的颜色
    private static final int DEFAULT_VALUE_COMPONENT_SELECTED_ITEM_UP_DOWN_LINE_COLOR = Color.parseColor("#65A6E2");
    // 默认控件边框颜色
    private static final int DEFAULT_VALUE_BOUND_COLOR = Color.parseColor("#EDEDED");
    // 横向的间距
    private static final int DEFAULT_VALUE_HORIZONTAL_TEXT_SPACE = 80; // dp
    // 纵向的间距
    private static final int DEFAULT_VALUE_VERTICAL_TEXT_SPACE = 40; // dp


    // var
    // 未被选中文字的大小
//    private int mUnSelectedTextSize;
    // 未被选中文字的颜色
    private int mUnSelectedTextColor;

    private int mTextSize;

    // 选中文字的大小
//    private int mSelectedTextSize;
    // 选中文字的颜色
    private int mSelectedTextColor;

    // 控件背景色
    private int mComponentBackgroundColor;
    // 控件边框颜色
    private int mComponentBoundColor;
    // 控件边框宽度
    private int mComponentBoundWidth;
    // 控件选中项上下线的宽度
    private int mComponentSelectedItemUpDownLineWidth;
    // 控件选中项上下线的颜色
    private int mComponentSelectedItemUpDownLineColor;

    // text的最大宽度
    private int mTextMaxWidth;
    // text的最大高度
    private int mTextMaxHeight;

    // 滚轮试图中显示item的个数
    private int mDisplayItemCount;

    // 横向间距 2 *
    private int mHorizontalTextSpace;

    // 纵向间距 2 *
    private int mVerticalTextSpace;

    // 当前选中的item的索引
//    private int mSelectedItemIndex;

    // Item的宽度
//    private int mItemWidth;
    // Item的高度
    private int mItemHeight;

    // 控件宽度
    private int mComponentWidth;
    // 控件高度
    private int mComponentHeight;

    // 文字画笔
    private Paint mTextPaint;

    // 控件边框的画笔
    private Paint mComponentBoundPaint;
    // 参考线
    private Paint mReferenceLinePaint;
    // 控件选中项上下线的画笔
    private Paint mComponentSelectedItemUpDownLinePaint;

    // 文字大小
    private Rect mTextBoundRect = new Rect();

    // 数据
    private List<String> mDataList = null;


    // y轴纵向滚动
    private int mScrollerOffsetY;

    // 点击事件按下的Y
    private float mLastDownY;

    // 是否循环
    private boolean mIsCycle = false;

    // Scroller
    private Scroller mScroller;

    // 速度追踪器
    private VelocityTracker mVelocityTracker;

    // 宽度测量模式
    private int mWidthMeasureMode;

    // 高度测量模式
    private int mHeightMeasureMode;


    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }


    /**
     * 初始化入口
     */
    private void initialize(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelView);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

//        mUnSelectedTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, typedArray.getDimension(R.styleable.WheelView_unselected_text_size, DEFAULT_VALUE_UNSELECTED_TEXT_SIZE), displayMetrics);
        mUnSelectedTextColor = typedArray.getColor(R.styleable.WheelView_unselected_text_color, DEFAULT_VALUE_UNSELECTED_TEXT_COLOR);
//        mSelectedTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, typedArray.getDimension(R.styleable.WheelView_selected_text_size, DEFAULT_VALUE_SELECTED_TEXT_SIZE), displayMetrics);
        mSelectedTextColor = typedArray.getColor(R.styleable.WheelView_selected_text_color, DEFAULT_VALUE_SELECTED_TEXT_COLOR);
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, typedArray.getDimension(R.styleable.WheelView_text_size, DEFAULT_VALUE_TEXT_SIZE), displayMetrics);


        mHorizontalTextSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension(R.styleable.WheelView_horizontal_text_space, DEFAULT_VALUE_HORIZONTAL_TEXT_SPACE), displayMetrics);
        mVerticalTextSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension(R.styleable.WheelView_vertical_text_space, DEFAULT_VALUE_VERTICAL_TEXT_SPACE), displayMetrics);

        mDisplayItemCount = typedArray.getInt(R.styleable.WheelView_display_item_count, DEFAULT_VALUE_DISPLAY_ITEM_COUNT);
        if (mDisplayItemCount % 2 == 0) {
            Log.e(TAG, "display item count can not be even");
            throw new RuntimeException("display item count can not be even");
        }

        mComponentBoundWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension(R.styleable.WheelView_component_bound_width, DEFAULT_VALUE_COMPONENT_BOUND_WIDTH), displayMetrics);
        mComponentBoundColor = typedArray.getColor(R.styleable.WheelView_component_bound_color, DEFAULT_VALUE_BOUND_COLOR);
        mComponentSelectedItemUpDownLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension(R.styleable.WheelView_component_selected_item_up_down_line_width, DEFAULT_VALUE_COMPONENT_SELECTED_ITEM_UP_DOWN_LINE_WIDTH), displayMetrics);
        mComponentSelectedItemUpDownLineColor = typedArray.getColor(R.styleable.WheelView_component_selected_item_up_down_line_color, DEFAULT_VALUE_COMPONENT_SELECTED_ITEM_UP_DOWN_LINE_COLOR);


        mComponentBackgroundColor = typedArray.getColor(R.styleable.WheelView_component_background_color, DEFAULT_VALUE_COMPONENT_BACKGROUND_COLOR);

        mIsCycle = typedArray.getBoolean(R.styleable.WheelView_cycle, false);

        typedArray.recycle();


        // 初始化工具
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mComponentBoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mComponentBoundPaint.setColor(mComponentBoundColor);
        mComponentBoundPaint.setStyle(Paint.Style.STROKE);
        mComponentBoundPaint.setStrokeWidth(mComponentBoundWidth);


        mComponentSelectedItemUpDownLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mComponentSelectedItemUpDownLinePaint.setStrokeWidth(mComponentSelectedItemUpDownLineWidth);
        mComponentSelectedItemUpDownLinePaint.setColor(mComponentSelectedItemUpDownLineColor);

        mReferenceLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReferenceLinePaint.setColor(Color.RED);


        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }


    private int getTextColorWithRatio(float ratio) {
        int startRed = Color.red(mUnSelectedTextColor);
        int endRed = Color.red(mSelectedTextColor);

        int startGreen = Color.green(mUnSelectedTextColor);
        int endGreen = Color.green(mSelectedTextColor);

        int startBlue = Color.blue(mUnSelectedTextColor);
        int endBlue = Color.blue(mSelectedTextColor);

        int red = (int) (startRed + ((endRed - startRed) * ratio + 0.5f));
        int greed = (int) (startGreen + ((endGreen - startGreen) * ratio + 0.5f));
        int blue = (int) (startBlue + ((endBlue - startBlue) * ratio + 0.5f));

        return Color.rgb(red, greed, blue);
    }

    /**
     * 测量字体的rect
     *
     * @param paint
     * @param text
     * @param bounds
     */
    private void measureText(Paint paint, String text, Rect bounds) {
        paint.getTextBounds(text, 0, text.length(), bounds);
    }

    /**
     * 设置需要显示的数据
     *
     * @param stringDataList
     */
    public void setData(List<String> stringDataList) {
        if (stringDataList == null || stringDataList.size() == 0) {
            return;
        }
        mTextMaxWidth = mTextMaxHeight = 0;
        mDataList = stringDataList;
        // 测量最大文字宽度和高度
        for (String str : stringDataList) {
            measureText(mTextPaint, str, mTextBoundRect);
            if (mTextBoundRect.width() > mTextMaxWidth) {
                mTextMaxWidth = mTextBoundRect.width();
            }
            if (mTextBoundRect.height() > mTextMaxHeight) {
                mTextMaxHeight = mTextBoundRect.height();
            }
        }

        if (mHeightMeasureMode == MeasureSpec.EXACTLY) {
            //
            mItemHeight = mComponentHeight / mDisplayItemCount;
        } else {
            // 计算出一个item的高度, item的宽度 = component的宽度
            mItemHeight = mTextMaxHeight + (mVerticalTextSpace << 1);
        }

        // 需要重新测量绘制，requestLayout?，按需测量
        requestLayout();
        // 刷新onDraw
        invalidate();
    }

    /**
     * 通知数据发生变化
     */
    public void notifyDataChanaged() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDataList == null || mDataList.size() == 0) {
            return;
        }
        Log.d(TAG, "onDraw");
        if (mScroller.computeScrollOffset()) {
            mScrollerOffsetY = mScroller.getCurrY();
            invalidate();
        }
        // 在滚动的过程中最多有mDisplayItemCount + 1条数据
        // 绘制背景
        canvas.drawColor(mComponentBackgroundColor);
        // 绘制辅助线
//        canvas.drawLine(0, mComponentHeight / 2, mComponentWidth, mComponentHeight / 2, mReferenceLinePaint);
        // 绘制边框
        canvas.drawRect(mComponentBoundWidth / 2, mComponentBoundWidth / 2, mComponentWidth - mComponentBoundWidth / 2, mComponentHeight - mComponentBoundWidth / 2, mComponentBoundPaint);
        // 绘制两条线
        canvas.drawLine(mComponentBoundWidth, (mComponentHeight - mItemHeight) / 2, mComponentWidth - mComponentBoundWidth, (mComponentHeight - mItemHeight) / 2, mComponentSelectedItemUpDownLinePaint);
        canvas.drawLine(mComponentBoundWidth, (mComponentHeight + mItemHeight) / 2, mComponentWidth - mComponentBoundWidth, (mComponentHeight + mItemHeight) / 2, mComponentSelectedItemUpDownLinePaint);

        // 绘制item
        int drawnSelectedIndex = -mScrollerOffsetY / mItemHeight;
        Log.d(TAG, "drawnSelectedIndex = " + drawnSelectedIndex);
        for (int index = drawnSelectedIndex - mDisplayItemCount / 2 - 1; index <= drawnSelectedIndex + mDisplayItemCount / 2 + 1; index++) {
            int realIndex = index;
            if (mIsCycle) {
                int reminder = realIndex % mDataList.size();
                if (realIndex < 0) {
                    realIndex = (reminder == 0) ? 0 : (mDataList.size() + reminder);
                } else if (realIndex >= mDataList.size()) {
                    realIndex = reminder;
                }
            } else {
                if (realIndex < 0 || realIndex > mDataList.size() - 1) {
                    continue;
                }
            }
            // 颜色计算
            int drawnItemY = (index + mDisplayItemCount / 2) * mItemHeight + mItemHeight / 2 + mScrollerOffsetY;
            int distanceY = Math.abs(getHeight() / 2 - drawnItemY);
            Log.d(TAG, "distanceY = " + distanceY);
            if (distanceY == 0) {
                mTextPaint.setColor(mSelectedTextColor);
            } else if (distanceY < mItemHeight) {
                float colorRatio = 1 - (distanceY / (float) mItemHeight);
                mTextPaint.setColor(getTextColorWithRatio(colorRatio));
            } else {
                mTextPaint.setColor(mUnSelectedTextColor);
            }
            // 绘制具体的item
            String data = mDataList.get(realIndex);
            measureText(mTextPaint, data, mTextBoundRect);
            canvas.drawText(data, mComponentWidth / 2, (index + mDisplayItemCount / 2) * mItemHeight + mItemHeight / 2 + mTextBoundRect.height() / 2 + mScrollerOffsetY, mTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDataList == null || mDataList.size() == 0) {
            return super.onTouchEvent(event);
        }

        mVelocityTracker.addMovement(event);

        // 处理滚动事件
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mLastDownY = event.getY();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }


            case MotionEvent.ACTION_MOVE: {
                float offsetY = event.getY() - mLastDownY;
                mScrollerOffsetY += offsetY;
                mLastDownY = event.getY();
                if (!mIsCycle) {
                    if (mScrollerOffsetY > 0) {
                        mScrollerOffsetY = 0;
                    } else if (mScrollerOffsetY < -mItemHeight * (mDataList.size() - 1)) {
                        mScrollerOffsetY = -mItemHeight * (mDataList.size() - 1);
                    }
                }
                invalidate();
                break;
            }


            case MotionEvent.ACTION_UP: {
                mVelocityTracker.computeCurrentVelocity(1000);
                int velocity = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocity) >= 50) {
                    mScroller.fling(0, mScrollerOffsetY, 0, velocity, 0, 0, mIsCycle ? Integer.MIN_VALUE : -mItemHeight * (mDataList.size() - 1), mIsCycle ? Integer.MAX_VALUE : 0);
                    mScroller.setFinalY(mScroller.getFinalY() + computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
                } else {
                    mScroller.startScroll(0, mScrollerOffsetY, 0, computeDistanceToEndPoint(mScrollerOffsetY % mItemHeight), 200);
                }
                invalidate();
                break;
            }
        }
        return true;
    }

    /**
     * 计算位置
     *
     * @param remainder
     * @return
     */
    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mItemHeight / 2) {
            if (mScrollerOffsetY < 0) {
                return -mItemHeight - remainder;
            } else {
                return mItemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.d(TAG, "Measure width mode = " + widthMode + ", height mode = " + heightMode);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            width = mTextMaxWidth + (mHorizontalTextSpace << 1);
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            height = (mTextMaxHeight + (mVerticalTextSpace << 1)) * mDisplayItemCount;
        }

        mWidthMeasureMode = widthMode;
        mHeightMeasureMode = heightMode;

        mComponentWidth = width;
        mComponentHeight = height;
        setMeasuredDimension(width, height);
    }
}
