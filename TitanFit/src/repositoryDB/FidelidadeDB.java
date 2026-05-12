package repositoryDB;

import entities.Fidelidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Fidelidade.
 * Realiza operacoes CRUD na tabela 'fidelidade' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * @author Lucas Felix
 * @version 1.0
 */
public class FidelidadeDB implements Persistivel<Fidelidade, Integer> {

	@Override
	public void inserir(Fidelidade fidelidade) {
		String sql = "INSERT INTO fidelidade (cod_fidelidade, descricao, periodo) VALUES (?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, fidelidade.getCodFidelidade());
			stmt.setString(2, fidelidade.getDescricao());
			stmt.setDate(3, Date.valueOf(fidelidade.getPeriodo()));

			stmt.executeUpdate();
			System.out.println("***  Fidelidade inserida com sucesso no TitanFit! ***");

		} catch (SQLException e) {
			System.out.println("***    Erro ao inserir fidelidade no banco:        ***" + e.getMessage());
		}
	}

	@Override
	public Fidelidade buscarPorId(Integer codFidelidade) {
		String sql = "SELECT * FROM fidelidade WHERE cod_fidelidade = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codFidelidade);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Fidelidade fidelidade = new Fidelidade();
					fidelidade.setCodFidelidade(rs.getInt("cod_fidelidade"));
					fidelidade.setDescricao(rs.getString("descricao"));
					fidelidade.setPeriodo(rs.getDate("periodo").toLocalDate());
					return fidelidade;
				}
			}
		} catch (SQLException e) {
			System.out.println("***         Erro ao buscar fidelidade:             ***" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Fidelidade> listarTodos() {
		String sql = "SELECT * FROM fidelidade";
		List<Fidelidade> lista = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql);
		     ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Fidelidade fidelidade = new Fidelidade();
				fidelidade.setCodFidelidade(rs.getInt("cod_fidelidade"));
				fidelidade.setDescricao(rs.getString("descricao"));
				fidelidade.setPeriodo(rs.getDate("periodo").toLocalDate());
				lista.add(fidelidade);
			}

		} catch (SQLException e) {
			System.out.println("***        Erro ao listar fidelidades:             ***" + e.getMessage());
		}

		return lista;
	}

	@Override
	public void atualizar(Fidelidade fidelidade) {
		String sql = "UPDATE fidelidade SET descricao = ?, periodo = ? WHERE cod_fidelidade = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, fidelidade.getDescricao());
			stmt.setDate(2, Date.valueOf(fidelidade.getPeriodo()));
			stmt.setInt(3, fidelidade.getCodFidelidade());

			stmt.executeUpdate();
			System.out.println("*** Fidelidade atualizada com sucesso no TitanFit! ***");

		} catch (SQLException e) {
			System.out.println("***        Erro ao atualizar fidelidade:           ***" + e.getMessage());
		}
	}

	@Override
	public void deletar(Integer codFidelidade) {
		String sql = "DELETE FROM fidelidade WHERE cod_fidelidade = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codFidelidade);

			int linhasAfetadas = stmt.executeUpdate();
			if (linhasAfetadas > 0) {
				System.out.println("***  Fidelidade deletada com sucesso do TitanFit! ***");
			} else {
				System.out.println("***  Nenhuma fidelidade encontrada com esse codigo. ***");
			}

		} catch (SQLException e) {
			System.out.println("***        Erro ao deletar fidelidade:             ***" + e.getMessage());
		}
	}
}