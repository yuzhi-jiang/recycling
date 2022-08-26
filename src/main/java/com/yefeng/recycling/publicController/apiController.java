package com.yefeng.recycling.publicController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class apiController {
    private void response401(ServletRequest request, ServletResponse response
            , String msg) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            // //请求转发401controller
            httpServletRequest.getRequestDispatcher("/rbacManager/401/" + msg).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
