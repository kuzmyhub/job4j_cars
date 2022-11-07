package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repostory.PostRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class PostService {

    private PostRepository store;

    public Post add(Post post) {
        return store.add(post);
    }

    public Optional<Post> findById(int id) {
        return store.findById(id);
    }

    public List<Post> findAll() {
        return store.findAll();
    }

    public List<Post> findByLastDay() {
        return store.findByLastDay();
    }

    public List<Post> findByAvailabilityPhoto() {
        return store.findByAvailabilityPhoto();
    }

    public List<Post> findByBrand(String brand) {
        return store.findByBrand(brand);
    }

}
