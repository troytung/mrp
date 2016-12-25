
package com.petfood.mrp.model;

import java.util.Iterator;
import java.util.List;

public class PageContainer<E> implements Iterable<E> {

    private List<E> rows;
    private int page;
    private long totalRows;
    private long totalPages;

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public Iterator<E> iterator() {
        return rows.iterator();
    }
    
    
}
