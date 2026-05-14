package entities;

/**
 * Representa um aluno matriculado na academia Titan Fit.
 *
 * <p>Herda de {@link Pessoa} os atributos de contato (e-mail,
 * data de nascimento, sexo) e adiciona os dados especificos do
 * aluno: CPF, telefone, data de matricula e nome.</p>
 *
 * <p>O metodo {@link #obterIdentificacao()} e utilizado pelo sistema
 * para exibir o aluno de forma polimórfica (Polimorfismo — Aula 11).</p>
 *
 * @author Lucas Rodrigues
 * @version 1.0
 * @see Pessoa
 * @see repositoryDB.AlunoDB
 */

import java.time.LocalDate;

// HERANÇA: Aluno herda de Pessoa
public class Aluno extends Pessoa {
	private String cpfAluno;
	private String telefoneAluno;
	private LocalDate dataMatricula;
	private String nomeAluno;

	public Aluno() {
		super();
	}

	public Aluno(String cpfAluno, String nomeAluno, String emailAluno, String telefoneAluno,
	             LocalDate dataNascimento, LocalDate dataMatricula, String sexo) {
		super(emailAluno, dataNascimento, sexo);
		this.cpfAluno = cpfAluno;
		this.nomeAluno = nomeAluno;
		this.telefoneAluno = telefoneAluno;
		this.dataMatricula = dataMatricula;
	}

	public String getCpfAluno() { return cpfAluno; }
	public void setCpfAluno(String cpfAluno) { this.cpfAluno = cpfAluno; }

	public String getTelefoneAluno() { return telefoneAluno; }
	public void setTelefoneAluno(String telefoneAluno) { this.telefoneAluno = telefoneAluno; }

	public LocalDate getDataMatricula() { return dataMatricula; }
	public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }

	public String getNomeAluno() { return nomeAluno; }
	public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

	// Redirecionando os métodos antigos para a classe Pai para não quebrar o banco de dados
	public String getEmailAluno() { return super.getEmail(); }
	public void setEmailAluno(String emailAluno) { super.setEmail(emailAluno); }

	public LocalDate getDataNascimento() { return super.getDataNascimento(); }
	public void setDataNascimento(LocalDate dataNascimento) { super.setDataNascimento(dataNascimento); }

	public String getSexo() { return super.getSexo(); }
	public void setSexo(String sexo) { super.setSexo(sexo); }

	@Override public final String obterIdentificacao() { return
			"Aluno: " + nomeAluno + " | CPF: " + cpfAluno; }


	@Override
	public String toString() {
		return "Aluno{" +
				"cpfAluno='" + cpfAluno + '\'' +
				", telefoneAluno=" + telefoneAluno +
				", dataMatricula=" + dataMatricula +
				", nomeAluno='" + nomeAluno + '\'' +
				", emailAluno='" + getEmailAluno() + '\'' +
				", dataNascimento=" + getDataNascimento() +
				", sexo='" + getSexo() + '\'' +
				'}';
	}
}