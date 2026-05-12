package repositoryDB;

import entities.Equipamentos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Equipamentos.
 * Realiza operacoes CRUD na tabela 'equipamentos' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * @author Mateus Santos
 * @version 1.0
 */
public class EquipamentosDB implements Persistivel<Equipamentos, Integer> {

	@Override
	public void inserir(Equipamentos equipamento) {
		String sql = "INSERT INTO equipamentos (cod_maquina, nome_equipamento, modelo, estado, data_aquisicao) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, equipamento.getCodMaquina());
			stmt.setString(2, equipamento.getNome());
			stmt.setString(3, equipamento.getModelo());
			stmt.setString(4, equipamento.getEstado());
			stmt.setDate(5, Date.valueOf(equipamento.getDataAquisicao()));

			stmt.executeUpdate();
			System.out.println("*** Equipamento inserido com sucesso no TitanFit! ***");

		} catch (SQLException e) {
			System.out.println("***    Erro ao inserir equipamento no banco:      ***" + e.getMessage());
		}
	}

	@Override
	public Equipamentos buscarPorId(Integer codMaquina) {
		String sql = "SELECT * FROM equipamentos WHERE cod_maquina = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codMaquina);

			try (var rs = stmt.executeQuery()) {
				if (rs.next()) {
					Equipamentos equipamento = new Equipamentos();
					equipamento.setCodMaquina(rs.getInt("cod_maquina"));
					equipamento.setNome(rs.getString("nome_equipamento"));
					equipamento.setModelo(rs.getString("modelo"));
					equipamento.setEstado(rs.getString("estado"));
					equipamento.setDataAquisicao(rs.getDate("data_aquisicao").toLocalDate());
					return equipamento;
				}
			}
		} catch (SQLException e) {
			System.out.println("***         Erro ao buscar equipamento:           ***" + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Equipamentos> listarTodos() {
		String sql = "SELECT * FROM equipamentos";
		List<Equipamentos> listaEquipamentos = new ArrayList<>();

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Equipamentos equipamento = new Equipamentos();
				equipamento.setCodMaquina(rs.getInt("cod_maquina"));
				equipamento.setNome(rs.getString("nome_equipamento"));
				equipamento.setModelo(rs.getString("modelo"));
				equipamento.setEstado(rs.getString("estado"));
				equipamento.setDataAquisicao(rs.getDate("data_aquisicao").toLocalDate());

				listaEquipamentos.add(equipamento);
			}

		} catch (SQLException e) {
			System.out.println("***         Erro ao listar equipamentos:          ***" + e.getMessage());
		}

		return listaEquipamentos;
	}

	@Override
	public void atualizar(Equipamentos equipamento) {
		String sql = "UPDATE equipamentos SET " +
				"nome_equipamento = ?, " +
				"modelo = ?, " +
				"estado = ?, " +
				"data_aquisicao = ? " +
				"WHERE cod_maquina = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, equipamento.getNome());
			stmt.setString(2, equipamento.getModelo());
			stmt.setString(3, equipamento.getEstado());
			stmt.setDate(4, Date.valueOf(equipamento.getDataAquisicao()));
			stmt.setInt(5, equipamento.getCodMaquina());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	@Override
	public void deletar(Integer codMaquina) {
		String sql = "DELETE FROM equipamentos WHERE cod_maquina = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codMaquina);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("*** Equipamento deletado com sucesso do TitanFit! ***");
			} else {
				System.out.println("*** Nenhuma maquina encontrada com esse codigo.   ***");
			}

		} catch (SQLException e) {
			System.out.println("***         Erro ao deletar equipamento:          ***" + e.getMessage());
		}
	}
}