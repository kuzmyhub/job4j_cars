package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.servise.PostService;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class PostController {

    private PostService service;

    @GetMapping("/carShop")
    public String getPosts(Model model) {
        List<Post> posts = service.findAll();
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/openPost/{id}")
    public String openPost(Model model,
                           @PathVariable(name = "id") int id) {
        Optional<Post> optionalPost = service.findById(id);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        model.addAttribute("post", optionalPost.get());
        return "post/post";
    }

    @GetMapping("/carPhoto/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = service.findById(postId);
        Post post = optionalPost.get();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }
}
