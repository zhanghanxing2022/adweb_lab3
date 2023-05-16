package com.lab3.controller;

import com.lab3.mybatis.SqlSessionLoader;
import com.lab3.mybatis.po.User;
import com.lab3.request.UserRegisterRequest;
import com.lab3.response.ErrorResponse;
import com.lab3.response.UserRegisterResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Object register(@RequestBody UserRegisterRequest request)throws IOException {
        SqlSessionLoader.getSqlSessionFactory();
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();

        User user = sqlSession.selectOne("com.lab3.UserMapper.findUserByUsername",request.getUsername());
        if(user!=null)
        {
            sqlSession.close();
            return new ErrorResponse("The username is already used");
        }else {
            sqlSession.insert("com.lab3.UserMapper.addUser",
                    new User(request.getUsername(), request.getPassword(),
                            request.getEmail(), request.getPhone()));

            sqlSession.commit();
            sqlSession.close();
            return new UserRegisterResponse("Register Successfully!");
        }
    }
}
