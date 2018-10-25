package org.demon.starter.common.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 */
public class Pager<T> implements Serializable {
    private static final long serialVersionUID = -1886255970252256895L;
    /**
     * 每页默认为20条记录
     */
    public static final int DEFAULT_PAGE_SIZE=20;
    
    private final static String ORDER_ASC="asc";
    /**
     * 当前页号
     */
    private int currentPage;
    /**
     * 每页记录数
     */
    private int pageSize=DEFAULT_PAGE_SIZE;

    /**
     * 排序方式 desc|asc
     */
    private String sortOrder=ORDER_ASC;
    /**
     *排序字段 多个以 逗号隔开
     */
    private String sortName;


    /**
     * 结果集起始记录数
     */
    private int startRow;
    private int startNum;
    private int endNum;

    /**
     * 总计页数
     */
    private int totalPages;

    /**
     * 总计记录数
     */
    private int totalRows;
    private List<T> data;

    public Pager() {
        currentPage = 1;
        startRow = 0;
        totalRows = 0;
        startNum = 0;
        endNum =  0;
    }

    /**
     * 构造函数 通过记录总数和每页记录数计算出相关信息
     *
     * param _totalRows  int //记录总数
     * param onePageSize int //每页记录数
     */
    public Pager(int _totalRows, int onePageSize) {
        setPageSize(onePageSize);
        totalRows = _totalRows;
        totalPages = totalRows / pageSize;
        int mod = totalRows % pageSize;
        if(mod > 0) {
            totalPages++;
        }
        if(totalRows == 0) {
            currentPage = 0;
        } else {
            currentPage = 1;
        }
        startRow = 0;
        if(totalRows == 0) {
            startNum = 0;
        } else {
            startNum = 1;
        }
        if(totalRows <= pageSize) {
            endNum = totalRows;
        } else {
            if(currentPage == totalPages) {
                endNum = totalRows;
            } else {
                endNum = pageSize * currentPage;
            }
        }
    }

    /**
     * 构造函数 通过记录总数和每页记录数计算出相关信息
     *
     * @param _totalRows  int //记录总数
     * @param onePageSize int //每页记录数
     */
    public Pager(int _totalRows, int onePageSize, int currentPage) {

        setPageSize(onePageSize);
        totalRows = _totalRows;
        totalPages = totalRows / pageSize;

        int mod = totalRows % pageSize;
        if(mod > 0) {
            totalPages++;
        }
        if(totalPages < currentPage) {
            currentPage = totalPages;
        }
        if(currentPage < 1) {
            currentPage = 1;
        }
        if(totalRows == 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        startRow = 0;
        if(totalRows == 0) {
            startNum = 0;
        } else {
            startNum = (currentPage - 1) * onePageSize;
        }
        if(totalRows <= pageSize) {
            endNum = totalRows;
        } else {
            if(currentPage == totalPages) {
                endNum = totalRows;
            } else {
                endNum = pageSize * currentPage;
            }
        }
    }

    /**
     * 第一页
     */
    public void first() {
        if(totalRows == 0) {
            currentPage = 0;
        } else {
            currentPage = 1;
        }
        startRow = 0;
        if(totalRows == 0) {
            startNum = 0;
        } else {
            startNum = 1;
        }
        if(totalRows <= pageSize) {
            endNum = totalRows;
        } else {
            if(currentPage == totalPages) {
                endNum = totalRows;
            } else {
                endNum = pageSize * currentPage;
            }
        }
    }

    /**
     * 最后一页
     */
    public void last() {
        if(totalPages != 0) {
            currentPage = totalPages;
            startRow = (currentPage - 1) * pageSize;
            startNum = startRow + 1;
            endNum = totalRows;
        }
    }

    /**
     * 下一页
     */
    public void next() {

        if(currentPage < totalPages && totalPages != 0) {
            currentPage++;
        }
        if(totalPages != 0) {
            startRow = (currentPage - 1) * pageSize;
        }
        startNum = startRow + 1;
        if(currentPage < totalPages && totalPages != 0) {
            endNum = startRow + pageSize;
        } else {
            endNum = totalRows;
        }
    }

    /**
     * 上一页
     */
    public void previous() {
        if(currentPage <= 1) {
            first();
        } else {
            currentPage--;
            startRow = (currentPage - 1) * pageSize;
            startNum = startRow + 1;
            if(currentPage < totalPages && totalPages != 0) {
                endNum = startRow + pageSize;
            } else {
                endNum = totalRows;
            }
        }
    }

    /**
     * 更新页
     *
     * @param _currentPage int
     */
    public void refresh(int _currentPage) {
        currentPage = _currentPage;
        if(currentPage > totalPages) {
            last();
        }
        if(currentPage < 0) {
            first();
        }
    }

    /**
     * 指定页码
     */
    public void go(int _pageNo) {
        if(_pageNo <= 1) {
            first();
        } else if(_pageNo >= totalPages) {
            last();
            if(totalPages < 1) {
                currentPage = 0;
            }
        } else {
            startRow = (currentPage - 1) * pageSize;
            startNum = startRow + 1;
            endNum = startRow + pageSize;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        /**
         * FIXME 重新计算 totalPages 总页数
         */
        totalPages = (totalRows / pageSize)+((totalRows % pageSize)==0?0:1);
        /**
         * 重新计算其它值
         */
        go(currentPage);
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getStartNum() {
        if(startNum < 0) startNum = 0;
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
