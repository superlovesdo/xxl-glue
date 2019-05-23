package com.bk.sv.core.handler;

import java.util.Map;

/**
 * @author BK
 * @description: sv 处理器
 * @date 2019/5/9 22:00
 */
public interface SvHandler {

    Object handler(Map<String, Object> params);
}
