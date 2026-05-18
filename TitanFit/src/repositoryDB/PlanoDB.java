package repositoryDB;

import entities.Plano;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Plano.
 * Realiza operacoes CRUD na tabela 'plano' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * <p>Alteracao v2.0: campo {@code cod_fidelidade} removido da tabela plano.
 * A fidelidade agora e vinculada ao aluno via {@link entities.Status}.</p>
 *
 * @author Lucas Felix
 * @version 2.0
 */
public class PlanoDB implements Persistivel<Plano, Integer> {

    @Override
    public void inserir(Plano plano) {
        plano.setCodPlano(proximoCodigo());
        String sql = "INSERT INTO plano (cod_plano, categoria, valor, beneficios, duracao_meses) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, plano.getCodPlano());
            stmt.setString(2, plano.getCategoria());
            stmt.setDouble(3, plano.getValor());

            String beneficiosStr = plano.getBeneficios() != null ? String.join(",", plano.getBeneficios()) : "";
            stmt.setString(4, beneficiosStr);
            stmt.setInt(5, plano.getDuracaoMeses());

            stmt.executeUpdate();
            System.out.println("***    Plano inserido com sucesso no TitanFit!    ***");

        } catch (SQLException e) {
            System.out.println("***       Erro ao inserir plano no banco:         ***" + e.getMessage());
        }
    }

    public int proximoCodigo() {
        String sql = "SELECT COALESCE(MAX(cod_plano), 0) + 1 FROM plano";
        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao gerar codigo: " + e.getMessage());
        }
        return 1;
    }

    @Override
    public Plano buscarPorId(Integer codPlano) {
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

    @Override
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

    @Override
    public void atualizar(Plano plano) {
        String sql = "UPDATE plano SET " +
                "categoria = ?, " +
                "valor = ?, " +
                "beneficios = ?, " +
                "duracao_meses = ? " +
                "WHERE cod_plano = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getCategoria());
            stmt.setDouble(2, plano.getValor());

            String beneficiosStr = plano.getBeneficios() != null ? String.join(",", plano.getBeneficios()) : "";
            stmt.setString(3, beneficiosStr);
            stmt.setInt(4, plano.getDuracaoMeses());
            stmt.setInt(5, plano.getCodPlano());

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer codPlano) {
        String sql = "DELETE FROM plano WHERE cod_plano = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codPlano);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("***     Plano deletado com sucesso do TitanFit!   ***");
            } else {
                System.out.println("***   Nenhum plano encontrado com esse codigo.    ***");
            }

        } catch (SQLException e) {
            System.out.println("***            Erro ao deletar plano:             ***" + e.getMessage());
        }
    }

    private Plano mapearPlano(ResultSet rs) throws SQLException {
        Plano plano = new Plano();
        plano.setCodPlano(rs.getInt("cod_plano"));
        plano.setCategoria(rs.getString("categoria"));
        plano.setValor(rs.getDouble("valor"));

        String beneficiosStr = rs.getString("beneficios");
        if (beneficiosStr != null && !beneficiosStr.isEmpty()) {
            plano.setBeneficios(new ArrayList<>(Arrays.asList(beneficiosStr.split(","))));
        }
        plano.setDuracaoMeses(rs.getInt("duracao_meses"));
        return plano;
    }
}