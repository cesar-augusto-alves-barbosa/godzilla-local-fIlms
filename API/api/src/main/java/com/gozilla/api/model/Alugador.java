package com.gozilla.api.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Alugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alugadorId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @NotNull
    private LocalDate dataAluguel;

    public Integer getAlugadorId() {
        return alugadorId;
    }

    public void setAlugadorId(Integer alugadorId) {
        this.alugadorId = alugadorId;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Filme getFilme() {
        return filme;
    }


}
