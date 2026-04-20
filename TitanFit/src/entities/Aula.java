package entities;

public class Aula {
	private int codAula;
	private String modalidade;
	private String horario;
	
	public Aula() {
	}

	public Aula(int codAula, String modalidade, String horario) {
		this.codAula = codAula;
		this.modalidade = modalidade;
		this.horario = horario;
	}

	public int getCodAula() {
		return codAula;
	}

	public void setCodAula(int codAula) {
		this.codAula = codAula;
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
		return "Aula [codAula=" + codAula + ", modalidade=" + modalidade + ", horario=" + horario + "]";
	}
	
	
}
