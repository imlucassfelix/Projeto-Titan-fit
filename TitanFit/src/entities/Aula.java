package entities;

public class Aula {
	private int codAula;
	private String nomeAula;
	private String descricaoAula;
	private int capacidadeMaxima;
	private String cpfAluno;
	private String modalidade;
	private String horario;
	
	public Aula() {
	}

	public Aula(int codAula, String nomeAula, String descricaoAula, int capacidadeMaxima, String cpfAluno, String modalidade, String horario) {
		this.codAula = codAula;
		this.nomeAula = nomeAula;
		this.descricaoAula = descricaoAula;
		this.capacidadeMaxima = capacidadeMaxima;
		this.cpfAluno = cpfAluno;
		this.modalidade = modalidade;
		this.horario = horario;
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

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return "Aula{" +
				"codAula=" + codAula +
				", nomeAula='" + nomeAula + '\'' +
				", descricaoAula='" + descricaoAula + '\'' +
				", capacidadeMaxima=" + capacidadeMaxima +
				", cpfAluno='" + cpfAluno + '\'' +
				", modalidade='" + modalidade + '\'' +
				", horario='" + horario + '\'' +
				'}';
	}
}
