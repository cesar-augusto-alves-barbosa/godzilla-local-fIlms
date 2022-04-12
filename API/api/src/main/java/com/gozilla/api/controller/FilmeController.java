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
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    public FilmesRepository repository;


    @GetMapping()
    public ResponseEntity getFilmes() {
        List<Filme> filmes = repository.findAll();
        if (filmes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(filmes);
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
