package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        posts.put(1, new Post(1, "Vacancy", "Junior Java Job", LocalDateTime.now().format(formatter)));
        posts.put(2, new Post(2, "Vacancy", "Middle Java Job", LocalDateTime.now().format(formatter)));
        posts.put(3, new Post(3, "Vacancy", "Senior Java Job", LocalDateTime.now().format(formatter)));
    }

    public static PostStore instOf() {
        return INST;
    }

    public void add(Post post) {
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
