package com.junyi.netty.entity;

import io.netty.util.Recycler;

/**
 * @time: 2021/1/4 11:02
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class User {
    private Integer id;
    private String description;
    private Recycler.Handle<User> handler;

    public User() {
    }

    public User(Recycler.Handle<User> handler) {
        this.handler = handler;
    }

    public void recycle() {
        handler.recycle(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
