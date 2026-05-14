package entities;

/**
 * Registra o vinculo entre um {@link Aluno} e um {@link Plano},
 * podendo incluir uma {@link Fidelidade} com desconto.
 *
 * <p>O campo {@code planoAtivo} indica se o plano esta em vigencia.
 * A verificacao real de vencimento e feita em {@code MetodosInscricao}
 * comparando {@code dataMatricula + duracaoMeses} com a data atual.</p>
 *
 * <p>Alteracao v2.0: adicionado {@code codFidelidade} (nullable).
 * Quando preenchido, indica que o aluno aderiu a uma fidelidade e
 * recebe o desconto fixo definido em {@link Fidelidade#getValorDesconto()}.
 * Valor {@code null} significa que o aluno nao possui fidelidade.</p>
 *
 * @author Mateus Santos
 * @version 2.0
 * @see repositoryDB.StatusDB
 */

public class Status {
	private int codStatus;
	private String cpfAluno;
	private int codPlano;
	private boolean planoAtivo;
	private Integer codFidelidade; // null = sem fidelidade

	public Status() {
	}

	public Status(int codStatus, String cpfAluno, int codPlano, boolean planoAtivo, Integer codFidelidade) {
		this.codStatus = codStatus;
		this.cpfAluno = cpfAluno;
		this.codPlano = codPlano;
		this.planoAtivo = planoAtivo;
		this.codFidelidade = codFidelidade;
	}

	/** Construtor de compatibilidade sem fidelidade. */
	public Status(int codStatus, String cpfAluno, int codPlano, boolean planoAtivo) {
		this(codStatus, cpfAluno, codPlano, planoAtivo, null);
	}

	public int getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(int codStatus) {
		this.codStatus = codStatus;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public int getCodPlano() {
		return codPlano;
	}

	public void setCodPlano(int codPlano) {
		this.codPlano = codPlano;
	}

	public boolean isPlanoAtivo() {
		return planoAtivo;
	}

	public void setPlanoAtivo(boolean planoAtivo) {
		this.planoAtivo = planoAtivo;
	}

	/** Retorna o codigo de fidelidade vinculado, ou {@code null} se nao houver. */
	public Integer getCodFidelidade() {
		return codFidelidade;
	}

	public void setCodFidelidade(Integer codFidelidade) {
		this.codFidelidade = codFidelidade;
	}

	/** Indica se o aluno possui fidelidade vinculada. */
	public boolean temFidelidade() {
		return codFidelidade != null;
	}

	@Override
	public String toString() {
		return "Status [codStatus=" + codStatus
				+ ", cpfAluno=" + cpfAluno
				+ ", codPlano=" + codPlano
				+ ", planoAtivo=" + planoAtivo
				+ ", codFidelidade=" + (codFidelidade != null ? codFidelidade : "nenhuma") + "]";
	}
}