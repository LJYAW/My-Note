package com.sino.base.system.entity;

/**
 *  jqGrid 分页封装参数
 *
 * @author change
 *
 */
public class JqPageBean {

    private String _search;
    private String nd;
    private int page;
    private int rows;
    private String sidx;
    private String sord;

    private double dCount = 0.00;
    private double dRows = 20.00;
    private int total ;

    public String get_search() {
        return _search;
    }

    public void set_search(String _search) {
        this._search = _search;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public double getdCount() {
        return dCount;
    }

    public void setdCount(double dCount) {
        this.dCount = dCount;
    }

    public double getdRows() {
        return dRows;
    }

    public void setdRows(double dRows) {
        this.dRows = dRows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
