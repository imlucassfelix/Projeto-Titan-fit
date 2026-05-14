package entities;

/**
 * Representa um contrato de fidelidade que pode ser vinculado a um aluno
 * no momento da adesao a um plano.
 *
 * <p>A fidelidade define um periodo minimo de permanencia e concede
 * um desconto fixo ({@code valorDesconto}) sobre o valor do plano
 * escolhido. O desconto e aplicado no momento do cadastro do aluno.</p>
 *
 * <p>Alteracao v2.0: fidelidade agora e vinculada ao aluno (via
 * {@link Status}), e nao mais ao plano.</p>
 *
 * @author Lucas Felix
 * @version 2.0
 * @see Status
 * @see repositoryDB.FidelidadeDB
 */

import java.time.LocalDate;

public class Fidelidade {
	private int codFidelidade;
	private String descricao;
	private LocalDate periodo;
	private double valorDesconto; // Desconto fixo em reais concedido ao aluno

	public Fidelidade() {
	}

	public Fidelidade(int codFidelidade, String descricao, LocalDate periodo, double valorDesconto) {
		this.codFidelidade = codFidelidade;
		this.descricao = descricao;
		this.periodo = periodo;
		this.valorDesconto = valorDesconto;
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

	public double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(double valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	@Override
	public String toString() {
		return "Fidelidade [codFidelidade=" + codFidelidade
				+ ", descricao=" + descricao
				+ ", periodo=" + periodo
				+ ", valorDesconto=R$ " + String.format("%.2f", valorDesconto) + "]";
	}
}