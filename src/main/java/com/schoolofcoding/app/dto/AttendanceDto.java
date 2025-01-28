package com.schoolofcoding.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceDto {

    private int id;

    private int batchId;
    private String batchCode;

    private String courseName;

    private String trainer;

    private int dayAttended;

    private  int batchDay;

    private boolean attendanceStatus;

    private String slot;

    private String studentName;


    public AttendanceDto(int id, String batchCode,int batchId, String courseName, String trainer, int dayAttended, boolean attendanceStatus, String slot, String studentName,int batchDay) {
        this.id = id;
        this.batchId=batchId;
        this.batchCode = batchCode;
        this.courseName = courseName;
        this.trainer = trainer;
        this.dayAttended = dayAttended;
        this.attendanceStatus = attendanceStatus;
        this.slot = slot;
        this.studentName = studentName;
        this.batchDay=batchDay;
    }

    public AttendanceDto() {
    }
}
