package com.luolc.litesyllabusview.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luolc.litesyllabusview.R;
import com.luolc.litesyllabusview.entity.LiteCourse;

/**
 * Created by Liangchen Luo on 16/2/12.
 * @author Liangchen Luo
 */
public class CourseView extends LinearLayout {

    public static final float DIVIDER_HEIGHT_DEFAULT = 0.5f;
    public static final int NAME_TEXT_SIZE_DEFAULT = 12;
    public static final int POSITION_TEXT_SIZE_DEFAULT = 9;
    public static final int NOTE_TEXT_SIZE_DEFAULT = 9;
    public static final int PADDING = 2;
    public static final int INACTIVE_ALPHA_DEFAULT = (int) (0xFF * 0.20);

    protected Context mContext;

    protected int mSectionWidth;
    protected int mSectionHeight;
    protected int mDividerHeight;
    protected int mNameTextSize;
    protected int mPositionTextSize;
    protected int mNoteTextSize;

    protected TextView tvName;
    protected TextView tvPosition;
    protected TextView tvNote;

    protected LiteCourse mData;

    public CourseView(Context context) {
        super(context);
        mContext = context;
        mDividerHeight = dip2px(DIVIDER_HEIGHT_DEFAULT);
        mNameTextSize = NAME_TEXT_SIZE_DEFAULT;
        mPositionTextSize = POSITION_TEXT_SIZE_DEFAULT;
        mNoteTextSize = NOTE_TEXT_SIZE_DEFAULT;

        setOrientation(VERTICAL);
        setPadding(dip2px(PADDING), dip2px(PADDING), dip2px(PADDING), dip2px(PADDING));
    }

    public void setCourseData(LiteCourse data) {
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

    public void setNameTextSize(int textSize) {
        mNameTextSize = textSize;
    }

    public void setPositionTextSize(int textSize) {
        mPositionTextSize = textSize;
    }

    public void setNoteTextSize(int textSize) {
        mNoteTextSize = textSize;
    }

    public CourseView getView() {
        setName();
        setPosition();
        if (mData.getNote() != null && !"".equals(mData.getNote())) setNote();
        int durationInSection = mData.getEndSection() - mData.getStartSection() + 1;
        // TODO: 16/2/13 duration判断边界
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSectionWidth,
                durationInSection * mSectionHeight + (durationInSection - 1) * mDividerHeight);
        setLayoutParams(params);

        return this;
    }

    private void setName() {
        tvName = new TextView(mContext);
        tvName.setWidth(mSectionWidth);
        tvName.setText(mData.getName());
        tvName.setTextSize(mNameTextSize);
        tvName.setTextColor(ContextCompat.getColor(mContext, R.color.textCourse));
        tvName.setTypeface(Typeface.DEFAULT_BOLD);
        addView(tvName);
    }

    private void setPosition() {
        tvPosition = new TextView(mContext);
        tvPosition.setWidth(mSectionWidth);
        tvPosition.setText("@" + mData.getPosition());
        tvPosition.setTextSize(mPositionTextSize);
        tvPosition.setTextColor(ContextCompat.getColor(mContext, R.color.textCourse));
        addView(tvPosition);
    }

    private void setNote() {
        tvNote = new TextView(mContext);
        tvNote.setWidth(mSectionWidth);
        tvNote.setText("[" + mData.getNote() + "]");
        tvNote.setTextSize(mNoteTextSize);
        tvNote.setTextColor(ContextCompat.getColor(mContext, R.color.textCourse));
        addView(tvNote);
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
        private String mNote;
        private int mNameTextSize;
        private int mPositionTextSize;
        private int mNoteTextSize;
        private int mStartSection;
        private int mEndSection;
        private int mWeekday;
        private boolean mActive;
        private OnClickListener mOnClickListener;
        private OnLongClickListener mOnLongClickListener;

        public Builder(Context context) {
            mContext = context;
            mDividerHeight = NULL_DIVIDER_HEIGHT;
            mNameTextSize = NAME_TEXT_SIZE_DEFAULT;
            mPositionTextSize = POSITION_TEXT_SIZE_DEFAULT;
            mNoteTextSize = NOTE_TEXT_SIZE_DEFAULT;
            mActive = true;
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

        public Builder setNote(String note) {
            mNote = note;
            return this;
        }

        public Builder setNameTextSize(int textSize) {
            mNameTextSize = textSize;
            return this;
        }

        public Builder setPositionTextSize(int textSize) {
            mPositionTextSize = textSize;
            return this;
        }

        public Builder setNoteTextSize(int textSize) {
            mNoteTextSize = textSize;
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

        public Builder setActive(boolean active) {
            mActive = active;
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

            LiteCourse data = new LiteCourse();
            data.setName(mName);
            data.setPosition(mPosition);
            data.setStartSection(mStartSection);
            data.setEndSection(mEndSection);
            data.setWeekday(mWeekday);
            data.setIsEmpty(false);
            data.setNote(mNote);
            view.setCourseData(data);
            view.setSectionWidth(mSectionWidth);
            view.setSectionHeight(mSectionHeight);
            if (mDividerHeight != NULL_DIVIDER_HEIGHT) view.setDividerHeight(mDividerHeight);
            view.setBackgroundColor(mBackgroundColor);
            view.setOnClickListener(mOnClickListener);
            view.setOnLongClickListener(mOnLongClickListener);
            view.setNameTextSize(mNameTextSize);
            view.setPositionTextSize(mPositionTextSize);
            view.setNoteTextSize(mNoteTextSize);
            if (!mActive) view.getBackground().setAlpha(INACTIVE_ALPHA_DEFAULT);
            return view.getView();
        }

        public CourseView createBlankSection() {
            CourseView view = new CourseView(mContext);

            LiteCourse data = new LiteCourse();
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
