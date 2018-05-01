package com.kickcity.task.blogmanagement.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class RecordDto {

    @JsonIgnore
    private Long id;

    private String title;

    private String text;

    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
