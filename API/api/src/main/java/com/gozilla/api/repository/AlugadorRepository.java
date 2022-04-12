package com.gozilla.api.repository;

import com.gozilla.api.model.Alugador;
import com.gozilla.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlugadorRepository  extends JpaRepository<Alugador, Integer> {

    Optional<Alugador> findByUsuarioId(Integer id);
}
