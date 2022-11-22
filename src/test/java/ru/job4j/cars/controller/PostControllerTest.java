package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    public void whenGetPosts() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        String filter = null;
        String brand = null;
        User user = new User();
        user.setLogin("Гость");
        List<Post> posts = List.of(new Post());
        when(simplePostService.findAll()).thenReturn(posts);
        String expected = "post/posts";
        String page = postController.getPosts(
                model, filter, brand, httpSession
        );
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenFindByBrand() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        User user = new User();
        user.setLogin("Гость");
        String expected = "post/findByBrand";
        String page = postController.findByBrand(model, httpSession);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenPostThenSuccess() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        int id = 1;
        User user = new User();
        user.setLogin("Гость");
        Driver driver = new Driver();
        driver.setName("driver");
        Car car = new Car();
        car.setId(1);
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Set<Driver> owners = Set.of(driver);
        car.setOwners(owners);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setAfter(45000);
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setCar(car);
        List<PriceHistory> priceHistories = new ArrayList<>();
        priceHistories.add(priceHistory);
        post.setPriceHistories(priceHistories);
        Optional<Post> optionalPost = Optional.of(post);
        Optional<Car> optionalCar = Optional.of(car);
        when(simplePostService.findById(id)).thenReturn(optionalPost);
        when(simpleCarService.findById(any(Integer.class))).thenReturn(optionalCar);
        String expected = "post/post";
        String page = postController.openPost(model, httpSession, id);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("priceHistories", List.of(priceHistory));
        verify(model).addAttribute("price", priceHistory.getAfter());
        verify(model).addAttribute("owners", owners);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenPostWithEmptyCarThenNotSuccess() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        int id = 1;
        Car car = new Car();
        car.setId(1);
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setAfter(45000);
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setCar(car);
        List<PriceHistory> priceHistories = new ArrayList<>();
        priceHistories.add(priceHistory);
        post.setPriceHistories(priceHistories);
        Optional<Post> optionalPost = Optional.of(post);
        Optional<Car> optionalCar = Optional.empty();
        when(simplePostService.findById(id)).thenReturn(optionalPost);
        when(simpleCarService.findById(any(Integer.class))).thenReturn(optionalCar);
        String expected = "404";
        String page = postController.openPost(model, httpSession, id);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("priceHistories", List.of(priceHistory));
        verify(model).addAttribute("price", priceHistory.getAfter());
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenOpenPostWithEmptyPostThenNotSuccess() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        int id = 1;
        Optional<Post> optionalPost = Optional.empty();
        when(simplePostService.findById(id)).thenReturn(optionalPost);
        String expected = "404";
        String page = postController.openPost(model, httpSession, id);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void addPost() {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        Model model = mock(Model.class);
        Post post = new Post();
        Engine engine = new Engine();
        engine.setName("diesel");
        User user = new User();
        user.setLogin("Гость");
        List<Engine> engines = List.of(engine);
        when(simpleEngineService.findAll()).thenReturn(engines);
        String expected = "post/addPost";
        String page = postController.addPost(model, httpSession);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("engines", engines);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreatePostThenNotSuccess() throws IOException {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        MultipartFile file = mock(MultipartFile.class);
        int engineId = 1;
        int price = 1;
        String driverName = "driver";
        Post post = new Post();
        Optional<Engine> optionalEngine = Optional.empty();
        when(simpleEngineService.findById(engineId)).thenReturn(optionalEngine);
        String expected = "redirect:/formAddPost";
        String page = postController.createPost(
                post, httpSession, engineId, price, driverName, file
        );
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenCreatePostThenSuccess() throws IOException {
        PostService simplePostService =
                mock(SimplePostService.class);
        CarService simpleCarService =
                mock(SimpleCarService.class);
        EngineService simpleEngineService =
                mock(SimpleEngineService.class);
        PriceHistoryService simplePriceHistoryService =
                mock(SimplePriceHistoryService.class);
        DriverService simpleDriverService =
                mock(SimpleDriverService.class);
        PostController postController = new PostController(
                simplePostService,
                simpleCarService,
                simpleEngineService,
                simplePriceHistoryService,
                simpleDriverService
        );
        HttpSession httpSession = mock(HttpSession.class);
        MultipartFile file = mock(MultipartFile.class);
        int engineId = 1;
        int price = 50000;
        String driverName = "driver";
        Engine engine = new Engine();
        engine.setId(1);
        engine.setName("diesel");
        Driver driver = new Driver();
        driver.setName(driverName);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setId(1);
        post.setDescription("Продам авто");
        post.setCar(car);
        Optional<Engine> optionalEngine = Optional.of(engine);
        Optional<Driver> optionalDriver = Optional.of(driver);
        when(simpleEngineService.findById(engineId)).thenReturn(optionalEngine);
        when(simpleDriverService.findByUser(any(User.class))).thenReturn(optionalDriver);
        String expected = "redirect:/openPost/" + post.getId();
        String page = postController.createPost(
                post, httpSession, engineId, price, driverName, file
        );
        assertThat(page).isEqualTo(expected);
    }
}