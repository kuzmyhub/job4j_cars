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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.servise.CarService;
import ru.job4j.cars.servise.EngineService;
import ru.job4j.cars.servise.PostService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private CarService carService;
    private EngineService engineService;

    @GetMapping("/carShop")
    public String getPosts(Model model,
                           @RequestParam(name = "filter", required = false)
                           String filter,
                           @RequestParam(name = "brand", required = false)
                               String brand) {
        List<Post> posts = null;
        if (filter == null) {
            posts = postService.findAll();
        } else if (filter.equals("day")) {
            posts = postService.findByLastDay();
        } else if (filter.equals("photo")) {
            posts = postService.findByAvailabilityPhoto();
        } else if (filter.equals("brand")) {
            posts = postService.findByBrand(brand);
        }
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/formFindByBrand")
    public String findByBrand() {
        return "post/findByBrand";
    }

    @GetMapping("/openPost/{id}")
    public String openPost(Model model,
                           @PathVariable(name = "id") int id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        Post post = optionalPost.get();
        List<PriceHistory> priceHistories = post.getPriceHistories();
        priceHistories.sort(Comparator.comparing(PriceHistory::getCreated));
        PriceHistory lastChange = priceHistories.get(priceHistories.size() - 1);
        model.addAttribute("post", post);
        model.addAttribute("priceHistories", priceHistories);
        model.addAttribute("price", lastChange.getAfter());
        return "post/post";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("engines", engineService.findAll());
        return "post/addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @ModelAttribute(name = "engineId") int engineId,
                             @RequestParam (name = "file")
                                     MultipartFile file) throws IOException {
        Optional<Engine> engine = engineService.findById(engineId);
        if (engine.isEmpty()) {
            return "redirect:/formAddPost";
        }
        post.getCar().setEngine(engine.get());
        post.setPhoto(file.getBytes());
        carService.add(post.getCar());
        postService.add(post);
        return "redirect:/openPost/" + post.getId();
    }

    @GetMapping("/carPhoto/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        Post post = optionalPost.get();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }
}
