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
@RequestMapping("/alugador")
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
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String emailUsuario;
            if (principal instanceof DetalheUsuario) {
                emailUsuario = ((DetalheUsuario)principal).getUsername();
            } else {
                emailUsuario = principal.toString();
            }
            Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByEmail(emailUsuario);

            if(!usuarioOptional.isPresent()) {
                return ResponseEntity.status(401).build();
            }
            Optional<Alugador> usuarioAlugador = repository.findByUsuarioId(usuarioOptional.get().getId());
            if(usuarioAlugador.isPresent()) {
                return ResponseEntity.status(403).body("Você já possui um filme alugado, " +
                        "devolva seu filme para alugar outro.");
            }
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
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/devolver/")
    public ResponseEntity devolverFilme() {
        return ResponseEntity.status(404).build();
    }
}
