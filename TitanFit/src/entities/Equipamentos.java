package entities;

import java.time.LocalDate;

public class Equipamentos {
	private int codMaquina;
	private String nomeEquipamento;
	private String modelo;
	private String estado;
	private LocalDate dataAquisicao;
	
	public Equipamentos() {
	}

	public Equipamentos(int codMaquina, String nomeEquipamento, String modelo, String estado, LocalDate dataAquisicao) {
		this.codMaquina = codMaquina;
		this.nomeEquipamento = nomeEquipamento;
		this.modelo = modelo;
		this.estado = estado;
		this.dataAquisicao = dataAquisicao;
	}

	public int getCodMaquina() {
		return codMaquina;
	}

	public void setCodMaquina(int codMaquina) {
		this.codMaquina = codMaquina;
	}

	public String getNome() {
		return nomeEquipamento;
	}

	public void setNome(String nome) {
		this.nomeEquipamento = nome;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(LocalDate dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	@Override
	public String toString() {
		return "Equipamento [codMaquina=" + codMaquina + ", nomeEquipamento=" + nomeEquipamento + ", modelo=" + modelo + ", estado=" + estado
				+ ", dataAquisicao=" + dataAquisicao + "]";
	}

}
