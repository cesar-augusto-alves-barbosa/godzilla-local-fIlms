package com.gozilla.api.repository;

import com.gozilla.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findUsuarioByToken(String token);

    Usuario findUsuarioByEmail(String email);
}
