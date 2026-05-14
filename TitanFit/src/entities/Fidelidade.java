package entities;

/**
 * Representa um contrato de fidelidade associado a um {@link Plano}.
 *
 * <p>Define o periodo de vigencia minima do plano. Um aluno que contrata
 * um plano com fidelidade assume o compromisso de permanencia ate a data
 * registrada em {@code periodo}.</p>
 *
 * @author Lucas Felix
 * @version 1.0
 * @see Plano
 * @see repositoryDB.FidelidadeDB
 */

import java.time.LocalDate;

public class Fidelidade {
	private int codFidelidade;
	private String descricao;
	private LocalDate periodo;
	
	public Fidelidade() {
	}

	public Fidelidade(int codFidelidade, String descricao, LocalDate periodo) {
		this.codFidelidade = codFidelidade;
		this.descricao = descricao;
		this.periodo = periodo;
	}

	public int getCodFidelidade() {
		return codFidelidade;
	}

	public void setCodFidelidade(int codFidelidade) {
		this.codFidelidade = codFidelidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getPeriodo() {
		return periodo;
	}

	public void setPeriodo(LocalDate periodo) {
		this.periodo = periodo;
	}

	@Override
	public String toString() {
		return "Fidelidade [codFidelidade=" + codFidelidade + ", descricao=" + descricao + ", periodo=" + periodo + "]";
	}
	
	
}
