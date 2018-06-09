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

    protected ResponseRest success() {
        return ResponseRest.success();
    }

    protected ResponseRest success(Object content) {
        return ResponseRest.success(content);
    }

    protected ResponseRest success(Object content, Integer total) {
        return ResponseRest.success(content, total);
    }

    protected ResponseRest error(ResponseRest.Status status) {
        return ResponseRest.error(status);
    }

    protected ResponseRest error(ResponseRest.Status status, String errorMsg) {
        return ResponseRest.error(status, errorMsg);
    }

}
