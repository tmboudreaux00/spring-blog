package com.codeup.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String getPostsIndex() {
        return "Post Index";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String getPostID(@PathVariable int id) {
        return "Post ID: " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String getPostCreate() {
        return "Create post form";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "Post created";
    }

}
