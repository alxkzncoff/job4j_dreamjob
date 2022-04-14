package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс-хранилище кандидатов.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@ThreadSafe
@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public CandidateStore() {

    }

    /**
     * Метод добавляет нового кандидата в хранилище,
     * если вакансии с таким id еще нет.
     * @param candidate вакансия.
     */
    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.putIfAbsent(candidate.getId(), candidate);
    }

    /**
     * Метод ищет кандидата в хранилище по id.
     * @param id идентификационный номер вакансии.
     * @return Candidate кандидата.
     */
    public Candidate findById(int id) {
        return candidates.get(id);
    }

    /**
     * Метод обновляет данные кандидата.
     * @param candidate вакансия с новыми данными.
     */
    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }

    /**
     * Метод возврщает всех имеющихся в хранилище кандидатов.
     * @return Collection<Post> вакансии.
     */
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
