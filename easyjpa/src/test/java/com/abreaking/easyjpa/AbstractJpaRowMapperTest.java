package com.abreaking.easyjpa;

import com.abreaking.easyjpa.matrix.Matrix;
import org.junit.Test;

import java.util.Date;

/**
 * test for AbstractJpaRowMapper
 * @author liwei_paas
 * @date 2019/11/8
 */
public class AbstractJpaRowMapperTest {

    @Test
    public void testEmptyMatrix(){
        User user = new User();
        System.out.println(user.emptyMatrix());
    }

    @Test
    public void testMatrix(){
        User user = new User();
        Matrix matrix = user.matrix();
        System.out.println(matrix);
    }

    class User extends AbstractJpaRowMapper{
        String userName;
        Integer age;
        Date birthday;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }

}
