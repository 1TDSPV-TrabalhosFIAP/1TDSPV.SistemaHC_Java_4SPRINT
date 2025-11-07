package br.com.fiap.sistemahc.service;


import br.com.fiap.sistemahc.dao.UsuarioDAO;
import br.com.fiap.sistemahc.exception.ResourceNotFoundException;
import br.com.fiap.sistemahc.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UsuarioService {
    @Inject
    UsuarioDAO dao;

    public List<Usuario> listar() {
        try { return dao.listar(); } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Usuario buscar(String id) {
        try {
            Usuario u = dao.buscar(id);
            if (u == null) throw new ResourceNotFoundException("Usuario não encontrado");
            return u;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public Usuario criar(Usuario u) {
        try {
            if (u.getEmail() != null && dao.existsByEmail(u.getEmail())) throw new IllegalArgumentException("Email já cadastrado");
            if (u.getCpf() != null && dao.existsByCpf(u.getCpf())) throw new IllegalArgumentException("CPF já cadastrado");
            dao.salvar(u);
            return u;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public Usuario atualizar(String id, Usuario dados) {
        try {
            Usuario existente = dao.buscar(id);
            if (existente == null) throw new ResourceNotFoundException("Usuario não encontrado");
            existente.setNome(dados.getNome());
            existente.setCpf(dados.getCpf());
            existente.setEmail(dados.getEmail());
            existente.setTelefone(dados.getTelefone());
            dao.atualizar(existente);
            return existente;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public void deletar(String id) {
        try {
            Usuario existente = dao.buscar(id);
            if (existente == null) throw new ResourceNotFoundException("Usuario não encontrado");
            dao.deletar(id);
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}

