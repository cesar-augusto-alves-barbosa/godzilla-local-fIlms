package com.gozilla.api.repository;

import com.gozilla.api.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmesRepository extends JpaRepository<Filme, Integer> {

    @Query(value = "select * from Filme where LOWER(titulo) like %?1%", nativeQuery = true)
    List<Filme> findByAnyTitulo(String titulo);

}
