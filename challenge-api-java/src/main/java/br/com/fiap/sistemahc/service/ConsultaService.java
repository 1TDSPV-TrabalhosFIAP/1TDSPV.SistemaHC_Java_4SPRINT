package br.com.fiap.sistemahc.service;

import br.com.fiap.sistemahc.dao.ConsultaDAO;
import br.com.fiap.sistemahc.dao.UsuarioDAO;
import br.com.fiap.sistemahc.exception.ResourceNotFoundException;
import br.com.fiap.sistemahc.model.Consulta;
import br.com.fiap.sistemahc.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ConsultaService {
    @Inject
    ConsultaDAO dao;

    @Inject
    UsuarioDAO usuarioDAO;

    public List<Consulta> listar() {
        try { return dao.listar(); } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Consulta buscar(String id) {
        try {
            Consulta c = dao.buscar(id);
            if (c == null) throw new ResourceNotFoundException("Consulta não encontrada");
            return c;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public Consulta criar(Consulta c) {
        try {
            if (c.getDataConsulta() != null && c.getDataConsulta().isBefore(LocalDate.now())) throw new IllegalArgumentException("Data da consulta não pode ser no passado");
            if (c.getUsuarioId() != null) {
                // verifica se usuario existe
                if (usuarioDAO.buscar(c.getUsuarioId()) == null) throw new ResourceNotFoundException("Usuario associado não encontrado");
            }
            dao.salvar(c);
            return c;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public Consulta atualizar(String id, Consulta dados) {
        try {
            Consulta existente = dao.buscar(id);
            if (existente == null) throw new ResourceNotFoundException("Consulta não encontrada");
            existente.setDataConsulta(dados.getDataConsulta());
            existente.setEspecialidade(dados.getEspecialidade());
            existente.setMedico(dados.getMedico());
            existente.setDescricao(dados.getDescricao());
            existente.setUsuarioId(dados.getUsuarioId());
            dao.atualizar(existente);
            return existente;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Transactional
    public void deletar(String id) {
        try { dao.deletar(id); } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Consulta> findByUsuarioId(String usuarioId) {
        try { return dao.findByUsuarioId(usuarioId); } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
