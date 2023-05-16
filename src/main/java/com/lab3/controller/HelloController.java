package com.lab3.controller;

import com.lab3.response.GreetingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {
    private final AtomicLong counter = new AtomicLong();
    @CrossOrigin(origins = "http://localhost:4200/")
    @RequestMapping("/hello")

    public  String index()
    {
        return "Hello, world";
    }
    @CrossOrigin(origins = "http://localhost:4200/")
    @RequestMapping("/greeting")
    /*
    * @ResponseBody注解的作用是将controller的方法返回的对象 通过适当的转换器 转换为指定的格式之后，写入到response对象的body区（响应体中），通常用来返回JSON数据或者是XML。

　　数据，需要注意的呢，在使用此注解之后不会再走视图处理器，而是直接将数据写入到输入流中，它的效果等同于通过response对象输出指定格式的数据。
*/
    public @ResponseBody GreetingResponse greeting(@RequestParam(value = "name",
            defaultValue = "World") String name){
        return new GreetingResponse(counter.incrementAndGet(), "Hello, " + name
                + "!"); }
}
