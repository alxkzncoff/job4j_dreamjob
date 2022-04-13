package ru.job4j.dreamjob.persistence;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    private PostStore() {

    }

    public static PostStore instOf() {
        return INST;
    }

    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.putIfAbsent(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}