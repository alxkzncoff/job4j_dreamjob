package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс-хранилище вакансий.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    private PostStore() {

    }

    /**
     * Метод добавляет новую вакансию в хранилище,
     * если вакансии с таким id еще нет.
     * @param post вакансия.
     */
    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.putIfAbsent(post.getId(), post);
    }

    /**
     * Метод ищет вакансию в хранилище по id.
     * @param id идентификационный номер вакансии.
     * @return Post вакансию.
     */
    public Post findById(int id) {
        return posts.get(id);
    }

    /**
     * Метод обновляет данные вакансии.
     * @param post вакансия с новыми данными.
     */
    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    /**
     * Метод возврщает все имеющиеся в хранилище вакансии.
     * @return Collection<Post> вакансии.
     */
    public Collection<Post> findAll() {
        return posts.values();
    }
}
