package entities;

import java.time.LocalDate;

// HERANÇA: Instrutor herda de Pessoa
public class Instrutor extends Pessoa {
	private String cpfInstrutor;
	private String nomeInstrutor;
	private String especialidade;
	private double salario;

	public Instrutor() {
		super();
	}

	public Instrutor(String cpfInstrutor, String nomeInstrutor, String emailInstrutor, LocalDate dataNascimento,
	                 String sexo, String especialidade, double salario) {
		super(emailInstrutor, dataNascimento, sexo);
		this.cpfInstrutor = cpfInstrutor;
		this.nomeInstrutor = nomeInstrutor;
		this.especialidade = especialidade;
		this.salario = salario;
	}

	public String getCpfInstrutor() { return cpfInstrutor; }
	public void setCpfInstrutor(String cpfInstrutor) { this.cpfInstrutor = cpfInstrutor; }

	public String getNomeInstrutor() { return nomeInstrutor; }
	public void setNomeInstrutor(String nomeInstrutor) { this.nomeInstrutor = nomeInstrutor; }

	public String getEspecialidade() { return especialidade; }
	public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

	public double getSalario() { return salario; }
	public void setSalario(double salario) { this.salario = salario; }

	// Redirecionando para a classe Pai
	public String getEmailInstrutor() { return super.getEmail(); }
	public void setEmailInstrutor(String emailInstrutor) { super.setEmail(emailInstrutor); }

	public LocalDate getDataNascimento() { return super.getDataNascimento(); }
	public void setDataNascimento(LocalDate dataNascimento) { super.setDataNascimento(dataNascimento); }

	public String getSexo() { return super.getSexo(); }
	public void setSexo(String sexo) { super.setSexo(sexo); }

	// POLIMORFISMO: Implementação específica do instrutor
	@Override
	public String obterIdentificacao() {
		return "Instrutor: " + nomeInstrutor + " | Especialidade: " + especialidade;
	}

	@Override
	public String toString() {
		return "Instrutor [cpfInstrutor=" + cpfInstrutor + ", nomeInstrutor=" + nomeInstrutor + ", emailInstrutor="
				+ getEmailInstrutor() + ", dataNascimento=" + getDataNascimento() + ", sexo=" + getSexo() + ", especialidade="
				+ especialidade + ", salario=" + salario + "]";
	}
}