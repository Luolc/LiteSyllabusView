package com.luolc.litesyllabusview.entity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liangchen Luo on 16/2/11.
 * @author Liangchen Luo
 */
public class Course {
    private int courseId;
    private String name;
    private String position;
    private int startSection;
    private int EndSection;
    private int weekday;
    private boolean isEmpty;
    private int backgroundColor;
    private String note;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
    }

    public int getEndSection() {
        return EndSection;
    }

    public void setEndSection(int endSection) {
        EndSection = endSection;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
