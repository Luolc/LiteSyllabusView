package com.luolc.litesyllabusview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luolc.litesyllabusview.R;
import com.luolc.litesyllabusview.entity.Course;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liangchen Luo on 16/2/11, with awful network status.
 * @author Liangchen Luo
 */
public class LiteSyllabusView extends LinearLayout {

    private static final String[] WEEKDAY_TITLES = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
    private static final int[] COURSE_BACKGROUND_COLOR_RES_DEFAULT = {
            R.color.bgCourse0,
            R.color.bgCourse1,
            R.color.bgCourse2,
            R.color.bgCourse3,
            R.color.bgCourse4,
            R.color.bgCourse5,
            R.color.bgCourse6,
            R.color.bgCourse7,
            R.color.bgCourse8,
            R.color.bgCourse9,
            R.color.bgCourse10,
            R.color.bgCourse11,
            R.color.bgCourse12,
            R.color.bgCourse13,
            R.color.bgCourse14
    };
    private static final int HEIGHT_DEFAULT = 640;
    private static final int HEADER_HEIGHT_DEFAULT = 24;
    private static final int SIDEBAR_WIDTH_DEFAULT = 16;
    private static final float DIVIDER_WIDTH_DEFAULT = 0.5f;
    private static final float DIVIDER_HEIGHT_DEFAULT = 0.5f;
    private static final int WEEKDAY_NUMBER_DEFAULT = 7;
    private static final int WEEKDAY_NUMBER_WITHOUT_WEEKEND = 5;
    private static final int SECTION_NUMBER_DEFAULT = 12;
    private static final int WEEKDAY_TITLE_TEXT_SIZE = 14;
    private static final int SECTION_SIDEBAR_TEXT_SIZE = 14;

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
    private Typeface mTitleTypeface;

    private LinearLayout layoutWeekdayHeader;
    private LinearLayout layoutSectionSidebar;
    private LinearLayout layoutCourseTable;

    private OnBlankViewClickListener mOnBlankViewClickListener;

    private List<Course> mCourses = new ArrayList<>();
    private int[] mCourseBackgroundColorPalette;

    public interface OnBlankViewClickListener {
        void onClick(int weekday, int section);
        void onLongClick(int weekday, int section);
    }

    public LiteSyllabusView(Context context) {
        this(context, null);
    }

    public LiteSyllabusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LiteSyllabusView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        mContext = context;
        mWeekdayNumber = WEEKDAY_NUMBER_DEFAULT;
        mSectionNumber = SECTION_NUMBER_DEFAULT;
        mWidth = getScreenWidth();
        mHeight = dip2px(HEIGHT_DEFAULT);
        mDividerWidth = dip2px(DIVIDER_WIDTH_DEFAULT);
        mDividerHeight = dip2px(DIVIDER_HEIGHT_DEFAULT);
        mWeekdayHeaderHeight = dip2px(HEADER_HEIGHT_DEFAULT);
        mSectionSidebarWidth = dip2px(SIDEBAR_WIDTH_DEFAULT);
        mTitleTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/ChalkboardSE.ttc");
        mOnBlankViewClickListener = new OnBlankViewClickListener() {
            @Override
            public void onClick(int weekday, int section) {}

            @Override
            public void onLongClick(int weekday, int section) {}
        };

