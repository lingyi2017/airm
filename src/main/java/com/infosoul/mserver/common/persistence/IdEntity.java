package com.infosoul.mserver.common.persistence;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.infosoul.mserver.common.utils.IdGen;


/**
 * 数据Entity类
 * 
 * @author free lance
 * @version 2013-05-28
 */
@MappedSuperclass
public abstract class IdEntity<T> extends DataEntity<T> implements Serializable {

    /**
     * 编号
     */
    protected String id;

    public IdEntity() {
        super();
    }

    @PrePersist
    @Override
    public void prePersist() {
        super.prePersist();
        this.id = IdGen.uuid();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
