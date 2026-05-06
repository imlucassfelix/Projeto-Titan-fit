package repositoyDB;

import entities.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class StatusDB {

    public void inserir(Status status) {
        String sql = "INSERT INTO status (cod_status, cpf_aluno, cod_plano, plano_ativo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, status.getCodStatus());

            // Removendo pontos e traços para salvar apenas os números no banco
            String cpfLimpo = status.getCpfAluno() != null ? status.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(2, cpfLimpo);

            stmt.setInt(3, status.getCodPlano());
            stmt.setBoolean(4, status.isPlanoAtivo());

            stmt.executeUpdate();
            System.out.println("***    Status inserido com sucesso no TitanFit!   ***");

        } catch (SQLException e) {
            System.out.println("***       Erro ao inserir status no banco:        ***" + e.getMessage());
        }
    }

    public Status buscarPorId(int codStatus) {
        String sql = "SELECT * FROM status WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codStatus);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Status status = new Status();
                    status.setCodStatus(rs.getInt("cod_status"));
                    status.setCpfAluno(rs.getString("cpf_aluno"));
                    status.setCodPlano(rs.getInt("cod_plano"));
                    status.setPlanoAtivo(rs.getBoolean("plano_ativo"));
                    return status;
                }
            }
        } catch (SQLException e) {
            System.out.println("***           Erro ao buscar status:              ***" + e.getMessage());
        }
        return null;
    }

    public List<Status> listarTodos() {
        String sql = "SELECT * FROM status";
        List<Status> listaStatus = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Status status = new Status();
                status.setCodStatus(rs.getInt("cod_status"));
                status.setCpfAluno(rs.getString("cpf_aluno"));
                status.setCodPlano(rs.getInt("cod_plano"));
                status.setPlanoAtivo(rs.getBoolean("plano_ativo"));

                listaStatus.add(status);
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao listar status:              ***" + e.getMessage());
        }

        return listaStatus;
    }

    public void atualizar(Status status) {
        String sql = "UPDATE status SET " +
                "cpf_aluno = ?, " +
                "cod_plano = ?, " +
                "plano_ativo = ? " +
                "WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = status.getCpfAluno() != null ? status.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);
            stmt.setInt(2, status.getCodPlano());
            stmt.setBoolean(3, status.isPlanoAtivo());

            // O código do status é a chave de busca
            stmt.setInt(4, status.getCodStatus());

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void deletar(int codStatus) {
        String sql = "DELETE FROM status WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codStatus);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("***    Status deletado com sucesso do TitanFit!   ***");
            } else {
                System.out.println("***  Nenhum status encontrado com esse código.    ***");
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao deletar status:             ***" + e.getMessage());
        }
    }
}