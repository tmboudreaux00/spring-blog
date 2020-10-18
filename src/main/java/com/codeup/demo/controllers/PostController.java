package com.codeup.demo.controllers;

import com.codeup.demo.models.Post;
import com.codeup.demo.models.User;
import com.codeup.demo.repositories.PostRepository;
import com.codeup.demo.repositories.UserRepository;
import com.codeup.demo.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final EmailService emailService;

    public PostController(PostRepository postRepo, UserRepository userRepo, EmailService emailService) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        List<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts.json")
    public @ResponseBody List<Post> viewAllPostsInJSONFormat() {
        return postRepo.findAll();
    }

    @GetMapping("/posts/ajax")
    public String viewAllPostsWithAjax() {
        return "posts/ajax";
    }

    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable Integer id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post.getUser() == null) {
            List <User> users = userRepo.findAll();
            post.setUser(users.get(0));
        }
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String showCreatePost (Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {

        // Send the create email
        if (post.getId() == 0) {

            // Get the currently logged in user and save their ID as the author of this post
            User thisAuthor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(thisAuthor);


            // post.setUser(userRepo.findAll().get(0)); // kluge to set a current user


            emailService.prepareAndSend(post.getUser().getEmail(),
                    "Created Post: " + post.getTitle(),
                    post.getTitle() + "\n\n" + post.getBody());
        }

        // Send email for an edit
        else {
            post.setUser(postRepo.getPostById(post.getId()).getUser()); // Get the user from the database
            emailService.prepareAndSend(post.getUser().getEmail(),
                    "Edited Post: " + post.getTitle(),
                    post.getTitle() + "\n\n" + post.getBody());
        }
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deleteAd(@PathVariable long id) {
        Post post = postRepo.getPostById(id);
        post.setUser(postRepo.getPostById(post.getId()).getUser()); // Get the user from the database

        // send email for a post delete
        emailService.prepareAndSend(post.getUser().getEmail(),
                "Created Post: " + post.getTitle(),
                post.getTitle() + "\n\n" + post.getBody());
        postRepo.delete(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editAd(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        model.addAttribute("post", post);
        return "posts/create";
    }

    @PostMapping("/posts/edit")
    public String updateAd(@ModelAttribute Post post) {
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }
}
