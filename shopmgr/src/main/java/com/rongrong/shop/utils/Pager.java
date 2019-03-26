package com.rongrong.shop.utils;

public class Pager {
    private int pageIndex;
    private int pageSize = 3;
    private int totalCount;
    private int totalPages;

    public int getPageIndex() {
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        pageIndex = pageIndex >= getTotalPages() ? getTotalPages() : pageIndex;
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return (this.getTotalCount() - 1) / this.getPageSize() + 1;
    }

//    public void setTotalPages(int totalPages) {
//        this.totalPages = totalPages;
//    }

    // 分页的第一个参数
    public int getFirstParam(){
        return (this.getPageIndex()  -1)*this.getPageSize();
    }
}
