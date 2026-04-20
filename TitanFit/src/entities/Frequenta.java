package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Frequenta {
	private String cpfAluno;
	private int codAula;
	private LocalDate dataEntrada;
	private LocalTime horaEntrada;
	
	public Frequenta() {
	}

	public Frequenta(String cpfAluno, int codAula, LocalDate dataEntrada, LocalTime horaEntrada) {
		this.cpfAluno = cpfAluno;
		this.codAula = codAula;
		this.dataEntrada = dataEntrada;
		this.horaEntrada = horaEntrada;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public int getCodAula() {
		return codAula;
	}

	public void setCodAula(int codAula) {
		this.codAula = codAula;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	@Override
	public String toString() {
		return "Frequenta [cpfAluno=" + cpfAluno + ", codAula=" + codAula + ", dataEntrada=" + dataEntrada
				+ ", horaEntrada=" + horaEntrada + "]";
	}
	
	
}
