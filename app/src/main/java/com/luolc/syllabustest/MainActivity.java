package com.luolc.syllabustest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.luolc.litesyllabusview.entity.LiteCourse;
import com.luolc.litesyllabusview.view.LiteSyllabusView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LiteSyllabusView mLiteSyllabusView;
    private ScrollView mScrollView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSyllabusView();
        mScrollView = (ScrollView) findViewById(R.id.scroll_syllabus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_screenshot:
                ScreenshotUtil.saveScreenshot(this, mScrollView);
                break;
        }
        return true;
    }

    private void setupSyllabusView() {
        mLiteSyllabusView = (LiteSyllabusView) findViewById(R.id.lite_syllabus);
        mLiteSyllabusView.setCourses(getCourses());
        mLiteSyllabusView.hideWeekendColumn(true);
        mLiteSyllabusView.setCourseNameTextSize(12);
        mLiteSyllabusView.setCoursePositionTextSize(9);
        mLiteSyllabusView.setCourseNoteTextSize(9);
        mLiteSyllabusView.setOnBlankViewClickListener(new LiteSyllabusView.OnBlankViewClickListener() {
            @Override
            public void onClick(int weekday, int section) {
                Toast.makeText(getApplicationContext(), "The NO." + (""+section) + " section on the " + (""+weekday) + " day.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int weekday, int section) {
                Toast.makeText(getApplicationContext(), "This is long click", Toast.LENGTH_SHORT).show();
            }
        });
        mLiteSyllabusView.show();
    }

    private List<LiteCourse> getCourses() {
        List<LiteCourse> data = new ArrayList<>();
        data.add(makeCourseEntity("数据库概论", "理教211", 5, 6, 1, "双周"));
        data.add(makeCourseEntity("数据库概论", "理教211", 3, 4, 4, null));
        data.add(makeCourseEntity("Java程序设计", "未知", 10, 11, 1, null));
        data.add(makeCourseEntity("现代电子电路基础及实验(一)", "二教425", 7, 8, 1, null));
        data.add(makeCourseEntity("现代电子电路基础及实验(一)", "二教425", 5, 6, 3, null));
        data.add(makeCourseEntity("函数式程序设计", "未知", 10, 12, 3, null));
        data.add(makeCourseEntity("中国古代政治与文化", "二教205", 10, 11, 0, null));
        data.add(makeCourseEntity("遥感概论", "二教525", 1, 2, 1, null));
        data.add(makeCourseEntity("遥感概论", "二教525", 7, 8, 4, "单周"));
        data.add(makeCourseEntity("物联网技术导论", "二教313", 3, 4, 2, null));
        data.add(makeCourseEntity("大学英语(四)", "文史楼304", 5, 6, 2, null));
        data.add(makeCourseEntity("地理信息系统原理", "理教203", 1, 2, 0, "单周"));
        data.add(makeCourseEntity("地理信息系统原理", "理教203", 1, 2, 3, null));
        return data;
    }

    private LiteCourse makeCourseEntity(String name, String position, int startSection, int endSection, int weekday, String note) {
        LiteCourse entity = new LiteCourse();
        entity.setName(name);
        entity.setPosition(position);
        entity.setStartSection(startSection);
        entity.setEndSection(endSection);
        entity.setWeekday(weekday);
        entity.setNote(note);

        return entity;
    }
}
