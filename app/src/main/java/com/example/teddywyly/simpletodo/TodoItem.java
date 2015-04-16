package com.example.teddywyly.simpletodo;

import java.io.Serializable;

/**
 * Created by teddywyly on 4/15/15.
 */
public class TodoItem implements Serializable {

    private static final long serialVersionUID = 5177222050535318633L;
    private int id;
    private String body;
    private int priority;

    public TodoItem(String body, int priority) {
        super();
        this.body = body;
        this.priority = priority;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
