package repositoryDB;

import entities.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Aluno.
 * Realiza operacoes CRUD na tabela 'aluno' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * @author Lucas Rodrigues
 * @version 1.0
 */
public class AlunoDB implements Persistivel<Aluno, String> {

	/**
	 * Insere um novo aluno no banco de dados.
	 *
	 * @param aluno Objeto Aluno com os dados a inserir
	 */
	@Override
	public void inserir(Aluno aluno) {
		String sql = "INSERT INTO aluno (cpf_aluno, nome_aluno, email_aluno, telefone_aluno, data_nascimento, data_matricula, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			String cpfLimpo = aluno.getCpfAluno() != null
					? aluno.getCpfAluno().replace(".", "").replace("-", "") : null;
			stmt.setString(1, cpfLimpo);
			stmt.setString(2, aluno.getNomeAluno());
			stmt.setString(3, aluno.getEmailAluno());
			stmt.setString(4, aluno.getTelefoneAluno());
			stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
			stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));
			stmt.setString(7, aluno.getSexo());

			stmt.executeUpdate();
			System.out.println("***  Aluno inserido com sucesso no TitanFit!   ***");

		} catch (SQLException e) {
			System.out.println("***       Erro ao inserir aluno no banco:      ***" + e.getMessage());
		}
	}

	/**
	 * Busca um aluno pelo CPF.
	 *
	 * @param cpfAluno CPF do aluno (11 digitos)
	 * @return Aluno encontrado, ou null se nao existir
	 */
	@Override
	public Aluno buscarPorId(String cpfAluno) {
		String sql = "SELECT * FROM aluno WHERE cpf_aluno = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			String cpfLimpo = cpfAluno != null
					? cpfAluno.replace(".", "").replace("-", "") : null;
			stmt.setString(1, cpfLimpo);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Aluno aluno = new Aluno();  // ← declarar aqui dentro
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
			System.out.println("*** Erro ao buscar aluno: ***" + e.getMessage());
		}
		return null;
	}

	/**
	 * Lista todos os alunos cadastrados no banco de dados.
	 *
	 * @return Lista de alunos (vazia se nao houver registros)
	 */
	@Override
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

				listaAlunos.add(aluno);
			}

		} catch (SQLException e) {
			System.out.println("***          Erro ao listar alunos:            ***" + e.getMessage());
		}

		return listaAlunos;
	}

	/**
	 * Atualiza os dados de um aluno existente no banco.
	 *
	 * @param aluno Objeto Aluno com os dados atualizados
	 */
	@Override
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
			String cpfLimpo = aluno.getCpfAluno() != null
					? aluno.getCpfAluno().replace(".", "").replace("-", "") : null;
			stmt.setString(6, cpfLimpo);

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	/**
	 * Remove um aluno do banco de dados pelo CPF.
	 *
	 * @param cpfAluno CPF do aluno a ser removido
	 */
	@Override
	public void deletar(String cpfAluno) {
		String sql = "DELETE FROM aluno WHERE cpf_aluno = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			// CORRETO — usar o parâmetro String cpfAluno que já existe
			String cpfLimpo = cpfAluno != null
					? cpfAluno.replace(".", "").replace("-", "") : null;
			stmt.setString(1, cpfLimpo);

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
