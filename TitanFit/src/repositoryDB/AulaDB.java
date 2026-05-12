package repositoryDB;

import entities.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Aula.
 * Realiza operacoes CRUD na tabela 'aula' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * @author Ryan Vinicius
 * @version 1.0
 */
public class AulaDB implements Persistivel<Aula, Integer> {

	/**
	 * Insere uma nova aula no banco de dados.
	 *
	 * @param aula Objeto Aula com os dados a inserir
	 */
	@Override
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

	/**
	 * Busca uma aula pelo codigo.
	 *
	 * @param codAula Codigo da aula
	 * @return Aula encontrada, ou null se nao existir
	 */
	@Override
	public Aula buscarPorId(Integer codAula) {
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

	/**
	 * Lista todas as aulas cadastradas no banco de dados.
	 *
	 * @return Lista de aulas (vazia se nao houver registros)
	 */
	@Override
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

	/**
	 * Atualiza os dados de uma aula existente no banco.
	 *
	 * @param aula Objeto Aula com os dados atualizados
	 */
	@Override
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
			stmt.setInt(6, aula.getCodAula());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	/**
	 * Remove uma aula do banco de dados pelo codigo.
	 *
	 * @param codAula Codigo da aula a ser removida
	 */
	@Override
	public void deletar(Integer codAula) {
		String sql = "DELETE FROM aula WHERE cod_aula = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codAula);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("***    Aula deletada com sucesso do TitanFit!   ***");
			} else {
				System.out.println("***  Nenhuma aula encontrada com esse codigo.   ***");
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao deletar aula:             ***" + e.getMessage());
		}
	}
}