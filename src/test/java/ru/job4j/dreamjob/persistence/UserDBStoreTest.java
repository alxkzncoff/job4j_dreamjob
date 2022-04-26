package ru.job4j.dreamjob.persistence;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserDBStoreTest {

    @Test
    public void whenRegistrationSuccess() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        User user = new User("user", "password");
        assertThat(store.add(user).orElse(null), is(user));
    }

    @Test
    public void whenRegistrationFail() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        User user = new User("user1", "password");
        store.add(user);
        assertTrue(store.add(user).isEmpty());
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = new Main().loadPool().getConnection()
                .prepareStatement("DELETE FROM users")) {
            statement.execute();
        }
    }

}