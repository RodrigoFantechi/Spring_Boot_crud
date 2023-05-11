package com.test.crud.repository;

import com.test.crud.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdNot(String name, Integer id);
}
