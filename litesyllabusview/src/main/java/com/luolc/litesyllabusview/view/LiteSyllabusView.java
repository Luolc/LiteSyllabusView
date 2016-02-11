package com.luolc.litesyllabusview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luolc.litesyllabusview.R;
import com.luolc.litesyllabusview.entity.CourseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiangchen on 16/2/11.
 */
public class LiteSyllabusView extends LinearLayout {

    private static final String[] WEEKDAY_TITLES = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
    private static final int HEIGHT_DEFAULT = 600;
    private static final int HEADER_HEIGHT_DEFAULT = 24;
    private static final int SIDEBAR_WIDTH_DEFAULT = 16;
    private static final int DIVIDER_WIDTH_DEFAULT = 2;
    private static final int DIVIDER_HEIGHT_DEFAULT = 2;
    private static final int WEEKDAY_NUMBER_DEFAULT = 7;
    private static final int SECTION_NUMBER_DEFAULT = 12;

    private Context mContext;

    private int mWeekdayNumber;
    private int mSectionNumber;
    private int mWidth;
    private int mHeight;
    private int mSectionWidth;
    private int mSectionHeight;
    private int mWeekdayHeaderHeight;
    private int mSectionSidebarWidth;
    private int mDividerWidth;
    private int mDividerHeight;

    private LinearLayout layoutWeekdayHeader;
    private LinearLayout layoutCourseTable;

    private List<CourseEntity> courses = new ArrayList<>();

    public LiteSyllabusView(Context context) {
        this(context, null);
    }

    public LiteSyllabusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LiteSyllabusView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        mContext = context;
        mWidth = getScreenWidth();
        mHeight = dip2px(HEIGHT_DEFAULT);
        mDividerWidth = DIVIDER_WIDTH_DEFAULT;
        mDividerHeight = DIVIDER_HEIGHT_DEFAULT;
        mWeekdayNumber = WEEKDAY_NUMBER_DEFAULT;
        mSectionNumber = SECTION_NUMBER_DEFAULT;
        mWeekdayHeaderHeight = dip2px(HEADER_HEIGHT_DEFAULT);
        mSectionSidebarWidth = dip2px(SIDEBAR_WIDTH_DEFAULT);
        mSectionWidth = (mWidth - mSectionSidebarWidth - mWeekdayNumber * mDividerWidth) / mWeekdayNumber;
        mSectionHeight = (mHeight - mWeekdayHeaderHeight - mSectionNumber * mDividerHeight) / mSectionNumber;

        setOrientation(VERTICAL);
    }

    public void setCourses() {
        // TODO: 16/2/11 add courses
        init();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init() {
        layoutCourseTable = new LinearLayout(mContext);
        layoutCourseTable.setOrientation(HORIZONTAL);

        setupWeekdayHeader();

        addView(layoutWeekdayHeader);
    }

    private void setupWeekdayHeader() {
        layoutWeekdayHeader = new LinearLayout(mContext);
        layoutWeekdayHeader.setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mWidth, mWeekdayHeaderHeight);

        TextView tvEmptyCellOnTopAndLeft = new TextView(mContext);
        tvEmptyCellOnTopAndLeft.setWidth(mSectionSidebarWidth);
        tvEmptyCellOnTopAndLeft.setHeight(mWeekdayHeaderHeight);
        tvEmptyCellOnTopAndLeft.setText("\\");
        tvEmptyCellOnTopAndLeft.setGravity(Gravity.CENTER);
        layoutWeekdayHeader.addView(tvEmptyCellOnTopAndLeft);

        for (int i = 0; i < mWeekdayNumber; ++i) {
            layoutWeekdayHeader.addView(getVerticalDividerView());
            TextView tvWeekdayTitle = new TextView(mContext);
            tvWeekdayTitle.setWidth(mSectionWidth);
            tvWeekdayTitle.setHeight(mWeekdayHeaderHeight);
            tvWeekdayTitle.setText(WEEKDAY_TITLES[i]);
            tvWeekdayTitle.setGravity(Gravity.CENTER);
            layoutWeekdayHeader.addView(tvWeekdayTitle);
        }
    }

    private View getVerticalDividerView() {
        View divider = new View(mContext);
        divider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.divider));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mDividerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        divider.setLayoutParams(params);
        return divider;
    }

    private View getHorizontalDividerView() {
        View divider = new View(mContext);
        divider.setBackgroundColor(ContextCompat.getColor(mContext, R.color.divider));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDividerHeight);
        divider.setLayoutParams(params);
        return divider;
    }

    private int getScreenWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
