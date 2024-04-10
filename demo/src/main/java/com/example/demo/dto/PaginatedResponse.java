package com.example.demo.dto;

import java.util.List;

/**
 * @author 揭程
 * @version 1.0
 */
public class PaginatedResponse<T> {
    private List<T> content;
    private long totalElements;

    public PaginatedResponse(List<T> content, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    // Getters and setters
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
