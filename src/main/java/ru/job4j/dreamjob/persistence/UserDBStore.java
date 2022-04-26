package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Класс-хранилще пользователей в БД.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@Repository
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод добавляет пользователя в БД.
     * @param user Пользователь.
     * @return Optional<User> пользователь.
     */
    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, password) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }

    /**
     * Метод ищет пользователя по имени и паролю.
     * @param name Имя пользователя.
     * @param password Пароль пользователя.
     * @return Optional<User> пользователь.
     */
    public Optional<User> findUserByNameAndPwd(String name, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE name = ? and password = ?")
        ) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(new User(it.getString("name"),
                            it.getString("password")));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }
}
