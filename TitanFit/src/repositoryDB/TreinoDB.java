package repositoryDB;

import entities.Treino;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Treino.
 * Realiza operacoes CRUD na tabela 'treino' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * <p>Alteracao v2.0: cod_treino agora e gerado automaticamente
 * via {@link #proximoCodigo()}, eliminando risco de duplicidade
 * por entrada manual do usuario.</p>
 *
 * @author Ryan Vinicius
 * @version 2.0
 */
public class TreinoDB implements Persistivel<Treino, Integer> {

	/**
	 * Insere um novo treino no banco de dados.
	 * O codigo do treino e gerado automaticamente.
	 *
	 * @param treino Objeto Treino com os dados a inserir
	 */
	@Override
	public void inserir(Treino treino) {
		treino.setCodTreino(proximoCodigo());

		String sql = "INSERT INTO treino (cod_treino, cod_aula, series, grupo_muscular, exercicio) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, treino.getCodTreino());
			stmt.setInt(2, treino.getCodAula());
			stmt.setString(3, treino.getSeries());
			stmt.setString(4, treino.getGrupoMuscular());
			stmt.setString(5, treino.getExercicio());

			stmt.executeUpdate();
			System.out.println("***    Treino inserido com sucesso no TitanFit!   ***");
			System.out.println("***    Codigo gerado: " + treino.getCodTreino() + "                  ***");

		} catch (SQLException e) {
			System.out.println("***       Erro ao inserir treino no banco:        ***" + e.getMessage());
		}
	}

	/**
	 * Busca um treino pelo codigo.
	 *
	 * @param codTreino Codigo do treino
	 * @return Treino encontrado, ou null se nao existir
	 */
	@Override
	public Treino buscarPorId(Integer codTreino) {
		String sql = "SELECT * FROM treino WHERE cod_treino = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codTreino);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Treino treino = new Treino();
					treino.setCodTreino(rs.getInt("cod_treino"));
					treino.setCodAula(rs.getInt("cod_aula"));
					treino.setSeries(rs.getString("series"));
					treino.setGrupoMuscular(rs.getString("grupo_muscular"));
					treino.setExercicio(rs.getString("exercicio"));
					return treino;
				}
			}
		} catch (SQLException e) {
			System.out.println("***           Erro ao buscar treino:              ***" + e.getMessage());
		}
		return null;
	}

	/**
	 * Lista todos os treinos cadastrados no banco de dados.
	 *
	 * @return Lista de treinos (vazia se nao houver registros)
	 */
	@Override
	public List<Treino> listarTodos() {
		String sql = "SELECT * FROM treino";
		List<Treino> listaTreinos = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Treino treino = new Treino();
				treino.setCodTreino(rs.getInt("cod_treino"));
				treino.setCodAula(rs.getInt("cod_aula"));
				treino.setSeries(rs.getString("series"));
				treino.setGrupoMuscular(rs.getString("grupo_muscular"));
				treino.setExercicio(rs.getString("exercicio"));

				listaTreinos.add(treino);
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao listar treinos:             ***" + e.getMessage());
		}

		return listaTreinos;
	}

	/**
	 * Atualiza os dados de um treino existente no banco.
	 *
	 * @param treino Objeto Treino com os dados atualizados
	 */
	@Override
	public void atualizar(Treino treino) {
		String sql = "UPDATE treino SET " +
				"cod_aula = ?, " +
				"series = ?, " +
				"grupo_muscular = ?, " +
				"exercicio = ? " +
				"WHERE cod_treino = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, treino.getCodAula());
			stmt.setString(2, treino.getSeries());
			stmt.setString(3, treino.getGrupoMuscular());
			stmt.setString(4, treino.getExercicio());
			stmt.setInt(5, treino.getCodTreino());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	/**
	 * Remove um treino do banco de dados pelo codigo.
	 *
	 * @param codTreino Codigo do treino a ser removido
	 */
	@Override
	public void deletar(Integer codTreino) {
		String sql = "DELETE FROM treino WHERE cod_treino = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codTreino);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("***    Treino deletado com sucesso do TitanFit!   ***");
			} else {
				System.out.println("***  Nenhum treino encontrado com esse codigo.    ***");
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao deletar treino:             ***" + e.getMessage());
		}
	}

	/**
	 * Retorna o proximo codigo de treino disponivel (max + 1).
	 * Usado para auto-incremento no cadastro de treinos.
	 *
	 * @return Proximo codigo disponivel (1 se tabela vazia)
	 */
	public int proximoCodigo() {
		String sql = "SELECT COALESCE(MAX(cod_treino), 0) + 1 FROM treino";
		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql);
		     ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("***   Erro ao obter proximo codigo de treino:   ***" + e.getMessage());
		}
		return 1;
	}
}