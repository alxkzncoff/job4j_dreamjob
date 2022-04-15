package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс описывающий вакансии.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
public class Post implements Serializable {
    private int id;
    private final String name;
    private final String description;
    private final String created;
    private boolean visible;
    private City city;

    public Post(int id, String name, String description, String created, City city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
