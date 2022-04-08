package com.gozilla.api.Adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gozilla.api.model.Filme;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUsuario {

    @NotNull
    @Email
    private String email;

    @Size(min = 8, max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
