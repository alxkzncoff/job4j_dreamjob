package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDBStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class PostService {

    private final PostDBStore store;
    private final CityService cityService;

    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public void add(Post post) {
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>(store.findAll());
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }
}
