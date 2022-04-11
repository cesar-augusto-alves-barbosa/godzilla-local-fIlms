package com.gozilla.api.repository;

import com.gozilla.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByToken(String token);

    Optional<Usuario> findUsuarioByEmail(String email);
}
