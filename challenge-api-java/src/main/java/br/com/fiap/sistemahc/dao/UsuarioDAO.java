package br.com.fiap.sistemahc.dao;

import br.com.fiap.sistemahc.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {
    @Inject
    DataSource dataSource;

    public void salvar(Usuario u) throws SQLException {
        String sql = "INSERT INTO USUARIOS (NOME, CPF, EMAIL, TELEFONE) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            System.out.println("Salvando usuário: " + u.getCpf() + " (" + u.getCpf().getClass() + ")");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setId(rs.getString(1));
                }
            }
        }
    }

    public Usuario buscar(String id) throws SQLException {
        String sql = "SELECT ID_USUARIO, NOME, CPF, EMAIL, TELEFONE, DATA_NASCIMENTO FROM USUARIOS WHERE ID_USUARIO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getString("ID_USUARIO"));
                    u.setNome(rs.getString("NOME"));
                    u.setCpf(rs.getString("CPF"));
                    u.setEmail(rs.getString("EMAIL"));
                    u.setTelefone(rs.getString("TELEFONE"));
                    Date d = rs.getDate("DATA_NASCIMENTO");
                    return u;
                }
            }
        }
        return null;
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT ID_USUARIO, NOME, CPF, EMAIL, TELEFONE FROM USUARIOS";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getString("ID_USUARIO"));
                u.setNome(rs.getString("NOME"));
                u.setCpf(rs.getString("CPF"));
                u.setEmail(rs.getString("EMAIL"));
                u.setTelefone(rs.getString("TELEFONE"));
                lista.add(u);
            }
        }
        return lista;
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE USUARIOS SET NOME = ?, CPF = ?, EMAIL = ?, TELEFONE = ? WHERE ID_USUARIO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            ps.setString(5, u.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(String id) throws SQLException {
        String sql = "DELETE FROM USUARIOS WHERE ID_USUARIO = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public boolean existsByEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(1) FROM USUARIOS WHERE EMAIL = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean existsByCpf(String cpf) throws SQLException {
        String sql = "SELECT COUNT(1) FROM USUARIOS WHERE CPF = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}