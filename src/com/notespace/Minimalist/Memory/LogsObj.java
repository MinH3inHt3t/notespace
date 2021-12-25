package com.notespace.Minimalist.Memory;

import com.notespace.Minimalist.Logs.REASON;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LogsObj implements Serializable {

    private String id = UUID.randomUUID().toString();
    private String fileName;
    private String folderName;
    private REASON reason;
    private long time = System.currentTimeMillis();
    private String formattedTime;

    public LogsObj(String fileName, String folderName, REASON reason) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.reason = reason;
        this.formattedTime = convertFormattedTime();
    }

    public String convertFormattedTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String formattedCreateDate = simpleDateFormat.format(new Date(time));
        return formattedCreateDate;
    }

    @Override
    public String toString() {
        return "LogsObj{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", folderName='" + folderName + '\'' +
                ", reason=" + reason +
                ", time=" + time +
                ", formattedTime='" + formattedTime + '\'' +
                '}';
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public REASON getReason() {
        return reason;
    }

    public void setReason(REASON reason) {
        this.reason = reason;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
