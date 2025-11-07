
package br.com.fiap.sistemahc.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Consulta implements Serializable {
    private String id;
    private LocalDate dataConsulta;
    private String especialidade;
    private String medico;
    private String descricao;
    private String usuarioId;

    public Consulta() { }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDataConsulta() { return dataConsulta; }
    public void setDataConsulta(LocalDate dataConsulta) { this.dataConsulta = dataConsulta; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}
