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

            String beneficiosStr = plano.getBeneficios() != null ? String.join(",", plano.getBeneficios()) : "";
            stmt.setString(4, beneficiosStr);

            stmt.setDouble(5, plano.getPagamento());
            stmt.setInt(6, plano.getCodFidelidade()); // Corrigido: setInt

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

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPlano(rs);
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
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaPlanos.add(mapearPlano(rs));
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
            stmt.setInt(5, plano.getCodFidelidade()); // Corrigido: setInt
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

    // Método auxiliar para evitar duplicação de código de mapeamento
    private Plano mapearPlano(ResultSet rs) throws SQLException {
        Plano plano = new Plano();
        plano.setCodPlano(rs.getInt("cod_plano"));
        plano.setCategoria(rs.getString("categoria"));
        plano.setValor(rs.getDouble("valor"));

        String beneficiosStr = rs.getString("beneficios");
        if (beneficiosStr != null && !beneficiosStr.isEmpty()) {
            plano.setBeneficios(new ArrayList<>(Arrays.asList(beneficiosStr.split(","))));
        }

        plano.setPagamento(rs.getDouble("pagamento"));
        plano.setCodFidelidade(rs.getInt("cod_fidelidade")); // Corrigido: getInt
        return plano;
    }
}