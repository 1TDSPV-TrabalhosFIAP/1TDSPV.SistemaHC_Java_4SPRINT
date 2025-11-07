package br.com.fiap.sistemahc.dao;

import br.com.fiap.sistemahc.model.Consulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDAO {
    @Inject
    DataSource dataSource;

    public void salvar(Consulta c) throws SQLException {
        String sql = "INSERT INTO CONSULTAS (DATA_CONSULTA, ESPECIALIDADE, MEDICO, DESCRICAO, USUARIO_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (c.getDataConsulta() != null) ps.setDate(1, Date.valueOf(c.getDataConsulta())); else ps.setNull(1, Types.DATE);
            ps.setString(2, c.getEspecialidade());
            ps.setString(3, c.getMedico());
            ps.setString(4, c.getDescricao());
            if (c.getUsuarioId() != null) ps.setString(5, c.getUsuarioId()); else ps.setNull(5, Types.BIGINT);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getString(1));
                }
            }
        }
    }

    public Consulta buscar(String id) throws SQLException {
        String sql = "SELECT ID_CONSULTA, DATA_CONSULTA, ESPECIALIDADE, MEDICO, DESCRICAO, USUARIO_ID FROM CONSULTAS WHERE ID_CONSULTA = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Consulta c = new Consulta();
                    c.setId(rs.getString("ID_CONSULTA"));
                    Date d = rs.getDate("DATA_CONSULTA");
                    if (d != null) c.setDataConsulta(d.toLocalDate());
                    c.setEspecialidade(rs.getString("ESPECIALIDADE"));
                    c.setMedico(rs.getString("MEDICO"));
                    c.setDescricao(rs.getString("DESCRICAO"));
                    c.setUsuarioId(rs.getObject("USUARIO_ID") != null ? rs.getString("USUARIO_ID") : null);
                    return c;
                }
            }
        }
        return null;
    }

    public List<Consulta> listar() throws SQLException {
        String sql = "SELECT ID_CONSULTA, DATA_CONSULTA, ESPECIALIDADE, MEDICO, DESCRICAO, USUARIO_ID FROM CONSULTAS";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getString("ID_CONSULTA"));
                Date d = rs.getDate("DATA_CONSULTA");
                if (d != null) c.setDataConsulta(d.toLocalDate());
                c.setEspecialidade(rs.getString("ESPECIALIDADE"));
                c.setMedico(rs.getString("MEDICO"));
                c.setDescricao(rs.getString("DESCRICAO"));
                c.setUsuarioId(rs.getObject("USUARIO_ID") != null ? rs.getString("USUARIO_ID") : null);
                lista.add(c);
            }
        }
        return lista;
    }

    public List<Consulta> findByUsuarioId(String usuarioId) throws SQLException {
        String sql = "SELECT ID_CONSULTA, DATA_CONSULTA, ESPECIALIDADE, MEDICO, DESCRICAO, USUARIO_ID FROM CONSULTAS WHERE USUARIO_ID = ?";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta c = new Consulta();
                    c.setId(rs.getString("ID_CONSULTA"));
                    Date d = rs.getDate("DATA_CONSULTA");
                    if (d != null) c.setDataConsulta(d.toLocalDate());
                    c.setEspecialidade(rs.getString("ESPECIALIDADE"));
                    c.setMedico(rs.getString("MEDICO"));
                    c.setDescricao(rs.getString("DESCRICAO"));
                    c.setUsuarioId(rs.getObject("USUARIO_ID") != null ? rs.getString("USUARIO_ID") : null);
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public void atualizar(Consulta c) throws SQLException {
        String sql = "UPDATE CONSULTAS SET DATA_CONSULTA = ?, ESPECIALIDADE = ?, MEDICO = ?, DESCRICAO = ?, USUARIO_ID = ? WHERE ID_CONSULTA = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (c.getDataConsulta() != null) ps.setDate(1, Date.valueOf(c.getDataConsulta())); else ps.setNull(1, Types.DATE);
            ps.setString(2, c.getEspecialidade());
            ps.setString(3, c.getMedico());
            ps.setString(4, c.getDescricao());
            if (c.getUsuarioId() != null) ps.setString(5, c.getUsuarioId()); else ps.setNull(5, Types.BIGINT);
            ps.setString(6, c.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(String id) throws SQLException {
        String sql = "DELETE FROM CONSULTAS WHERE ID_CONSULTA = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
