package com.abreaking.easyjpa.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:*.xml"})
public class SpringTest {

    @Resource(name = "userServiceSecond")
    UserService userService;

    @Test
    public void test01(){
        userService.print();
    }


}
