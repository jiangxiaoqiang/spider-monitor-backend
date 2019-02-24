package model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    private int page;
    private int size;
    private int totalElements;
    private int totalPage;
    private List<T> content;

    public Page(Pageable pageable, List<T> content) {
        this.page = pageable.getPage();
        this.size = pageable.getSize();
        this.totalElements = pageable.getTotalElements();
        this.content = content;
        this.totalPage = (pageable.getTotalElements() + pageable.getSize() - 1) / pageable.getSize();
    }
}