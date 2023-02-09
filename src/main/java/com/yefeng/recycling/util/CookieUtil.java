package com.yefeng.recycling.util;

import javax.servlet.http.Cookie;

/**
 * @author yefeng
 * @version 1.0.0
 * @ClassName CookieUtil.java
 * @Description TODO
 * @createTime 2022年05月11日 15:45:00
 */
public class CookieUtil {
    public static String getCookieValue(String cookieName, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getAuthenticationByCookie(Cookie[] cookies) {
        if (cookies != null) {
            return getCookieValue("Authentication", cookies);
        }
        return null;
    }
}
