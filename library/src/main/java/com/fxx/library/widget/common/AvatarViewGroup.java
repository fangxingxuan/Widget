package com.fxx.library.widget.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fxx.library.widget.R;
import com.fxx.library.widget.utils.FXWidgetUtils;

import java.util.List;

/**
 * Created by binlly on 2017/10/13.
 * 相互覆盖的头像组布局
 */

public class AvatarViewGroup<T, V extends ImageView> extends ViewGroup {

    private boolean isInvert = false;//是否逆向展示
    private int mOverlap = (int) FXWidgetUtils.dp2px(6, getContext());
    private Orientation orientation = Orientation.LOR;//覆盖方向
    private List<T> mDatas;
    private OnDisplayListener<T> mDisplayListener;
    private OnGenerateViewListener<V> mGenerateViewListener;
    private int childWidth;
    private int childHeight;

    private int moreDrawableResId;

    private boolean showMore;//显示更多

    private static final String TAG = AvatarViewGroup.class.getSimpleName();

    private SparseArray<ImageView> mSparseArray = new SparseArray<>();

    public AvatarViewGroup(Context context) {
        this(context, null);
    }

    public AvatarViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        moreDrawableResId = R.drawable.fw_group_avatar_more;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = sizeHeight;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            if (i > 0)
                width += childWidth - mOverlap;
            else
                width += childWidth;
        }
        height = childHeight;

        setMeasuredDimension(//
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
    }

    //右边覆盖左边的View
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mDatas == null || mDatas.size() == 0)
            return;

        int childCount = getChildCount();
        Log.d(TAG, "childCount=" + childCount + " mDatas.size=" + mDatas.size());
        int top = getPaddingTop();
        int left = getPaddingLeft() + (childCount - 1) * (childWidth - mOverlap);

        if (orientation == Orientation.LOR) {
            ImageView child = null;
            if (showMore) {
                child = (ImageView) getChildAt(0);
                child.setImageResource(moreDrawableResId);

                int lc = left;
                int tc = top;
                int rc = lc + childWidth;
                int bc = tc + childHeight;

                child.layout(lc, tc, rc, bc);
                left -= childWidth - mOverlap;
            }
            for (int i = 0; i < mDatas.size(); i++) {
                child = (ImageView) getChildAt(showMore ? i + 1 : i);
                if (mDisplayListener != null) {
                    T lorData;
                    if (isInvert) {
                        lorData = mDatas.get(i);
                    } else {
                        lorData = mDatas.get(mDatas.size() - 1 - i);
                    }
                    mDisplayListener.onDisplay(getContext(), lorData, child);
                }

                int lc = left;
                int tc = top;
                int rc = lc + childWidth;
                int bc = tc + childHeight;

                // logger.d("position=" + i + " lc=" + lc + " tc=" + tc + " rc=" + rc + " bc=" + bc);

                child.layout(lc, tc, rc, bc);
                left -= childWidth - mOverlap;
            }
        } else {
            ImageView child = null;
            if (showMore) {
                child = (ImageView) getChildAt(childCount - 1);
                child.setBackgroundResource(moreDrawableResId);

                int lc = left;
                int tc = top;
                int rc = lc + childWidth;
                int bc = tc + childHeight;

                child.layout(lc, tc, rc, bc);
                left -= childWidth - mOverlap;
            }
            for (int i = mDatas.size() - 1; i >= 0; i--) {
                child = (ImageView) getChildAt(i);
                if (mDisplayListener != null) {
                    T rolData;
                    if (isInvert) {
                        rolData = mDatas.get(mDatas.size() - 1 - i);
                    } else {
                        rolData = mDatas.get(i);
                    }
                    mDisplayListener.onDisplay(getContext(), rolData, child);
                }

                int lc = left;
                int tc = top;
                int rc = lc + childWidth;
                int bc = tc + childHeight;

                // logger.d("position=" + i + " lc=" + lc + " tc=" + tc + " rc=" + rc + " bc=" + bc);

                child.layout(lc, tc, rc, bc);
                left -= childWidth - mOverlap;
            }
        }
    }

    // //左边覆盖右边的View
    // @Override
    // protected void onLayout(boolean changed, int l, int t, int r, int b) {
    //     if (mDatas == null || mDatas.size() == 0)
    //         return;
    //
    //     int childCount = getChildCount();
    //     int top = getPaddingTop();
    //     int left = getPaddingLeft() + (childCount - 1) * (childWidth - mOverlap);
    //     for (int i = childCount - 1; i >= 0; i--) {
    //         ImageView child = (ImageView) getChildAt(i);
    //         if (mDisplayListener != null) {
    //             if (showMore && mAllSize > max && i == 0) {//最后一个头像的显示，由于是从左向右，所以是最后一个位置
    //                 mDisplayListener.onDisplay(getContext(), null, child);
    //             } else {
    //                 mDisplayListener.onDisplay(getContext(), mDatas.get(childCount - 1 - i), child);
    //             }
    //         }
    //
    //         int lc = left;
    //         int tc = top;
    //         int rc = lc + childWidth;
    //         int bc = tc + childHeight;
    //
    //         // logger.d("position=" + i + " lc=" + lc + " tc=" + tc + " rc=" + rc + " bc=" + bc);
    //
    //         child.layout(lc, tc, rc, bc);
    //         left -= childWidth - mOverlap;
    //     }
    // }

    //相互覆盖重叠部分的size
    public void setOverlap(int overlap) {
        mOverlap = overlap;
    }

    public void setMoreDrawableResId(int moreDrawableResId) {
        this.moreDrawableResId = moreDrawableResId;
        requestLayout();
    }

    public void setOverOrientation(Orientation orientation) {
        this.orientation = orientation;
        requestLayout();
    }

    public void setChildSize(int w, int h) {
        childWidth = w;
        childHeight = h;
    }

    public void setDatas(List<T> datas) {
        if (mDatas != null)
            mDatas.clear();

        if (datas == null || datas.isEmpty()) {
            Log.e(TAG, "mDatas is empty!");
            mSparseArray.clear();
            removeAllViews();
            return;
        }

        if (mGenerateViewListener == null) {
            throw new RuntimeException("OnGenerateViewListener必须被设置！");
        }

        //管理缓存View
        int realChildCount = getRealChildCount(datas.size());
        if (getChildCount() > realChildCount) {
            for (int i = realChildCount; i < getChildCount(); i++) {
                mSparseArray.remove(i);
            }
            removeViews(realChildCount, getChildCount() - realChildCount);
        }

        for (int i = 0; i < realChildCount; i++) {
            ImageView view = getCachedView(i);
            if (view == null) {
                view = mGenerateViewListener.getView(i);
                if (view == null) {
                    throw new RuntimeException("生成的View不能为空！");
                }
                mSparseArray.put(i, view);
                addView(view, i, generateDefaultLayoutParams());
            }
        }
        mDatas = datas;
        requestLayout();
    }

    public void setOnDisplayListener(OnDisplayListener<T> listener) {
        mDisplayListener = listener;
    }

    public void setOnGenerateViewListener(OnGenerateViewListener<V> listener) {
        mGenerateViewListener = listener;
    }

    public interface OnDisplayListener<T> {
        void onDisplay(Context context, T data, ImageView imageView);
    }

    public interface OnGenerateViewListener<V> {
        V getView(int at);
    }

    private ImageView getCachedView(int at) {
        return mSparseArray.get(at);
    }

    /**
     * 只有当数据集size（mAllSize）超过了max的是时候才生效
     *
     * @param showMore
     */
    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
        if (mDatas == null || mDatas.isEmpty())
            return;

        setDatas(mDatas);
    }

    public int getRealChildCount(int size) {
        return showMore ? size + 1 : size;
    }

    public void setInvert(boolean invert) {
        isInvert = invert;
    }

    public enum Orientation {
        LOR, ROL
    }
}
