package com.luolc.litesyllabusview.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiangchen on 16/2/11.
 */
public class CourseEntity {
    private int cid;
    private String code;
    private String name;
    private String department;
    private double credit;
    private String teacher;
    private int classNumber;
    private int startWeek;
    private int endWeek;
    private ArrayList<CourseTimeEntity> time;
    private String position;
    private String examTime;
    private String examPosition;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public ArrayList<CourseTimeEntity> getTime() {
        return time;
    }

    public void setTime(ArrayList<CourseTimeEntity> time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamPosition() {
        return examPosition;
    }

    public void setExamPosition(String examPosition) {
        this.examPosition = examPosition;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    private int source;

    public class CourseTimeEntity {
        private int weekday;
        private int startSection;
        private int endSection;
        private int notes;

        public int getWeekday() {
            return weekday;
        }

        public void setWeekday(int weekday) {
            this.weekday = weekday;
        }

        public int getStartSection() {
            return startSection;
        }

        public void setStartSection(int startSection) {
            this.startSection = startSection;
        }

        public int getEndSection() {
            return endSection;
        }

        public void setEndSection(int endSection) {
            this.endSection = endSection;
        }

        public int getNotes() {
            return notes;
        }

        public void setNotes(int notes) {
            this.notes = notes;
        }
    }
}
