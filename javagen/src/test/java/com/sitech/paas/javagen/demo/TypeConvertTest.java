package com.sitech.paas.javagen.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author liwei_paas
 * @date 2020/3/12
 */
public class TypeConvertTest {

    static User user = new User(1, "zhangsan", 1.2f, new Date());
    static {
       // user.setTeacher(new Teacher("lisi"));
    }

    @Test
    public void fastjsonTypeConvert(){
        String a = "123";
        Float cast = TypeUtils.cast(a, Float.class, null);
        System.out.println(cast);

        String userStr = JSON.toJSONString(user);

        User userCast = JSONObject.toJavaObject(JSONObject.parseObject(userStr), User.class);
        System.out.println(userCast);
    }


    @Test
    public void testConvert(){
        Object o = JSONObject.toJSON(user);
        Assert.assertEquals(o.getClass(),JSONObject.class);
        Object o1 = JSON.toJSON(user);
        Assert.assertEquals(o1.getClass(),JSONObject.class);

        String name = "123";
        Assert.assertEquals(JSON.toJSON(name).getClass(),String.class);

        int age = 123;
        Assert.assertEquals(JSON.toJSON(age).getClass(),Integer.class);
        float f = 12.3f;
        Assert.assertEquals(JSON.toJSON(f).getClass(),Float.class);

        Date date = new Date();
        System.out.println(JSON.toJSON(date));
        Assert.assertEquals(JSON.toJSON(date).getClass(),Date.class);

        List<User> list = Collections.singletonList(user);
        Assert.assertEquals(JSON.toJSON(list).getClass(),JSONArray.class);
        List<String> strList = Collections.singletonList("123");
        Assert.assertEquals(JSON.toJSON(strList).getClass(),JSONArray.class);

        Map<String, User> map = Collections.singletonMap("123", user);
        Assert.assertEquals(JSON.toJSON(map).getClass(),JSONObject.class);
    }

    @Test
    public void testPojo2Json(){
        User user = new User(1, "zhangsan", 1.2f, new Date());
        user.setTeacher(new Teacher("lisi"));
        String s = JSONObject.toJSONString(user);
        System.out.println(s);
    }

    @Test
    public void testStr2Json(){
        String name = "123";
        String s = JSONObject.toJSONString(name);
        System.out.println(s);
        System.out.println(name);

    }

    @Test
    public void json2pojo(){
        String json = "{\"age\":1,\"birthday\":1583981538997,\"name\":\"zhangsan\",\"score\":1.2,\"teacher\":{\"name\":\"lisi\"}}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        User user = JSON.toJavaObject(jsonObject, User.class);

        Integer age = jsonObject.getInteger("age");
        System.out.println(age);
    }

    public static class User implements Serializable {
        int age;
        String name;
        float score;
        Date birthday;
        Teacher teacher;

        public User(int age, String name, float score, Date birthday) {
            this.age = age;
            this.name = name;
            this.score = score;
            this.birthday = birthday;
        }

        public User() {
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }
    }

    public static class Teacher{
        String name;


        public Teacher(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
