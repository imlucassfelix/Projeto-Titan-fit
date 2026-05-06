package repositoyDB;

import entities.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import connection.ConexaoBancoDados;

public class PlanoDB {

    public void inserir(Plano plano) {
        String sql = "INSERT INTO plano (cod_plano, categoria, valor, beneficios, pagamento, cod_fidelidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, plano.getCodPlano());
            stmt.setString(2, plano.getCategoria());
            stmt.setDouble(3, plano.getValor());

            // Transforma a lista de benefícios em uma String separada por vírgulas para salvar no banco
            String beneficiosStr = plano.getBeneficios() != null ? String.join(",", plano.getBeneficios()) : "";
            stmt.setString(4, beneficiosStr);

            stmt.setDouble(5, plano.getPagamento());
            stmt.setString(6, plano.getCodFidelidade());

            stmt.executeUpdate();
            System.out.println("***    Plano inserido com sucesso no TitanFit!    ***");

        } catch (SQLException e) {
            System.out.println("***       Erro ao inserir plano no banco:         ***" + e.getMessage());
        }
    }

    public Plano buscarPorId(int codPlano) {
        String sql = "SELECT * FROM plano WHERE cod_plano = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codPlano);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Plano plano = new Plano();
                    plano.setCodPlano(rs.getInt("cod_plano"));
                    plano.setCategoria(rs.getString("categoria"));
                    plano.setValor(rs.getDouble("valor"));

                    // Transforma a String do banco de volta em uma Lista de Strings
                    String beneficiosStr = rs.getString("beneficios");
                    if (beneficiosStr != null && !beneficiosStr.isEmpty()) {
                        plano.setBeneficios(new ArrayList<>(Arrays.asList(beneficiosStr.split(","))));
                    }

                    plano.setPagamento(rs.getDouble("pagamento"));
                    plano.setCodFidelidade(rs.getString("cod_fidelidade"));
                    return plano;
                }
            }
        } catch (SQLException e) {
            System.out.println("***            Erro ao buscar plano:              ***" + e.getMessage());
        }
        return null;
    }

    public List<Plano> listarTodos() {
        String sql = "SELECT * FROM plano";
        List<Plano> listaPlanos = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plano plano = new Plano();
                plano.setCodPlano(rs.getInt("cod_plano"));
                plano.setCategoria(rs.getString("categoria"));
                plano.setValor(rs.getDouble("valor"));

                String beneficiosStr = rs.getString("beneficios");
                if (beneficiosStr != null && !beneficiosStr.isEmpty()) {
                    plano.setBeneficios(new ArrayList<>(Arrays.asList(beneficiosStr.split(","))));
                }

                plano.setPagamento(rs.getDouble("pagamento"));
                plano.setCodFidelidade(rs.getString("cod_fidelidade"));

                listaPlanos.add(plano);
            }

        } catch (SQLException e) {
            System.out.println("***            Erro ao listar planos:             ***" + e.getMessage());
        }

        return listaPlanos;
    }

    public void atualizar(Plano plano) {
        String sql = "UPDATE plano SET " +
                "categoria = ?, " +
                "valor = ?, " +
                "beneficios = ?, " +
                "pagamento = ?, " +
                "cod_fidelidade = ? " +
                "WHERE cod_plano = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getCategoria());
            stmt.setDouble(2, plano.getValor());

            String beneficiosStr = plano.getBeneficios() != null ? String.join(",", plano.getBeneficios()) : "";
            stmt.setString(3, beneficiosStr);

            stmt.setDouble(4, plano.getPagamento());
            stmt.setString(5, plano.getCodFidelidade());

            // O código do plano é a chave de busca
            stmt.setInt(6, plano.getCodPlano());

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void deletar(int codPlano) {
        String sql = "DELETE FROM plano WHERE cod_plano = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codPlano);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("***     Plano deletado com sucesso do TitanFit!   ***");
            } else {
                System.out.println("***   Nenhum plano encontrado com esse código.    ***");
            }

        } catch (SQLException e) {
            System.out.println("***            Erro ao deletar plano:             ***" + e.getMessage());
        }
    }
}