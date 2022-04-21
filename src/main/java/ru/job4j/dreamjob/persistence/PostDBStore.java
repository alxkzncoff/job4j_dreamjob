package ru.job4j.dreamjob.persistence;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-хранилще вакансий в БД.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@Repository
public class PostDBStore {
    private static final Logger LOG = LogManager.getLogger(PostDBStore.class.getName());

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод добавляет вакансию в БД.
     * @param post вакансия.
     * @return Post добавленная вакансия.
     */
    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO post(name, description, created, city_id, visible) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getCreated());
            ps.setInt(4, post.getCity().getId());
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return post;
    }

    /**
     * Метод обновляет вакансию в БД
     * @param post вакансия.
     */
    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post set name = ?, "
                     + "description = ?, created = ?, city_id = ?, visible = ? where id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getCreated());
            ps.setInt(4, post.getCity().getId());
            ps.setBoolean(5, post.isVisible());
            ps.setInt(6, post.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Exception", e);
        }
    }

    /**
     * Метод ищет вакансию по идентификационному номеру в БД.
     * @param id идентификационный номер.
     * @return Post если вакансия найдена, иначе null.
     */
    public Post findById(int id) {
        Post result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getString("created"),
                            new City(it.getInt("city_id"), null));
                    result.setVisible(it.getBoolean("visible"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }

    /**
     * Метод ищет все вакансии в БД.
     * @return List<Post> список вакансий.
     */
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Post result = new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getString("created"),
                            new City(it.getInt("city_id"), null));
                    result.setVisible(it.getBoolean("visible"));
                    posts.add(result);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return posts;
    }

}
