package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class PostService {

    private final PostStore store;
    private final CityService cityService;

    public PostService(PostStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public void add(Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
        store.add(post);
    }

    public void update(Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
        store.update(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public List<Post> findAll() {
        return new ArrayList<>(store.findAll());
    }
}
