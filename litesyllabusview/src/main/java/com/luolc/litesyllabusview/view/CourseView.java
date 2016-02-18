package com.luolc.litesyllabusview.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luolc.litesyllabusview.R;
import com.luolc.litesyllabusview.entity.Course;

/**
 * Created by Liangchen Luo on 16/2/12.
 * @author Liangchen Luo
 */
public class CourseView extends LinearLayout {

    private static final float DIVIDER_HEIGHT_DEFAULT = 0.5f;
    private static final int NAME_TEXT_SIZE = 13;
    private static final int POSITION_TEXT_SIZE = 10;
    private static final int PADDING = 2;

    private Context mContext;

    private int mSectionWidth;
    private int mSectionHeight;
    private int mDividerHeight;

    private TextView tvName;
    private TextView tvPosition;

    private Course mData;

    public CourseView(Context context) {
        super(context);
        mContext = context;
        mDividerHeight = dip2px(DIVIDER_HEIGHT_DEFAULT);

        setOrientation(VERTICAL);
        setPadding(dip2px(PADDING), dip2px(PADDING), dip2px(PADDING), dip2px(PADDING));
    }

    public void setCourseData(Course data) {
        mData = data;
    }

    public void setSectionWidth(int width) {
        mSectionWidth = width;
    }

    public void setSectionHeight(int height) {
        mSectionHeight = height;
    }

    public void setDividerHeight(int height) {
        mDividerHeight = height;
    }

    public CourseView getView() {
        tvName = new TextView(mContext);
        tvName.setWidth(mSectionWidth);
        tvName.setText(mData.getName());
        tvName.setTextSize(NAME_TEXT_SIZE);
        tvName.setTextColor(ContextCompat.getColor(mContext, R.color.textCourse));
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        tvPosition = new TextView(mContext);
        tvPosition.setWidth(mSectionWidth);
        tvPosition.setText("@" + mData.getPosition());
        tvPosition.setTextSize(POSITION_TEXT_SIZE);
        tvPosition.setTextColor(ContextCompat.getColor(mContext, R.color.textCourse));

        addView(tvName);
        addView(tvPosition);
        int durationInSection = mData.getEndSection() - mData.getStartSection() + 1;
        // TODO: 16/2/13 duration判断边界
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSectionWidth,
                durationInSection * mSectionHeight + (durationInSection - 1) * mDividerHeight);
        setLayoutParams(params);

        return this;
    }

    public CourseView getBlankSectionView() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSectionWidth, mSectionHeight);
        setLayoutParams(params);

        return this;
    }

    public static class Builder {
        private static final int NULL_DIVIDER_HEIGHT = -1;

        private Context mContext;
        private int mSectionWidth;
        private int mSectionHeight;
        private int mDividerHeight;
        private int mBackgroundColor;
        private String mName;
        private String mPosition;
        private int mStartSection;
        private int mEndSection;
        private int mWeekday;
        private OnClickListener mOnClickListener;
        private OnLongClickListener mOnLongClickListener;

        public Builder(Context context) {
            mContext = context;
            mDividerHeight = NULL_DIVIDER_HEIGHT;
        }

        public Builder setSectionWidth(int width) {
            mSectionWidth = width;
            return this;
        }

        public Builder setSectionHeight(int height) {
            mSectionHeight = height;
            return this;
        }

        public Builder setDividerHeight(int height) {
            mDividerHeight = height;
            return this;
        }

        public Builder setBackgroundColor(int color) {
            mBackgroundColor = color;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setPosition(String position) {
            mPosition = position;
            return this;
        }

        public Builder setStartSection(int startSection) {
            mStartSection = startSection;
            return this;
        }

        public Builder setEndSection(int endSection) {
            mEndSection = endSection;
            return this;
        }

        public Builder setSection(int section) {
            mStartSection = section;
            mEndSection = section;
            return this;
        }

        public Builder setWeekday(int weekday) {
            mWeekday = weekday;
            return this;
        }

        public Builder setOnClickListener(OnClickListener listener) {
            mOnClickListener = listener;
            return this;
        }

        public Builder setOnLongClickListener(OnLongClickListener listener) {
            mOnLongClickListener = listener;
            return this;
        }

        public CourseView create() {
            CourseView view = new CourseView(mContext);

            Course data = new Course();
            data.setName(mName);
            data.setPosition(mPosition);
            data.setStartSection(mStartSection);
            data.setEndSection(mEndSection);
            data.setWeekday(mWeekday);
            data.setIsEmpty(false);
            view.setCourseData(data);
            view.setSectionWidth(mSectionWidth);
            view.setSectionHeight(mSectionHeight);
            if (mDividerHeight != NULL_DIVIDER_HEIGHT) view.setDividerHeight(mDividerHeight);
            view.setBackgroundColor(mBackgroundColor);
            view.setOnClickListener(mOnClickListener);
            view.setOnLongClickListener(mOnLongClickListener);

            return view.getView();
        }

        public CourseView createBlankSection() {
            CourseView view = new CourseView(mContext);

            Course data = new Course();
            data.setStartSection(mStartSection);
            data.setEndSection(mEndSection);
            data.setWeekday(mWeekday);
            data.setIsEmpty(true);
            view.setCourseData(data);
            view.setSectionWidth(mSectionWidth);
            view.setSectionHeight(mSectionHeight);
            view.setOnClickListener(mOnClickListener);
            view.setOnLongClickListener(mOnLongClickListener);

            return view.getBlankSectionView();
        }
    }

    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
