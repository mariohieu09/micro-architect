package org.example.userservice.repository;

import org.example.userservice.entity.IndexableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseIndexEntityRepo<T extends IndexableEntity> extends JpaRepository<T, Long> {


}

