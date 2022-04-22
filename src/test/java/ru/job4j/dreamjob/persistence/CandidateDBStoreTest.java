package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {
    @Test
    public void whenCreatePost() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Junior", "Java", "today", new byte[]{1, 2, 3});
        store.add(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Junior", "Java", "today", new byte[]{1, 2, 3});
        store.add(candidate);
        candidate.setName("Middle");
        candidate.setDescription("C++");
        store.update(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

}