package entities;

public class Aula {
	private int codAula;           // Identificador único da aula
	private String nomeAula;
	private String descricaoAula;
	private int capacidadeMaxima;
	private String modalidade;

	public Aula() {
	}

	public Aula(int codAula, String nomeAula, int capacidadeMaxima, String descricaoAula) {
		this.codAula = codAula;
		this.nomeAula = nomeAula;
		this.descricaoAula = descricaoAula;
		this.capacidadeMaxima = capacidadeMaxima;
		this.modalidade = modalidade;
	}


	public int getCodAula() {
		return codAula;
	}

	public void setCodAula(int codAula) {
		this.codAula = codAula;
	}

	public String getNomeAula() {
		return nomeAula;
	}

	public void setNomeAula(String nomeAula) {
		this.nomeAula = nomeAula;
	}

	public String getDescricaoAula() {
		return descricaoAula;
	}

	public void setDescricaoAula(String descricaoAula) {
		this.descricaoAula = descricaoAula;
	}

	public int getCapacidadeMaxima() {
		return capacidadeMaxima;
	}

	public void setCapacidadeMaxima(int capacidadeMaxima) {
		this.capacidadeMaxima = capacidadeMaxima;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}


	@Override
	public String toString() {
		return "Aula{" +
				"codAula=" + codAula +
				", nomeAula='" + nomeAula + '\'' +
				", descricaoAula='" + descricaoAula + '\'' +
				", capacidadeMaxima=" + capacidadeMaxima +
				", modalidade='" + modalidade + '\'' +
				'}';
	}
}
