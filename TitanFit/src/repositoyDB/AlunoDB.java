package repositoyDB;

import entities.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class AlunoDB {
	public void inserir(Aluno aluno) {
		// Dica: Liste as colunas explicitamente. É mais seguro se você mudar a tabela depois.
		String sql = "INSERT INTO aluno (cpf_aluno, nome_aluno, email_aluno, telefone_aluno, data_nascimento, data_matricula, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		// O try-with-resources garante que a conexão e o statement sejam fechados sozinhos
		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, aluno.getCpfAluno());
			stmt.setString(2, aluno.getNomeAluno());
			stmt.setString(3, aluno.getEmailAluno());
			stmt.setString(4, aluno.getTelefoneAluno());
			stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
			stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));
			stmt.setString(7, aluno.getSexo());

			stmt.executeUpdate();
			System.out.println("***  Aluno inserido com sucesso no TitanFit!   ***");

		} catch (SQLException e) {
			// Em projetos profissionais, costumamos lançar uma exceção personalizada ou usar um Logger
			System.out.println("***       Erro ao inserir aluno no banco:      ***" + e.getMessage());
		}
	}

	public Aluno buscarPorId(String cpfAluno) {
		String sql = "SELECT * FROM aluno WHERE cpf_aluno = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpfAluno);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Aluno aluno = new Aluno();
					aluno.setCpfAluno(rs.getString("cpf_aluno"));
					aluno.setNomeAluno(rs.getString("nome_aluno"));
					aluno.setEmailAluno(rs.getString("email_aluno"));
					aluno.setTelefoneAluno(rs.getString("telefone_aluno"));
					aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
					aluno.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
					aluno.setSexo(rs.getString("sexo"));
					return aluno;
				}
			}
		} catch (SQLException e) {
			System.out.println("***           Erro ao buscar aluno:            ***" + e.getMessage());
		}
		return null;
	}

	public List<Aluno> listarTodos() {
		String sql = "SELECT * FROM aluno";
		List<Aluno> listaAlunos = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setCpfAluno(rs.getString("cpf_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setEmailAluno(rs.getString("email_aluno"));
				aluno.setTelefoneAluno(rs.getString("telefone_aluno"));
				aluno.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
				aluno.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
				aluno.setSexo(rs.getString("sexo"));

				// Adiciona o aluno preenchido na lista
				listaAlunos.add(aluno);
			}

		} catch (SQLException e) {
			System.out.println("***          Erro ao listar alunos:            ***" + e.getMessage());
		}

		return listaAlunos; // Retorna a lista completa (ou vazia, caso não haja alunos)
	}

	public void atualizar(Aluno aluno) {
		String sql = "UPDATE aluno SET " +
				"nome_aluno = ?, " +
				"email_aluno = ?, " +
				"telefone_aluno = ?, " +
				"data_nascimento = ?, " +
				"sexo = ? " +
				"WHERE cpf_aluno = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, aluno.getNomeAluno());
			stmt.setString(2, aluno.getEmailAluno());
			stmt.setString(3, aluno.getTelefoneAluno());
			stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
			stmt.setString(5, aluno.getSexo());

			// O CPF é a chave para encontrar o registro
			stmt.setString(6, aluno.getCpfAluno());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	public void deletar(String cpfAluno) {
		// 1. O SQL precisa estar entre aspas duplas
		String sql = "DELETE FROM aluno WHERE cpf_aluno = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			// 2. Define o valor do CPF que será deletado
			stmt.setString(1, cpfAluno);

			// 3. Executa a deleção
			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("***   Aluno deletado com sucesso do TitanFit!  ***");
			} else {
				System.out.println("***    Nenhum aluno encontrado com esse CPF.   ***");
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao deletar aluno:           ***" + e.getMessage());
		}
	}
}
