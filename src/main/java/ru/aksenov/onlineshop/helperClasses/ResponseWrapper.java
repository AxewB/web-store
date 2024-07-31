package ru.aksenov.onlineshop.helperClasses;

import java.util.List;

public class ResponseWrapper<T> {
    private List<T> data;
    private int total;
    private int limit;
    private int skip;

    public ResponseWrapper(List<T> data, int total, int limit, int skip) {
        this.data = data;
        this.total = total;
        this.limit = limit;
        this.skip = skip;
    }
    // Геттеры и сеттеры

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
