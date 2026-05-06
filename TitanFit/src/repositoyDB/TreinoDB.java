package repositoyDB;

import entities.Treino;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class TreinoDB {

	public void inserir(Treino treino) {
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

		} catch (SQLException e) {
			System.out.println("***       Erro ao inserir treino no banco:        ***" + e.getMessage());
		}
	}

	public Treino buscarPorId(int codTreino) {
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

			// O código do treino é a chave de busca
			stmt.setInt(5, treino.getCodTreino());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	public void deletar(int codTreino) {
		String sql = "DELETE FROM treino WHERE cod_treino = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codTreino);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("***    Treino deletado com sucesso do TitanFit!   ***");
			} else {
				System.out.println("***  Nenhum treino encontrado com esse código.    ***");
			}

		} catch (SQLException e) {
			System.out.println("***           Erro ao deletar treino:             ***" + e.getMessage());
		}
	}
}