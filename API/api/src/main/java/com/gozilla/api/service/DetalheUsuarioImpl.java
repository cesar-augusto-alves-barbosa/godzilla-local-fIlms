package com.gozilla.api.service;

import com.gozilla.api.data.DetalheUsuario;
import com.gozilla.api.model.Usuario;
import com.gozilla.api.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuarioImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public DetalheUsuarioImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findUsuarioByEmail(username);
        if(!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuário [" + username +"] não encontrado");
        }
        return new DetalheUsuario(usuario);
    }
}
