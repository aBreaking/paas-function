package com.abreaking.easyjpa.spring;

import org.springframework.stereotype.Component;

/**
 * @{USER}
 * @{DATE}
 */
@Component
public class UserServiceFirst implements UserService {

    @Override
    public void print() {
        System.out.println("first");
    }
}