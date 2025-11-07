
package br.com.fiap.sistemahc.model;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;

public class Usuario implements Serializable {
    private String id;

    @NotBlank
    private String nome;

    private String cpf;

    @Email
    private String email;

    private String telefone;


    public Usuario() { }

    public Usuario(String id, String nome, String cpf, String email, String telefone, LocalDate dataNascimento) {
        this.id = id; this.nome = nome; this.cpf = cpf; this.email = email; this.telefone = telefone;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

}
