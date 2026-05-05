package repositoyDB;

import entities.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class AulaDB {

	public void inserir(Aula aula) {
		String sql = "INSERT INTO aula (cod_aula, nome_aula, descricao_aula, capacidade_maxima, modalidade, cpf_instrutor) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, aula.getCodAula());
			stmt.setString(2, aula.getNomeAula());
			stmt.setString(3, aula.getDescricaoAula());
			stmt.setInt(4, aula.getCapacidadeMaxima());
			stmt.setString(5, aula.getModalidade());
			stmt.setString(6, aula.getCpfInstrutor());

			stmt.executeUpdate();
			System.out.println("***    Aula inserida com sucesso no TitanFit!   ***");

		} catch (SQLException e) {
			System.out.println("***       Erro ao inserir aula no banco:        ***" + e.getMessage());
		}
	}

	public Aula buscarPorId(int codAula) {
		String sql = "SELECT * FROM aula WHERE cod_aula = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codAula);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Aula aula = new Aula();
					aula.setCodAula(rs.getInt("cod_aula"));
					aula.setNomeAula(rs.getString("nome_aula"));
					aula.setDescricaoAula(rs.getString("descricao_aula"));
					aula.setCapacidadeMaxima(rs.getInt("capacidade_maxima"));
					aula.setModalidade(rs.getString("modalidade"));
					aula.setCpfInstrutor(rs.getString("cpf_instrutor"));
					return aula;
				}
			}
		} catch (SQLException e) {
			System.out.println("***           Erro ao buscar aula:              ***" + e.getMessage());
		}
		return null;
	}

	public List<Aula> listarTodos() {
		String sql = "SELECT * FROM aula";
		List<Aula> listaAulas = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Aula aula = new Aula();
				aula.setCodAula(rs.getInt("cod_aula"));
				aula.setNomeAula(rs.getString("nome_aula"));
				aula.setDescricaoAula(rs.getString("descricao_aula"));
				aula.setCapacidadeMaxima(rs.getInt("capacidade_maxima"));
				aula.setModalidade(rs.getString("modalidade"));
				aula.setCpfInstrutor(rs.getString("cpf_instrutor"));

				listaAulas.add(aula);
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao listar aulas:             ***" + e.getMessage());
		}

		return listaAulas;
	}

	public void atualizar(Aula aula) {
		String sql = "UPDATE aula SET " +
				"nome_aula = ?, " +
				"descricao_aula = ?, " +
				"capacidade_maxima = ?, " +
				"modalidade = ?, " +
				"cpf_instrutor = ? " +
				"WHERE cod_aula = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, aula.getNomeAula());
			stmt.setString(2, aula.getDescricaoAula());
			stmt.setInt(3, aula.getCapacidadeMaxima());
			stmt.setString(4, aula.getModalidade());
			stmt.setString(5, aula.getCpfInstrutor());

			// O código da aula é a chave de busca
			stmt.setInt(6, aula.getCodAula());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	public void deletar(int codAula) {
		String sql = "DELETE FROM aula WHERE cod_aula = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codAula);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("***    Aula deletada com sucesso do TitanFit!   ***");
			} else {
				System.out.println("***  Nenhuma aula encontrada com esse código.   ***");
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao deletar aula:             ***" + e.getMessage());
		}
	}
}