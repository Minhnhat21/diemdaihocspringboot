package com.example.thongbaotrungtuyendh.util;

import com.example.thongbaotrungtuyendh.payload.response.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



@Component
public class AppUtils {
    public static Pageable buildPageRequest(Integer page, Integer limit) {
        if(null == page) {
            page = 0;
        }
        if(null == limit) {
            limit = 10;
        }
        return PageRequest.of(page, limit);
    }
    public static PageInfo buildPageResponse(Page page) {
        PageInfo pageInfo = new PageInfo<>();
        pageInfo.setData(page.getContent());
        pageInfo.setPage(page.getNumber());
        pageInfo.setPages(page.getTotalPages());
        pageInfo.setLimit(page.getSize());
        pageInfo.setTotal((int) page.getTotalElements());
        return pageInfo;
    }
}
