package repositoyDB;

import entities.Instrutor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class InstrutorDB {

	public void inserir(Instrutor instrutor) {
		String sql = "INSERT INTO instrutor (cpf_instrutor, nome_instrutor, email_instrutor, data_nascimento, sexo, especialidade, salario) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, instrutor.getCpfInstrutor());
			stmt.setString(2, instrutor.getNomeInstrutor());
			stmt.setString(3, instrutor.getEmailInstrutor());
			stmt.setDate(4, Date.valueOf(instrutor.getDataNascimento()));
			stmt.setString(5, instrutor.getSexo());
			stmt.setString(6, instrutor.getEspecialidade());
			stmt.setDouble(7, instrutor.getSalario());

			stmt.executeUpdate();
			System.out.println("*** Instrutor inserido com sucesso no TitanFit! ***");

		} catch (SQLException e) {
			System.out.println("***     Erro ao inserir instrutor no banco:     ***" + e.getMessage());
		}
	}

	public Instrutor buscarPorId(String cpfInstrutor) {
		String sql = "SELECT * FROM instrutor WHERE cpf_instrutor = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpfInstrutor);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Instrutor instrutor = new Instrutor();
					instrutor.setCpfInstrutor(rs.getString("cpf_instrutor"));
					instrutor.setNomeInstrutor(rs.getString("nome_instrutor"));
					instrutor.setEmailInstrutor(rs.getString("email_instrutor"));
					instrutor.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
					instrutor.setSexo(rs.getString("sexo"));
					instrutor.setEspecialidade(rs.getString("especialidade"));
					instrutor.setSalario(rs.getDouble("salario"));
					return instrutor;
				}
			}
		} catch (SQLException e) {
			System.out.println("***         Erro ao buscar instrutor:           ***" + e.getMessage());
		}
		return null;
	}

	public List<Instrutor> listarTodos() {
		String sql = "SELECT * FROM instrutor";
		List<Instrutor> listaInstrutores = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Instrutor instrutor = new Instrutor();
				instrutor.setCpfInstrutor(rs.getString("cpf_instrutor"));
				instrutor.setNomeInstrutor(rs.getString("nome_instrutor"));
				instrutor.setEmailInstrutor(rs.getString("email_instrutor"));
				instrutor.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
				instrutor.setSexo(rs.getString("sexo"));
				instrutor.setEspecialidade(rs.getString("especialidade"));
				instrutor.setSalario(rs.getDouble("salario"));

				listaInstrutores.add(instrutor);
			}

		} catch (SQLException e) {
			System.out.println("***         Erro ao listar instrutores:         ***" + e.getMessage());
		}

		return listaInstrutores;
	}

	public void atualizar(Instrutor instrutor) {
		String sql = "UPDATE instrutor SET " +
				"nome_instrutor = ?, " +
				"email_instrutor = ?, " +
				"data_nascimento = ?, " +
				"sexo = ?, " +
				"especialidade = ?, " +
				"salario = ? " +
				"WHERE cpf_instrutor = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, instrutor.getNomeInstrutor());
			stmt.setString(2, instrutor.getEmailInstrutor());
			stmt.setDate(3, Date.valueOf(instrutor.getDataNascimento()));
			stmt.setString(4, instrutor.getSexo());
			stmt.setString(5, instrutor.getEspecialidade());
			stmt.setDouble(6, instrutor.getSalario());

			// O CPF é a chave de busca
			stmt.setString(7, instrutor.getCpfInstrutor());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	public void deletar(String cpfInstrutor) {
		String sql = "DELETE FROM instrutor WHERE cpf_instrutor = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpfInstrutor);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("*** Instrutor deletado com sucesso do TitanFit! ***");
			} else {
				System.out.println("***  Nenhum instrutor encontrado com esse CPF.  ***");
			}

		} catch (SQLException e) {
			System.out.println("***         Erro ao deletar instrutor:          ***" + e.getMessage());
		}
	}
}