package com.notespace.Minimalist.Memory;

import com.ocpsoft.pretty.time.PrettyTime;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class EditHistoryObj implements Serializable {

    private String id= UUID.randomUUID().toString();
    private String fileName;
    private String textData;
    private long editTime = System.currentTimeMillis();
    private String prettyTime;

    public EditHistoryObj(String fileName, String textData) {
        this.fileName = fileName;
        this.textData = textData;
        this.prettyTime = convertPrettyTime();
    }
    public String  convertPrettyTime(){
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(new Date(editTime));
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

    public long getEditTime() {
        return editTime;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public String getPrettyTime() {
        return prettyTime;
    }

    public void setPrettyTime(String prettyTime) {
        this.prettyTime = prettyTime;
    }

    @Override
    public String toString() {
        return "EditHistoryObj{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", textData='" + textData + '\'' +
                ", editTime=" + editTime +
                ", prettyTime='" + prettyTime + '\'' +
                '}';
    }
}
