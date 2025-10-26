package io.github.luyang.starter.mybatis.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * 分页参数
 *
 * @author yang.lu
 */
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private Integer pageNum;
    private Integer pageSize;

    public <T> Page<T> build() {
        int pageNum = Optional.ofNullable(getPageNum()).orElse(DEFAULT_PAGE_NUM);
        int pageSize = Optional.ofNullable(getPageSize()).orElse(DEFAULT_PAGE_SIZE);

        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }

        return new Page<>(pageNum, pageSize);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
