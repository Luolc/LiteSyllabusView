package com.luolc.syllabustest;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.luolc.litesyllabusview.entity.Course;
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

    private List<Course> getCourses() {
        List<Course> data = new ArrayList<>();
        data.add(makeCourseEntity("数据库概论", "理教211", 5, 6, 1));
        data.add(makeCourseEntity("数据库概论", "理教211", 3, 4, 4));
        data.add(makeCourseEntity("Java程序设计", "未知", 10, 11, 1));
        data.add(makeCourseEntity("现代电子电路基础及实验(一)", "二教425", 7, 8, 1));
        data.add(makeCourseEntity("现代电子电路基础及实验(一)", "二教425", 5, 6, 3));
        data.add(makeCourseEntity("函数式程序设计", "未知", 10, 12, 3));
        data.add(makeCourseEntity("中国古代政治与文化", "二教205", 10, 11, 0));
        data.add(makeCourseEntity("遥感概论", "二教525", 1, 2, 1));
        data.add(makeCourseEntity("遥感概论", "二教525", 7, 8, 4));
        data.add(makeCourseEntity("物联网技术导论", "二教313", 3, 4, 2));
        data.add(makeCourseEntity("创新工程实践", "理教308", 10, 12, 2));
        return data;
    }

    private Course makeCourseEntity(String name, String position, int startSection, int endSection, int weekday) {
        Course entity = new Course();
        entity.setName(name);
        entity.setPosition(position);
        entity.setStartSection(startSection);
        entity.setEndSection(endSection);
        entity.setWeekday(weekday);

        return entity;
    }
}