        setDefaultColorPalette();
        setOrientation(VERTICAL);
    }

    public void show() {
        calculateSectionSize();
        generateCourseBackground();
        setupView();
        invalidate();
    }

    public void setCourses(List<Course> data) {
        mCourses.clear();
        if (data == null) return;
        mCourses.addAll(data);
    }

    public void addCourse(Course course) {
        if (course == null) return;
        mCourses.add(course);
    }

    public void removeCourseById(int id) {
        for (Course course: mCourses) {
            if (course.getCourseId() == id) mCourses.remove(course);
        }
    }

    public void setSectionNumber(int sectionNumber) throws IllegalArgumentException {
        if (sectionNumber <= 0) throw new IllegalArgumentException("Section number must be positive");
        mSectionNumber = sectionNumber;
    }

    public void setCourseBackgroundColors(int[] colors) {
        mCourseBackgroundColorPalette = colors;
    }

    public void setOnBlankViewClickListener(OnBlankViewClickListener listener) {
        mOnBlankViewClickListener = listener;
    }

    public void hideWeekendColumn(boolean what) {
        if (what) {
            mWeekdayNumber = WEEKDAY_NUMBER_WITHOUT_WEEKEND;
        } else {
            mWeekdayNumber = WEEKDAY_NUMBER_DEFAULT;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void setDefaultColorPalette() {
        int size = COURSE_BACKGROUND_COLOR_RES_DEFAULT.length;
        mCourseBackgroundColorPalette = new int[size];
        for (int i = 0; i < size; ++i) {
            int color = ContextCompat.getColor(mContext, COURSE_BACKGROUND_COLOR_RES_DEFAULT[i]);
            mCourseBackgroundColorPalette[i] = color;
        }
    }

    private void calculateSectionSize() {
        mSectionWidth = (mWidth - mSectionSidebarWidth - mWeekdayNumber * mDividerWidth) / mWeekdayNumber;
        mSectionHeight = (mHeight - mWeekdayHeaderHeight - mSectionNumber * mDividerHeight) / mSectionNumber;
    }

    private void generateCourseBackground() {
        int currentColorIndex = 0;
        int paletteSize = mCourseBackgroundColorPalette.length;
        Map<String, Integer> nameColorMap = new HashMap<>();
        if (mCourses == null) mCourses = new ArrayList<>();
        for (Course course: mCourses) {
            if (nameColorMap.containsKey(course.getName())) {
                course.setBackgroundColor(nameColorMap.get(course.getName()));
            }
            else {
                int color = mCourseBackgroundColorPalette[currentColorIndex % paletteSize];
                nameColorMap.put(course.getName(), color);
                course.setBackgroundColor(color);
                currentColorIndex++;
            }
        }
    }

    private void setupView() {
        removeAllViews();
        setupWeekdayHeader();
        setupSectionSidebar();
        setupCourseTable();

        LinearLayout layoutBottom = new LinearLayout(mContext);
        layoutBottom.setOrientation(HORIZONTAL);
        layoutBottom.addView(layoutSectionSidebar);
        layoutBottom.addView(layoutCourseTable);

        addView(layoutWeekdayHeader);
        addView(layoutBottom);
    }

    private void setupWeekdayHeader() {
        layoutWeekdayHeader = new LinearLayout(mContext);
        layoutWeekdayHeader.setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mWeekdayHeaderHeight);
        layoutWeekdayHeader.setLayoutParams(params);

        TextView tvEmptyCellOnTopAndLeft = new TextView(mContext);
        tvEmptyCellOnTopAndLeft.setWidth(mSectionSidebarWidth);
        tvEmptyCellOnTopAndLeft.setHeight(mWeekdayHeaderHeight);
        tvEmptyCellOnTopAndLeft.setText("\\");
        tvEmptyCellOnTopAndLeft.setTextSize(WEEKDAY_TITLE_TEXT_SIZE);
        tvEmptyCellOnTopAndLeft.setTextColor(ContextCompat.getColor(mContext, R.color.textTitle));
        tvEmptyCellOnTopAndLeft.setGravity(Gravity.CENTER);
        tvEmptyCellOnTopAndLeft.setTypeface(mTitleTypeface);
        layoutWeekdayHeader.addView(tvEmptyCellOnTopAndLeft);

        for (int i = 0; i < mWeekdayNumber; ++i) {
            layoutWeekdayHeader.addView(getVerticalDividerView());
            TextView tvWeekdayTitle = new TextView(mContext);
            tvWeekdayTitle.setWidth(mSectionWidth);
            tvWeekdayTitle.setHeight(mWeekdayHeaderHeight);
            tvWeekdayTitle.setText(WEEKDAY_TITLES[i]);
            tvWeekdayTitle.setTextSize(WEEKDAY_TITLE_TEXT_SIZE);
            tvWeekdayTitle.setTextColor(ContextCompat.getColor(mContext, R.color.textTitle));
            tvWeekdayTitle.setGravity(Gravity.CENTER);
            tvWeekdayTitle.setTypeface(mTitleTypeface);
            layoutWeekdayHeader.addView(tvWeekdayTitle);
        }

        layoutWeekdayHeader.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bgWeekdayHeader));
    }

    private void setupSectionSidebar() {
        layoutSectionSidebar = new LinearLayout(mContext);
        layoutSectionSidebar.setOrientation(VERTICAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSectionSidebarWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutSectionSidebar.setLayoutParams(params);

        for (int i = 1; i <= mSectionNumber; ++i) {
            layoutSectionSidebar.addView(getHorizontalDividerView());
            TextView tvSectionNumberTitle = new TextView(mContext);
            tvSectionNumberTitle.setWidth(mSectionSidebarWidth);
            tvSectionNumberTitle.setHeight(mSectionHeight);
            tvSectionNumberTitle.setText("" + i);
            tvSectionNumberTitle.setTextSize(SECTION_SIDEBAR_TEXT_SIZE);
            tvSectionNumberTitle.setTextColor(ContextCompat.getColor(mContext, R.color.textTitle));
            tvSectionNumberTitle.setGravity(Gravity.CENTER);
            tvSectionNumberTitle.setTypeface(mTitleTypeface);
            layoutSectionSidebar.addView(tvSectionNumberTitle);
        }

        layoutSectionSidebar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bgSectionSidbar));
    }

    private void setupCourseTable() {
        layoutCourseTable = new LinearLayout(mContext);
        layoutCourseTable.setOrientation(HORIZONTAL);

        for (int i = 0; i < mWeekdayNumber; ++i) {
            layoutCourseTable.addView(getVerticalDividerView());
            layoutCourseTable.addView(getCourseViewColumn(i));
        }
    }

    private LinearLayout getCourseViewColumn(final int weekday) throws InvalidParameterException {
        LinearLayout layoutColumn = new LinearLayout(mContext);
        layoutColumn.setOrientation(VERTICAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mSectionWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutColumn.setLayoutParams(params);

        List<Course> coursesInCurrentDay = new ArrayList<>();
        if (mCourses == null) mCourses = new ArrayList<>();
        for (Course course: mCourses) {
            if (course.getWeekday() == weekday) {
                coursesInCurrentDay.add(course);
            }
        }
        Collections.sort(coursesInCurrentDay, new Comparator<Course>() {
            @Override
            public int compare(Course lhs, Course rhs) {
                return Integer.valueOf(lhs.getStartSection()).compareTo(rhs.getStartSection());
            }
        });
        for (int i = 1; i < coursesInCurrentDay.size(); ++i) {
            if (coursesInCurrentDay.get(i).getStartSection() <= coursesInCurrentDay.get(i-1).getEndSection()) {
                throw new InvalidParameterException("There are duration conflicts among courses");
            }
        }
        for (int i = 0; i < coursesInCurrentDay.size(); ++i) {
            int blankSectionStartPosition;
            Course course = coursesInCurrentDay.get(i);
            if (i == 0) {
                blankSectionStartPosition = 1;
            } else {
                blankSectionStartPosition = coursesInCurrentDay.get(i-1).getEndSection() + 1;
            }
            // 添加每节课前空课时View
            for (int j = blankSectionStartPosition; j < course.getStartSection(); ++j) {
                layoutColumn.addView(getHorizontalDividerView());
                final int section = j;
                CourseView blankCourseView = new CourseView.Builder(mContext)
                        .setSectionWidth(mSectionWidth)
                        .setSectionHeight(mSectionHeight)
                        .setSection(section)
                        .setWeekday(weekday)
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnBlankViewClickListener.onClick(weekday, section);
                            }
                        })
                        .setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                mOnBlankViewClickListener.onLongClick(weekday, section);
                                return true;
                            }
                        })
                        .createBlankSection();
                layoutColumn.addView(blankCourseView);
            }
            // 添加position为i的课的View
            layoutColumn.addView(getHorizontalDividerView());
            CourseView courseView = new CourseView.Builder(mContext)
                    .setSectionWidth(mSectionWidth)
                    .setSectionHeight(mSectionHeight)
                    .setName(course.getName())
                    .setPosition(course.getPosition())
                    .setStartSection(course.getStartSection())
                    .setEndSection(course.getEndSection())
                    .setWeekday(weekday)
                    .setBackgroundColor(course.getBackgroundColor())
                    .setOnClickListener(course.getOnClickListener())
                    .setOnLongClickListener(course.getOnLongClickListener())
                    .create();
            layoutColumn.addView(courseView);
            // 如果是当天最后一节课，添加末尾的空课时View
            if (i == coursesInCurrentDay.size() - 1) {
                blankSectionStartPosition = course.getEndSection() + 1;
                for (int j = blankSectionStartPosition; j <= mSectionNumber; ++j) {
                    layoutColumn.addView(getHorizontalDividerView());
                    final int section = j;
                    CourseView blankCourseView = new CourseView.Builder(mContext)
                            .setSectionWidth(mSectionWidth)
                            .setSectionHeight(mSectionHeight)
                            .setSection(section)
                            .setWeekday(weekday)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOnBlankViewClickListener.onClick(weekday, section);
                                }
                            })
                            .setOnLongClickListener(new OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    mOnBlankViewClickListener.onLongClick(weekday, section);
                                    return true;
                                }
                            })
                            .createBlankSection();
                    layoutColumn.addView(blankCourseView);
                }
            }
        }
        if (coursesInCurrentDay.isEmpty()) {
            for (int i = 1; i <= mSectionNumber; ++i) {
                layoutColumn.addView(getHorizontalDividerView());
                final int section = i;
                CourseView blankCourseView = new CourseView.Builder(mContext)
                        .setSectionWidth(mSectionWidth)
                        .setSectionHeight(mSectionHeight)
                        .setSection(section)
                        .setWeekday(weekday)
                        .setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnBlankViewClickListener.onClick(weekday, section);
                            }
                        })
                        .setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                mOnBlankViewClickListener.onLongClick(weekday, section);
                                return true;
                            }
                        })
                        .createBlankSection();
                layoutColumn.addView(blankCourseView);
            }
        }
        return layoutColumn;
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
