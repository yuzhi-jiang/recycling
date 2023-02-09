package com.yefeng.recycling.util;

import org.springframework.http.HttpHeaders;

public interface AuthConstant {
    String ALGORITHM_TYPE = "md5";
    int HASH_ITERATIONS = 2;
    String AUTHENTICATION_HEADER = HttpHeaders.COOKIE;
    String AUTHENTICATION_HEADER_SET_COOKIE= HttpHeaders.SET_COOKIE;
}