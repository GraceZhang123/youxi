package com.zq.youxi.service;

import com.zq.youxi.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestDao dao;

    public String sayHello() {
        String s = dao.getString();
        System.out.println(s);
        return s;
    }
}
