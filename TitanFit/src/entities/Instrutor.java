package entities;

import java.time.LocalDate;

public class Instrutor {
	private String cpfInstrutor;
	private String nomeInstrutor;
	private String emailInstrutor;
	private LocalDate dataNascimento;
	private String sexo;
	private String especialidade;
	private double salario;
	
	public Instrutor() {
	}
	
	public Instrutor(String cpfInstrutor, String nomeInstrutor, String emailInstrutor, LocalDate dataNascimento,
			String sexo, String especialidade, double salario) {
		this.cpfInstrutor = cpfInstrutor;
		this.nomeInstrutor = nomeInstrutor;
		this.emailInstrutor = emailInstrutor;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.especialidade = especialidade;
		this.salario = salario;
	}

	public String getCpfInstrutor() {
		return cpfInstrutor;
	}

	public void setCpfInstrutor(String cpfInstrutor) {
		this.cpfInstrutor = cpfInstrutor;
	}

	public String getNomeInstrutor() {
		return nomeInstrutor;
	}

	public void setNomeInstrutor(String nomeInstrutor) {
		this.nomeInstrutor = nomeInstrutor;
	}

	public String getEmailInstrutor() {
		return emailInstrutor;
	}

	public void setEmailInstrutor(String emailInstrutor) {
		this.emailInstrutor = emailInstrutor;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "Instrutor [cpfInstrutor=" + cpfInstrutor + ", nomeInstrutor=" + nomeInstrutor + ", emailInstrutor="
				+ emailInstrutor + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + ", especialidade="
				+ especialidade + ", salario=" + salario + "]";
	}
	

}
