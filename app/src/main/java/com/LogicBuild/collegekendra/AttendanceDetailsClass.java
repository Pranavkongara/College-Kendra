package com.LogicBuild.collegekendra;
public class AttendanceDetailsClass {
    String name, attendance;

    public AttendanceDetailsClass() {
    }

    public AttendanceDetailsClass(String name, String attendance) {
        this.name = name;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
