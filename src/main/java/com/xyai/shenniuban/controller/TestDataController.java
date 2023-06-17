package com.xyai.shenniuban.controller;

import com.xyai.shenniuban.service.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value= "/test")
public class TestDataController {
    @Autowired
    private TestDataService testDataService;

    @PostMapping("/getdata")
    @ResponseBody
    public Map<String,String> login(@RequestParam(value = "username",required = false) String username,
                                    @RequestParam(value = "password",required = false) String password,
                                    @RequestParam(value = "code",required = false) String code,
                                    HttpSession session, HttpServletResponse response, Model model) {
//        LOGGER.info("enter login");
        Map<String,String> map = new HashMap<String, String>();
        System.out.println();
        map.put("url", testDataService.getById(1).toString());
        map.put("token",response.getHeader("token"));
//        LOGGER.info("leave login");
        return map;
    }

}
