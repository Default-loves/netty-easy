package com.junyi.netty.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @time: 2021年11月25日 11:56
 * @author: junyi Xu
 */
@Data
@Builder
public class Response {
    private Integer code;
    private String message;
}
