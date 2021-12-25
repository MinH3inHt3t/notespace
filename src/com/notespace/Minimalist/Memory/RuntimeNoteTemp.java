package com.notespace.Minimalist.Memory;

import java.util.UUID;

public class RuntimeNoteTemp {

    private String id = UUID.randomUUID().toString();
    private String fileName;
    private String text;

    public RuntimeNoteTemp(String fileName, String text) {
        this.fileName = fileName;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RuntimeNoteTemp{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
