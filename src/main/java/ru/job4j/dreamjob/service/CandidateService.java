package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.ArrayList;
import java.util.List;

public class CandidateService {

    private final CandidateStore store = CandidateStore.instOf();

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public List<Candidate> findAll() {
        return new ArrayList<>(store.findAll());
    }
 }
