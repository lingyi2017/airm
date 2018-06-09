package com.infosoul.mserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * resource 基类
 *
 * @author longxy
 * @date 2018-06-09 15:52
 */
public abstract class BaseResource {

    protected static final Logger logger = LoggerFactory.getLogger(BaseResource.class);

    protected Response success() {
        return Response.success();
    }

    protected Response success(Object content) {
        return Response.success(content);
    }

    protected Response success(Object content, Integer total) {
        return Response.success(content, total);
    }

    protected Response error(Response.Status status) {
        return Response.error(status);
    }

    protected Response error(Response.Status status, String errorMsg) {
        return Response.error(status, errorMsg);
    }

}
