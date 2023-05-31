package com.lab3.controller;

import com.lab3.mybatis.SqlSessionLoader;
import com.lab3.mybatis.po.User;
import com.lab3.request.UserRegisterRequest;
import com.lab3.response.ErrorResponse;
import com.lab3.response.LoginResponse;
import com.lab3.response.UserRegisterResponse;
import com.lab3.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
//    @CrossOrigin(origins = "http://54.174.100.55:4200/")

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(name = "username") String username,@RequestParam(name = "password")String pass, HttpServletResponse response) throws IOException {
        SqlSessionLoader.getSqlSessionFactory();
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        User user = sqlSession.selectOne("com.lab3.UserMapper.findUserByUsername",username);
        if(user==null)
        {
            sqlSession.close();
            return new LoginResponse(false,"The username is wrong");
        }else {
            if(user.getPassword().equals(pass))
            {
                sqlSession.close();
                System.out.println(user.getUsername());
                System.out.println(user.getUserID());
                String token= JwtUtil.createJWT(user.getUsername(),user.getUserID());
                response.setHeader("Authorization",token);
                response.setHeader("username",user.getUsername());
                return new LoginResponse(true,"Login Successfully!");
            }else
            {
                sqlSession.close();
                return new LoginResponse(false,"The username or the password is wrong");
            }
        }
    }

    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllUser() throws IOException {
        SqlSessionLoader.getSqlSessionFactory();
        SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        List<User> res =  sqlSession.selectList("com.lab3.UserMapper.findAllUser");
        sqlSession.close();
        return res;
    }
}
