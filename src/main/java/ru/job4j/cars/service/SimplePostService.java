package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private PostRepository hibernatePostRepository;

    public Optional<Post> add(Post post) {
        return hibernatePostRepository.add(post);
    }

    public Optional<Post> findById(int id) {
        return hibernatePostRepository.findById(id);
    }

    public List<Post> findAll() {
        return hibernatePostRepository.findAll();
    }

    public List<Post> findByLastDay() {
        return hibernatePostRepository.findByLastDay();
    }

    public List<Post> findByAvailabilityPhoto() {
        return hibernatePostRepository.findByAvailabilityPhoto();
    }

    public List<Post> findByBrand(String brand) {
        return hibernatePostRepository.findByBrand(brand);
    }

    public void updateDescription(int id, String description) {
        hibernatePostRepository.updateDescription(id, description);
    }

    public void changeStatus(int id, boolean sold) {
        hibernatePostRepository.changeStatus(id, sold);
    }

    public void update(Post post) {
        hibernatePostRepository.update(post);
    }
}
