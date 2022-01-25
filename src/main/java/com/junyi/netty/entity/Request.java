package com.junyi.netty.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @time: 2021年11月25日 11:51
 * @author: junyi Xu
 */
@Data
@Builder
public class Request implements Serializable {
    private String id;
    private String msg;
}
