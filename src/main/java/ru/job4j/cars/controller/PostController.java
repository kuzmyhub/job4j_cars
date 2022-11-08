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
import ru.job4j.cars.servise.*;
import ru.job4j.cars.util.SessionUser;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ThreadSafe
@Controller
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private CarService carService;
    private EngineService engineService;
    private PriceHistoryService priceHistoryService;
    private DriverService driverService;

    @GetMapping("/carShop")
    public String getPosts(Model model,
                           @RequestParam(name = "filter", required = false)
                           String filter,
                           @RequestParam(name = "brand", required = false)
                               String brand, HttpSession httpSession) {
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
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "post/posts";
    }

    @GetMapping("/formFindByBrand")
    public String findByBrand(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("user", user);
        return "post/findByBrand";
    }

    @GetMapping("/openPost/{id}")
    public String openPost(Model model, HttpSession httpSession,
                           @PathVariable(name = "id") int id) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        Post post = optionalPost.get();
        List<PriceHistory> priceHistories = post.getPriceHistories();
        priceHistories.sort(Comparator.comparing(PriceHistory::getCreated));
        PriceHistory lastChange = priceHistories.get(priceHistories.size() - 1);
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", post);
        model.addAttribute("priceHistories", priceHistories);
        model.addAttribute("price", lastChange.getAfter());
        model.addAttribute("user", user);
        return "post/post";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession httpSession) {
        Post post = new Post();
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", post);
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("user", user);
        return "post/addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, HttpSession httpSession,
                             @ModelAttribute(name = "engineId") int engineId,
                             @ModelAttribute(name = "price") int price,
                             @RequestParam (name = "file")
                                     MultipartFile file) throws IOException {
        User user = SessionUser.getSession(httpSession);
        Optional<Engine> engine = engineService.findById(engineId);
        if (engine.isEmpty()) {
            return "redirect:/formAddPost";
        }
        post.getCar().setEngine(engine.get());
        Driver driver = new Driver();
        driver.setName(user.getLogin());
        driver.setUser(user);
        driverService.add(driver);
        post.getCar().setOwners(Set.of(driver));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(0);
        priceHistory.setAfter(price);
        priceHistoryService.add(priceHistory);
        post.setPhoto(file.getBytes());
        post.setPriceHistories(List.of(priceHistory));
        post.setUser(user);
        post.setHead(post.getCar().getBrand() + " " + post.getCar().getModel());
        carService.add(post.getCar());
        postService.add(post);
        return "redirect:/openPost/" + post.getId();
    }

    @GetMapping("/formEdit")
    public String formEdit(Model model, HttpSession httpSession,
                           @ModelAttribute(name = "postId") int postId) {
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("user", user);
        return "post/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Post post,
                       @ModelAttribute(name = "engineId") int engineId,
                       @ModelAttribute(name = "price") int price,
                       @ModelAttribute(name = "postId") int postId,
                       @RequestParam (name = "file")
                           MultipartFile file) throws IOException {
        Optional<Engine> engine = engineService.findById(engineId);
        if (engine.isEmpty()) {
            return "redirect:/formAddPost";
        }
        System.out.println("popo" + postId);
        Optional<Post> optionalPost = postService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        Post postDB = optionalPost.get();
        post.getCar().setEngine(engine.get());
        post.getCar().setId(postDB.getCar().getId());
        post.getCar().setOwners(postDB.getCar().getOwners());
        PriceHistory priceHistory = new PriceHistory();
        List<PriceHistory> priceHistories = postDB.getPriceHistories();
        priceHistories.sort(Comparator.comparing(PriceHistory::getCreated));
        PriceHistory lastChange = priceHistories.get(priceHistories.size() - 1);
        priceHistory.setBefore(lastChange.getAfter());
        priceHistory.setAfter(price);
        priceHistoryService.add(priceHistory);
        postDB.setPhoto(file.getBytes());
        postDB.getPriceHistories().add(priceHistory);
        postDB.setHead(post.getCar().getBrand() + " " + post.getCar().getModel());
        carService.update(post.getCar());
        postDB.setCar(post.getCar());
        postService.update(post);
        return "redirect:/openPost/" + postDB.getId();
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
