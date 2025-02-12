package com.sketchuling.schedule.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private int id;
    private int userId;
    private int categoryId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
