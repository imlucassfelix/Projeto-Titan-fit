package entities;

import java.time.LocalDate;

public class Aluno {
	private String cpfAluno;
	private String nomeAluno;
	private String emailAluno;
	private LocalDate dataNascimento;
	private String sexo;
	
	public Aluno() {
	}

	public Aluno(String cpfAluno, String nomeAluno, String emailAluno, LocalDate dataNascimento, String sexo) {
		this.cpfAluno = cpfAluno;
		this.nomeAluno = nomeAluno;
		this.emailAluno = emailAluno;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getEmailAluno() {
		return emailAluno;
	}

	public void setEmailAluno(String emailAluno) {
		this.emailAluno = emailAluno;
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
	
	@Override
	public String toString() {
		return "Aluno [cpfAluno=" + cpfAluno + ", nomeAluno=" + nomeAluno + ", emailAluno=" + emailAluno
				+ ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + "]";
	}
	
	

}
