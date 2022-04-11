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

    @GetMapping("/filmes/{token}")
    public ResponseEntity getFilmes(@PathVariable String token) {
        Optional<Usuario> usuarioOptional = userRepository.findUsuarioByToken(token);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(403).build();
        }
        List<Filme> filmes = repository.findAll();
        if (filmes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(filmes);
    }

    @PostMapping("/godzilla/{id}/{token}")
    public ResponseEntity alugarFilme(@PathVariable Integer id, @PathVariable String token) {
        Optional<Usuario> usuarioOptional = userRepository.findUsuarioByToken(token);
        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
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
                    return ResponseEntity.status(200).build();
                }
                return ResponseEntity.status(404).build();
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

    @DeleteMapping("/devolver/{token}")
    public ResponseEntity devolverFilme(@PathVariable String token) {
        Optional<Usuario> usuarioOptional = userRepository.findUsuarioByToken(token);
        if(usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getFilmeAlugado() == null) {
                return ResponseEntity.status(202).build();
            }
            Integer estoqueFilme = usuario.getFilmeAlugado().getEstoque();
            Filme filme = usuario.getFilmeAlugado();
            filme.setEstoque(estoqueFilme+1);
            repository.save(filme);
            usuario.setFilmeAlugado(null);
            userRepository.save(usuario);

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
