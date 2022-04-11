package com.gozilla.api.controller;

import com.gozilla.api.Adapter.LoginUsuario;
import com.gozilla.api.model.Filme;
import com.gozilla.api.model.Usuario;
import com.gozilla.api.repository.FilmesRepository;
import com.gozilla.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    public UsuarioRepository repository;

    @Autowired
    public FilmesRepository filmeRepository;


    private final PasswordEncoder encoder;

    public UsuarioController(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping()
    public ResponseEntity getUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @PostMapping("/usuario")
    public ResponseEntity postUsuario(@RequestBody @Valid Usuario usuario) {
        Optional<Usuario> usuarioOptional = repository.findUsuarioByEmail(usuario.getEmail());
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(400).build();
        }
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        usuario.setToken(token);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);

        return ResponseEntity.status(201).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity loginUsuario(@RequestBody @Valid LoginUsuario loginUsuario) {
        Optional<Usuario> usuarioOptional = repository.findUsuarioByEmail(loginUsuario.getEmail());

        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            String senhaUsuario = usuario.getSenha();
            if(!encoder.matches(loginUsuario.getSenha(), senhaUsuario)) {
                return ResponseEntity.status(403).build();
            }
            if(usuario.getToken() != null) {
                return ResponseEntity.status(202).build();
            }
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();
            usuario.setToken(token);
            repository.save(usuario);
            return ResponseEntity.status(200).body(usuario);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/expirar/{token}")
    public ResponseEntity expirarToken(@PathVariable String token) {
        Optional<Usuario> usuarioOptional = repository.findUsuarioByToken(token);
        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setToken(null);
            repository.save(usuario);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }


}
