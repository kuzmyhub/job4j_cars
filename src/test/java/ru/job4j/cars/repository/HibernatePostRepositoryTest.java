package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HibernatePostRepositoryTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void createSessionFactory() {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterEach
    public void cleanTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User")
                .list();
        for (User u : users) {
            u.getParticipates().clear();
            session.merge(u);
        }
        session.flush();
        session.createQuery("DELETE FROM PriceHistory")
                .executeUpdate();
        session.createQuery("DELETE FROM Post")
                .executeUpdate();
        List<Car> cars = session.createQuery("FROM Car")
                .list();
        for (Car c : cars) {
            c.getOwners().clear();
            session.merge(c);
        }
        session.flush();
        session.createQuery("DELETE FROM Driver")
                .executeUpdate();
        session.createQuery("DELETE FROM User")
                .executeUpdate();
        session.createQuery("DELETE FROM Car")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddPostThenSetPostId() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        hibernatePostRepository.add(post);
        int notExpected = 0;
        assertThat(notExpected).isNotEqualTo(post.getId());
    }

    @Test
    public void whenAddPostThenGetSomePostById() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        hibernatePostRepository.add(post);
        Post dbPost = hibernatePostRepository
                .findById(post.getId())
                .get();
        assertThat(post.getDescription())
                .isEqualTo(dbPost.getDescription());
        assertThat(post.getCar().getBrand())
                .isEqualTo(dbPost.getCar().getBrand());
        assertThat(post.getCar().getModel())
                .isEqualTo(dbPost.getCar().getModel());
        assertThat(post.getPriceHistories().get(0).getBefore())
                .isEqualTo(dbPost.getPriceHistories().get(0).getBefore());
        assertThat(post.getPriceHistories().get(0).getAfter())
                .isEqualTo(dbPost.getPriceHistories().get(0).getAfter());
    }

    @Test
    public void whenAddPostsThenGetSomePosts() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);

        User firstUser = new User();
        firstUser.setLogin("firstUser");
        firstUser.setPassword("firstPassword");
        PriceHistory firstPriceHistory = new PriceHistory();
        firstPriceHistory.setBefore(50000);
        firstPriceHistory.setBefore(45000);
        Car firstCar = new Car();
        firstCar.setBrand("СМЗ");
        firstCar.setModel("С-3Д");
        Post firstPost = new Post();
        firstPost.setDescription("Продам авто");
        firstPost.setUser(firstUser);
        firstPost.setPriceHistories(List.of(firstPriceHistory));
        firstPost.setCar(firstCar);
        hibernatePostRepository.add(firstPost);
        Post firstDbPost = hibernatePostRepository
                .findById(firstPost.getId())
                .get();

        User secondUser = new User();
        secondUser.setLogin("secondUser");
        secondUser.setPassword("secondPassword");
        PriceHistory secondPriceHistory = new PriceHistory();
        secondPriceHistory.setBefore(60000);
        secondPriceHistory.setBefore(55000);
        Car secondCar = new Car();
        secondCar.setBrand("ГАЗ");
        secondCar.setModel("21");
        Post secondPost = new Post();
        secondPost.setDescription("Не продам авто");
        secondPost.setUser(secondUser);
        secondPost.setPriceHistories(List.of(secondPriceHistory));
        secondPost.setCar(secondCar);
        hibernatePostRepository.add(secondPost);
        Post secondDbPost = hibernatePostRepository
                .findById(secondPost.getId())
                .get();

        assertThat(firstPost.getDescription())
                .isEqualTo(firstDbPost.getDescription());
        assertThat(firstPost.getCar().getBrand())
                .isEqualTo(firstDbPost.getCar().getBrand());
        assertThat(firstPost.getCar().getModel())
                .isEqualTo(firstDbPost.getCar().getModel());
        assertThat(firstPost.getPriceHistories().get(0).getBefore())
                .isEqualTo(firstDbPost.getPriceHistories().get(0).getBefore());
        assertThat(firstPost.getPriceHistories().get(0).getAfter())
                .isEqualTo(firstDbPost.getPriceHistories().get(0).getAfter());

        assertThat(secondPost.getDescription())
                .isEqualTo(secondDbPost.getDescription());
        assertThat(secondPost.getCar().getBrand())
                .isEqualTo(secondDbPost.getCar().getBrand());
        assertThat(secondPost.getCar().getModel())
                .isEqualTo(secondDbPost.getCar().getModel());
        assertThat(secondPost.getPriceHistories().get(0).getBefore())
                .isEqualTo(secondDbPost.getPriceHistories().get(0).getBefore());
        assertThat(secondPost.getPriceHistories().get(0).getAfter())
                .isEqualTo(secondDbPost.getPriceHistories().get(0).getAfter());
    }

    @Test
    public void whenAddPostThenGetSomePostByLasDay() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        hibernatePostRepository.add(post);
        List<Post> dbPosts = hibernatePostRepository
                .findByLastDay();
        assertThat(post.getDescription())
                .isEqualTo(dbPosts.get(0).getDescription());
        assertThat(post.getCar().getBrand())
                .isEqualTo(dbPosts.get(0).getCar().getBrand());
        assertThat(post.getCar().getModel())
                .isEqualTo(dbPosts.get(0).getCar().getModel());
        assertThat(post.getPriceHistories().get(0).getBefore())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getBefore());
        assertThat(post.getPriceHistories().get(0).getAfter())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getAfter());
    }

    @Test
    public void whenAddPostThenGetSomePostByBrand() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        hibernatePostRepository.add(post);
        List<Post> dbPosts = hibernatePostRepository
                .findByBrand("СМЗ");
        assertThat(post.getDescription())
                .isEqualTo(dbPosts.get(0).getDescription());
        assertThat(post.getCar().getBrand())
                .isEqualTo(dbPosts.get(0).getCar().getBrand());
        assertThat(post.getCar().getModel())
                .isEqualTo(dbPosts.get(0).getCar().getModel());
        assertThat(post.getPriceHistories().get(0).getBefore())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getBefore());
        assertThat(post.getPriceHistories().get(0).getAfter())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getAfter());
    }

    @Test
    public void whenAddPostThenGetSomePostByAvailabilityPhoto() throws IOException {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        File file = new File("src/main/resources/static/images/СМЗ.jpg");
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageInByte = baos.toByteArray();
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        post.setPhoto(imageInByte);
        hibernatePostRepository.add(post);
        List<Post> dbPosts = hibernatePostRepository
                .findByBrand("СМЗ");
        assertThat(post.getDescription())
                .isEqualTo(dbPosts.get(0).getDescription());
        assertThat(post.getCar().getBrand())
                .isEqualTo(dbPosts.get(0).getCar().getBrand());
        assertThat(post.getCar().getModel())
                .isEqualTo(dbPosts.get(0).getCar().getModel());
        assertThat(post.getPriceHistories().get(0).getBefore())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getBefore());
        assertThat(post.getPriceHistories().get(0).getAfter())
                .isEqualTo(dbPosts.get(0).getPriceHistories().get(0).getAfter());
        assertThat(post.getPhoto())
                .isEqualTo(dbPosts.get(0).getPhoto());
    }

    @Test
    public void whenUpdatePostDescriptionThenGetModifiedPostDescription() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        hibernatePostRepository.add(post);
        String expected = "Не продам авто";
        hibernatePostRepository.updateDescription(
                post.getId(), expected
        );
        Post dbPost = hibernatePostRepository
                .findById(post.getId())
                .get();
        assertThat(expected).isEqualTo(dbPost.getDescription());
    }

    @Test
    public void thenUpdatePostStatusWhenGetModifiedPostStatus() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PostRepository hibernatePostRepository =
                new HibernatePostRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        hibernatePostRepository.add(post);
        boolean expected = true;
        hibernatePostRepository.changeStatus(post.getId(), expected);
        Post dbPost = hibernatePostRepository
                .findById(post.getId())
                .get();
        assertThat(expected).isEqualTo(dbPost.isSold());
    }
}