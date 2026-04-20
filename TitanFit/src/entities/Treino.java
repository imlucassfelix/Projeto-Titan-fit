package entities;

public class Treino {
	private int codTreino;
	private int codAula;
	private String series;
	private String grupoMuscular;
	private String exercicio;
	
	public Treino() {
	}
	public Treino(int codTreino, int codAula, String series, String grupoMuscular, String exercicio) {
		this.codTreino = codTreino;
		this.codAula = codAula;
		this.series = series;
		this.grupoMuscular = grupoMuscular;
		this.exercicio = exercicio;
	}
	public int getCodTreino() {
		return codTreino;
	}
	public void setCodTreino(int codTreino) {
		this.codTreino = codTreino;
	}
	public int getCodAula() {
		return codAula;
	}
	public void setCodAula(int codAula) {
		this.codAula = codAula;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getGrupoMuscular() {
		return grupoMuscular;
	}
	public void setGrupoMuscular(String grupoMuscular) {
		this.grupoMuscular = grupoMuscular;
	}
	public String getExercicio() {
		return exercicio;
	}
	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}
	@Override
	public String toString() {
		return "Treino [codTreino=" + codTreino + ", codAula=" + codAula + ", series=" + series + ", grupoMuscular="
				+ grupoMuscular + ", exercicio=" + exercicio + "]";
	}
	

}
