package repositoyDB;

import entities.Equipamentos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class EquipamentosDB {

	public void inserir(Equipamentos equipamento) {
		String sql = "INSERT INTO equipamento (cod_maquina, nome_equipamento, modelo, estado, data_aquisicao) VALUES (?, ?, ?, ?, ?)";

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

	public Equipamentos buscarPorId(int codMaquina) {
		String sql = "SELECT * FROM equipamento WHERE cod_maquina = ?";

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

	public List<Equipamentos> listarTodos() {
		String sql = "SELECT * FROM equipamento";
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

	public void atualizar(Equipamentos equipamento) {
		String sql = "UPDATE equipamento SET " +
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

			// O código da máquina é a chave de busca
			stmt.setInt(5, equipamento.getCodMaquina());

			stmt.executeUpdate();
			System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar: " + e.getMessage());
		}
	}

	public void deletar(int codMaquina) {
		String sql = "DELETE FROM equipamento WHERE cod_maquina = ?";

		try (Connection conn = ConexaoBancoDados.conectar();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, codMaquina);

			int linhasAfetadas = stmt.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("*** Equipamento deletado com sucesso do TitanFit! ***");
			} else {
				System.out.println("*** Nenhuma máquina encontrada com esse código.   ***");
			}

		} catch (SQLException e) {
			System.out.println("***         Erro ao deletar equipamento:          ***" + e.getMessage());
		}
	}
}