package ru.job4j.cars.repostory;

import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostRepository {

     Post add(Post post);

     List<Post> findAll();

     Optional<Post> findById(int id);

     List<Post> findByLastDay();

     List<Post> findByAvailabilityPhoto();

     List<Post> findByBrand(String brand);

     void updateDescription(int id, String description);

     void changeStatus(int id, boolean sold);

     void update(Post post);
}
