package com.notespace.Minimalist.Memory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Backup implements Serializable {

    private String id = UUID.randomUUID().toString();
    private String fileName;
    private String backupFileName;
    private String textData;
    private long deletedTime = System.currentTimeMillis();
    private String formattedTime;

    public Backup(String fileName, String textData) {
        this.fileName = fileName;
        this.backupFileName = "backup " + fileName;
        this.textData = textData;
        this.formattedTime = convertFormattedTime();
    }


    public String convertFormattedTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String formattedCreateDate = simpleDateFormat.format(new Date(deletedTime));
        return formattedCreateDate;
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

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public long getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(long deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    @Override
    public String toString() {
        return "Backup{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", backupFileName='" + backupFileName + '\'' +
                ", textData='" + textData + '\'' +
                ", deletedTime=" + deletedTime +
                ", formattedTime='" + formattedTime + '\'' +
                '}';
    }

    public String getBackupFileName() {
        return backupFileName;
    }

    public void setBackupFileName(String backupFileName) {
        this.backupFileName = backupFileName;
    }

}
