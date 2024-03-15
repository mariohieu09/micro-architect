package org.example.userservice.service;

import org.example.userservice.entity.IndexableEntity;
import org.example.userservice.repository.BaseIndexEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface IGeneralService<T extends IndexableEntity> {

    Optional<T> getById(Long id);

    T create(T entity);

    Iterable<T> getAll();

    T update(T entity);

    void deleteById(Long id);


}
