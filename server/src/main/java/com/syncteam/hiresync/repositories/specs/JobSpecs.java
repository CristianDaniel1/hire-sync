package com.syncteam.hiresync.repositories.specs;

import com.syncteam.hiresync.entities.Job;
import com.syncteam.hiresync.entities.enums.WorkMode;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecs {
    public static Specification<Job> titleLike(String title) {
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Job> locationLike(String location) {
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("location")), "%" + location.toUpperCase() + "%");
    }

    public static Specification<Job> workModeEqual(WorkMode workMode) {
        return (root, query, cb) ->
                cb.equal(root.get("workMode"), workMode);
    }
}
