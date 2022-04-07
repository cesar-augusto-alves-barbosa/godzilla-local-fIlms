package com.gozilla.api.controller;


import com.gozilla.api.model.Filme;
import com.gozilla.api.model.Usuario;
import com.gozilla.api.repository.FilmesRepository;
import com.gozilla.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class FilmeController {

    @Autowired
    public FilmesRepository repository;

    @Autowired
    public UsuarioRepository userRepository;

    @GetMapping("/filmes")
    public ResponseEntity getFilmes() {
        List<Filme> filmes = repository.findAll();
        if (filmes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(filmes);
    }

    @PutMapping("/godzilla/{id}/{token}")
    public ResponseEntity alugarFilme(@PathVariable Integer id, @PathVariable String token) {
        Usuario usuario = userRepository.findUsuarioByToken(token);
        if(usuario != null) {
            if(usuario.getFilmeAlugado() == null) {
                Optional<Filme> filmeOptional = repository.findById(id);

                if(filmeOptional.isPresent()) {
                    Filme filme = filmeOptional.get();
                    if(filme.getEstoque() == 0) {
                        return ResponseEntity.status(403).build();
                    }
                    filme.setEstoque(filme.getEstoque()-1);
                    usuario.setFilmeAlugado(filme);
                    repository.save(filme);
                    userRepository.save(usuario);
                    return ResponseEntity.status(200).body(filme);
                }
                return ResponseEntity.status(204).build();

            }

        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/locadora/{titulo}")
    public ResponseEntity alugaFilme(@PathVariable String titulo) {
        List<Filme> filmes = repository.findByAnyTitulo(titulo.toLowerCase());
        if (filmes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(filmes);
    }
}
