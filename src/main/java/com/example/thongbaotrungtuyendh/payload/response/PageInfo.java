package com.example.thongbaotrungtuyendh.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    private int page;
    private int pages;
    private int limit;
    private int total;
    private List<T> data;

}
