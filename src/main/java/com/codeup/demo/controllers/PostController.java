package com.codeup.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import com.codeup.demo.models.Post;
import com.codeup.demo.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
class PostController {
    private final PostRepository postRepo;


    public PostController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/post")
    public String index(Model model) {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("Second Post", "Testing posting another post"));
        postList.add(new Post("Third Post", "Third post, auto-generated post"));
        model.addAttribute("postList", postList);
        return "post/index";
    }

    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Integer id, Model model) {
        Post post = new Post("A Single Post", "This is the body of a single test post.");
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/post/create")
    public String showCreatePost () {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name = "title") String title,
                             @RequestParam(name = "body") String body,
                             Model model) {
        Post post = new Post(title, body);
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        postRepo.delete(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/edit")
    public String updateAd(@RequestParam(name = "id") long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "body") String body) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setBody(body);
        postRepo.save(post);
        return "redirect:/ads/" + post.getId();
    }
}
