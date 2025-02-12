package com.sketchuling.to_do_list.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class to_do_list {

    private int id;
    private int categoryId;
    private int subcategoryId;
    private String todolist;
    private boolean isChecked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
