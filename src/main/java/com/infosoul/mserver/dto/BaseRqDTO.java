package com.infosoul.mserver.dto;

import java.io.Serializable;

/**
 * 查询基类DTO
 *
 * @author longxy
 * @date 2018-06-22 23:09
 */
public abstract class BaseRqDTO implements Serializable {

    private static final long serialVersionUID = 278056301567619368L;

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页容量
     */
    private Integer pageSize;

    /**
     * 令牌
     */
    private String token;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
