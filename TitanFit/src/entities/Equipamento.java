package entities;

import java.time.LocalDate;

public class Equipamento {
	private int codMaquina;
	private String nome;
	private String modelo;
	private String estado;
	private LocalDate dataAquisicao;
	
	public Equipamento() {
	}

	public Equipamento(int codMaquina, String nome, String modelo, String estado, LocalDate dataAquisicao) {
		this.codMaquina = codMaquina;
		this.nome = nome;
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
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		return "Equipamento [codMaquina=" + codMaquina + ", nome=" + nome + ", modelo=" + modelo + ", estado=" + estado
				+ ", dataAquisicao=" + dataAquisicao + "]";
	}

}
