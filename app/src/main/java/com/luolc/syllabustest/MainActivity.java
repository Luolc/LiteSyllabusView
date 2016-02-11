package com.luolc.syllabustest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luolc.litesyllabusview.view.LiteSyllabusView;


public class MainActivity extends AppCompatActivity {

    private LiteSyllabusView mLiteSyllabusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLiteSyllabusView = (LiteSyllabusView) findViewById(R.id.lite_syllabus);
        mLiteSyllabusView.setCourses();
    }
}
