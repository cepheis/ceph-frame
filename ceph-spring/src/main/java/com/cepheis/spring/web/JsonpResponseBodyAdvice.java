package com.cepheis.spring.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice {

    protected JsonpResponseBodyAdvice() {
        super("callback");
    }
}