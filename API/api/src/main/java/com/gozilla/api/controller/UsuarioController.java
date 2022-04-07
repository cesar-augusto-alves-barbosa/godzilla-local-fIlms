package com.gozilla.api.controller;

import com.gozilla.api.model.Filme;
import com.gozilla.api.model.Usuario;
import com.gozilla.api.repository.FilmesRepository;
import com.gozilla.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    public UsuarioRepository repository;

    @Autowired
    public FilmesRepository filmeRepository;

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
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        usuario.setToken(token);
        repository.save(usuario);

        return ResponseEntity.status(201).body(usuario);
    }

    @DeleteMapping("/devolver/{token}")
    public ResponseEntity devolverFilme(@PathVariable String token) {
        Usuario usuario = repository.findUsuarioByToken(token);
        if (usuario.getFilmeAlugado() == null) {
            return ResponseEntity.status(202).build();
        }
        Integer estoqueFilme = usuario.getFilmeAlugado().getEstoque();
        Filme filme = usuario.getFilmeAlugado();
        filme.setEstoque(estoqueFilme+1);
        filmeRepository.save(filme);
        usuario.setFilmeAlugado(null);
        repository.save(usuario);

        return ResponseEntity.status(200).body(usuario);
    }
}
