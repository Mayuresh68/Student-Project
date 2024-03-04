package com.mayuresh.student.Response;

import java.io.Serializable;
import java.util.List;

public class GenericListResponse<T> implements Serializable {
    private String status;
    private String error;
    private List<T> dataList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
