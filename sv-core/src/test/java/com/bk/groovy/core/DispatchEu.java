package com.bk.groovy.core;

import com.bk.sv.core.handler.SvHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author BK
 * @description:
 * @date 2019/5/23 23:21
 */
@Component
public class DispatchEu implements SvHandler {
    @Override
    public Object handler(Map<String, Object> params) {
        System.out.println("dispatch execution unit ");
        return "execution unit ";
    }
}
