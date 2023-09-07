package com.project.ipyang.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PageDto<T> {
    private T dto;
    private int startPage;
    private int endPage;

    public PageDto(T dto, int startPage, int endPage) {
        this.dto = dto;
        this.startPage = startPage;
        this.endPage = endPage;
    }
}
