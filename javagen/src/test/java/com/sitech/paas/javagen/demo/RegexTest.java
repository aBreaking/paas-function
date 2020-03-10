package com.sitech.paas.javagen.demo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @{USER}
 * @{DATE}
 */
public class RegexTest {

    @Test
    public void testPlaceholder(){
        String regex = "\\$\\{[^}]+\\}";
        String s = "abc${name[1]}de${user.name}f";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
