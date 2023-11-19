package com.codewithyaho.lambda.model;

import com.codewithyaho.lambda.enums.FileSizeTypeEnum;

public class RequestInput {
    private FileSizeTypeEnum type;
    private long size;

    public RequestInput() {
    }

    public RequestInput(FileSizeTypeEnum type, long size) {
        this.type = type;
        this.size = size;
    }

    public FileSizeTypeEnum getType() {
        return type;
    }

    public void setType(FileSizeTypeEnum type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
