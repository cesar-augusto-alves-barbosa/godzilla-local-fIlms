package com.gozilla.api.controller;


import com.gozilla.api.data.DetalheUsuario;
import com.gozilla.api.model.Alugador;
import com.gozilla.api.model.Filme;
import com.gozilla.api.model.Usuario;
import com.gozilla.api.repository.AlugadorRepository;
import com.gozilla.api.repository.FilmesRepository;
import com.gozilla.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugadores")
@CrossOrigin
public class AlugadorController {

    @Autowired
    private AlugadorRepository repository;

    @Autowired
    public FilmesRepository filmesRepository;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity getAlugadorById(@PathVariable Integer id) {
        Optional<Alugador> alugador = repository.findById(id);
        if (!alugador.isPresent()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(alugador);
    }

    @PostMapping("/alugar/{idFilme}")
    public ResponseEntity alugarFilme(@PathVariable Integer idFilme) {
        Optional<Filme> filmeOptional = filmesRepository.findById(idFilme);
        if(filmeOptional.isPresent()) {
            Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String emailUsuario;
            Boolean usuarioAutenticado = usuarioLogado instanceof DetalheUsuario;
            emailUsuario = usuarioAutenticado ? ((DetalheUsuario) usuarioLogado).getUsername() : usuarioLogado.toString();
            Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(emailUsuario);
            if(!usuarioOptional.isPresent()) {
                return ResponseEntity.status(401).build();
            } else {
                Optional<Alugador> usuarioAlugador = repository.findByUsuarioId(usuarioOptional.get().getId());
                if(!usuarioAlugador.isPresent()) {
                    Usuario usuario = usuarioOptional.get();
                    Filme filme = filmeOptional.get();
                    filme.setEstoque(filme.getEstoque() - 1);
                    Alugador alugador = new Alugador();
                    alugador.setFilme(filme);
                    alugador.setUsuario(usuario);
                    alugador.setDataAluguel(LocalDate.now());
                    filmesRepository.save(filme);
                    repository.save(alugador);
                    return ResponseEntity.status(201).build();
                }
                return ResponseEntity.status(403).body("Você já possui um filme alugado, " +
                        "devolva seu filme para alugar outro.");
            }
        }
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/devolver/")
    public ResponseEntity devolverFilme() {
        Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuario;
        Boolean usuarioAutenticado = usuarioLogado instanceof DetalheUsuario;
        emailUsuario = usuarioAutenticado ? ((DetalheUsuario) usuarioLogado).getUsername() : usuarioLogado.toString();
        if(emailUsuario == null) {
            return ResponseEntity.status(403).build();
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(emailUsuario);
        Optional<Alugador> usuarioAlugadorOptional = repository.findByUsuarioId(usuarioOptional.get().getId());
        if(!usuarioOptional.isPresent()) {
            return ResponseEntity.status(401).build();
        }
        if(!usuarioAlugadorOptional.isPresent()) {
            return ResponseEntity.status(204).build();
        }
        Alugador usuarioAlugador = usuarioAlugadorOptional.get();
        Filme filmeAlugado = filmesRepository.findById(usuarioAlugador.getFilme().getId()).get();
        filmeAlugado.setEstoque(filmeAlugado.getEstoque() + 1);
        filmesRepository.save(filmeAlugado);
        repository.delete(usuarioAlugador);
        return ResponseEntity.status(200).build();
    }
}
