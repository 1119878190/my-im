package com.myim.common.responseprotocal;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分页响应
 *
 * @author liuxu
 * @date 2023/04/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    /**
     * 总数
     */
    private long totalCount;

    /**
     * 当前页
     */
    private long pageNum;

    /**
     * 每页显示条数
     */
    private long pageSize;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 数据
     */
    private T data;




}
