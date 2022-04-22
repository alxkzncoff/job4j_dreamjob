package ru.job4j.dreamjob.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс-хранилще кандидатов в БД.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@Repository
public class CandidateDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());

    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод добавляет кандидата в БД.
     * @param candidate кандидат.
     */
    public void add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "INSERT INTO candidate(name, description, created, photo) VALUES (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setString(3, candidate.getCreated());
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    /**
     * Метод обновляет данные кандидата в БД
     * @param candidate вакансия.
     */
    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate set name = ?, "
                     + "description = ?, created = ? where id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setString(3, candidate.getCreated());
            ps.setInt(4, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Exception", e);
        }
    }

    /**
     * Метод ищет кандидата по идентификационному номеру в БД.
     * @param id идентификационный номер.
     * @return Candidate если кандидат найден, иначе null.
     */
    public Candidate findById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = new Candidate(it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getString("created"), it.getBytes("photo"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return result;
    }

    /**
     * Метод ищет всех кандидатов в БД.
     * @return List<Candidate> список кандидатов.
     */
    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Candidate result = new Candidate(it.getInt("id"), it.getString("name"),
                            it.getString("description"), it.getString("created"), it.getBytes("photo"));
                    candidates.add(result);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return candidates;
    }
}
