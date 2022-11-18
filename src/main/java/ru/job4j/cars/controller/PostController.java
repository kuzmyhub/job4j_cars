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
import ru.job4j.cars.service.*;
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

    private PostService simplePostService;
    private CarService simpleCarService;
    private EngineService simpleEngineService;
    private PriceHistoryService simplePriceHistoryService;
    private DriverService simpleDriverService;

    @GetMapping("/carShop")
    public String getPosts(Model model,
                           @RequestParam(name = "filter", required = false)
                           String filter,
                           @RequestParam(name = "brand", required = false)
                               String brand, HttpSession httpSession) {
        System.out.println(filter + "popo");
        System.out.println(brand + "popo");
        List<Post> posts = null;
        if (filter == null) {
            posts = simplePostService.findAll();
        } else if (filter.equals("day")) {
            posts = simplePostService.findByLastDay();
        } else if (filter.equals("photo")) {
            posts = simplePostService.findByAvailabilityPhoto();
        } else if (filter.equals("brand")) {
            posts = simplePostService.findByBrand(brand);
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
        Optional<Post> optionalPost = simplePostService.findById(id);
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
        Optional<Car> optionalCar = simpleCarService.findById(post.getCar().getId());
        if (optionalCar.isEmpty()) {
            return "404";
        }
        Set<Driver> owners = optionalCar.get().getOwners();
        for (Driver d : owners) {
            System.out.println(d.getName());
        }
        model.addAttribute("owners", owners);
        model.addAttribute("user", user);
        return "post/post";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession httpSession) {
        Post post = new Post();
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", post);
        model.addAttribute("engines", simpleEngineService.findAll());
        model.addAttribute("user", user);
        return "post/addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, HttpSession httpSession,
                             @ModelAttribute(name = "engineId") int engineId,
                             @ModelAttribute(name = "price") int price,
                             @ModelAttribute(name = "driverName") String driverName,
                             @RequestParam (name = "file")
                                     MultipartFile file) throws IOException {
        User user = SessionUser.getSession(httpSession);
        Optional<Engine> engine = simpleEngineService.findById(engineId);
        if (engine.isEmpty()) {
            return "redirect:/formAddPost";
        }
        post.getCar().setEngine(engine.get());
        Optional<Driver> optionalDriver = simpleDriverService.findByUser(user);
        Driver driver;
        if (optionalDriver.isEmpty()) {
            driver = new Driver();
            driver.setName(driverName);
            driver.setUser(user);
            simpleDriverService.add(driver);
        } else {
            driver = optionalDriver.get();
        }
        post.getCar().setOwners(Set.of(driver));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(0);
        priceHistory.setAfter(price);
        simplePriceHistoryService.add(priceHistory);
        post.setPhoto(file.getBytes());
        post.setPriceHistories(List.of(priceHistory));
        post.setUser(user);
        post.setHead(post.getCar().getBrand() + " " + post.getCar().getModel());
        simpleCarService.add(post.getCar());
        simplePostService.add(post);
        return "redirect:/openPost/" + post.getId();
    }

    @GetMapping("/formEditDescription")
    public String formEdit(Model model, HttpSession httpSession,
                           @ModelAttribute(name = "postId") int postId) {
        Optional<Post> optionalPost = simplePostService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", optionalPost.get());
        model.addAttribute("user", user);
        return "post/editDescription";
    }

    @PostMapping("/editDescription")
    public String edit(@ModelAttribute(name = "postId") int postId,
                       @ModelAttribute(name = "description") String description) {
        simplePostService.updateDescription(postId, description);
        return "redirect:/openPost/" + postId;
    }

    @GetMapping("/changeStatus")
    public String changeStatus(@ModelAttribute(name = "postId") int postId) {
        Optional<Post> optionalPost = simplePostService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "redirect:/404";
        }
        simplePostService.changeStatus(postId, !optionalPost.get().isSold());
        return "redirect:/openPost/" + postId;
    }

    @GetMapping("/formEditPriceHistory")
    public String formEditPrice(Model model, HttpSession httpSession,
                                @ModelAttribute(name = "postId") int postId) {
        Optional<Post> optionalPost = simplePostService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("post", optionalPost.get());
        List<PriceHistory> priceHistories = optionalPost.get().getPriceHistories();
        priceHistories.sort(Comparator.comparing(PriceHistory::getCreated));
        model.addAttribute("price", priceHistories.get(priceHistories.size() - 1).getAfter());
        model.addAttribute("user", user);
        return "post/editPriceHistory";
    }

    @PostMapping("/editPriceHistory")
    public String edit(@ModelAttribute(name = "postId") int postId,
                       @ModelAttribute(name = "price") int price) {
        Optional<Post> optionalPost = simplePostService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        List<PriceHistory> priceHistories = optionalPost.get().getPriceHistories();
        priceHistories.sort(Comparator.comparing(PriceHistory::getCreated));
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(priceHistories.get(priceHistories.size() - 1).getAfter());
        priceHistory.setAfter(price);
        priceHistories.add(priceHistory);
        Post post = optionalPost.get();
        post.setPriceHistories(priceHistories);
        simplePostService.update(post);
        return "redirect:/openPost/" + postId;
    }

    @GetMapping("/carPhoto/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> optionalPost = simplePostService.findById(postId);
        Post post = optionalPost.get();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
    }
}
