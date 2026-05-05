package repositoyDB;

import entities.Frequenta;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

public class FrequentaDB {

    public void inserir(Frequenta frequenta) {
        String sql = "INSERT INTO frequenta (cpf_aluno, cod_aula, data_entrada, hora_entrada) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = frequenta.getCpfAluno() != null ? frequenta.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);

            stmt.setInt(2, frequenta.getCodAula());
            stmt.setDate(3, Date.valueOf(frequenta.getDataEntrada()));
            stmt.setTime(4, Time.valueOf(frequenta.getHoraEntrada()));

            stmt.executeUpdate();
            System.out.println("***  Frequência registrada com sucesso no TitanFit!  ***");

        } catch (SQLException e) {
            System.out.println("***      Erro ao registrar frequência no banco:      ***" + e.getMessage());
        }
    }

    public Frequenta buscar(String cpfAluno, int codAula, LocalDate dataEntrada) {
        String sql = "SELECT * FROM frequenta WHERE cpf_aluno = ? AND cod_aula = ? AND data_entrada = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);
            stmt.setInt(2, codAula);
            stmt.setDate(3, Date.valueOf(dataEntrada));

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Frequenta frequenta = new Frequenta();
                    frequenta.setCpfAluno(rs.getString("cpf_aluno"));
                    frequenta.setCodAula(rs.getInt("cod_aula"));
                    frequenta.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
                    frequenta.setHoraEntrada(rs.getTime("hora_entrada").toLocalTime());
                    return frequenta;
                }
            }
        } catch (SQLException e) {
            System.out.println("***           Erro ao buscar frequência:             ***" + e.getMessage());
        }
        return null;
    }

    public List<Frequenta> listarTodos() {
        String sql = "SELECT * FROM frequenta";
        List<Frequenta> listaFrequencias = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Frequenta frequenta = new Frequenta();
                frequenta.setCpfAluno(rs.getString("cpf_aluno"));
                frequenta.setCodAula(rs.getInt("cod_aula"));
                frequenta.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
                frequenta.setHoraEntrada(rs.getTime("hora_entrada").toLocalTime());

                listaFrequencias.add(frequenta);
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao listar frequências:            ***" + e.getMessage());
        }

        return listaFrequencias;
    }

    public void atualizar(Frequenta frequenta) {
        // Em uma tabela de associação, geralmente atualizamos os dados secundários usando as chaves compostas no WHERE
        String sql = "UPDATE frequenta SET " +
                "hora_entrada = ? " +
                "WHERE cpf_aluno = ? AND cod_aula = ? AND data_entrada = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTime(1, Time.valueOf(frequenta.getHoraEntrada()));

            String cpfLimpo = frequenta.getCpfAluno() != null ? frequenta.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(2, cpfLimpo);
            stmt.setInt(3, frequenta.getCodAula());
            stmt.setDate(4, Date.valueOf(frequenta.getDataEntrada()));

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void deletar(String cpfAluno, int codAula, LocalDate dataEntrada) {
        String sql = "DELETE FROM frequenta WHERE cpf_aluno = ? AND cod_aula = ? AND data_entrada = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);
            stmt.setInt(2, codAula);
            stmt.setDate(3, Date.valueOf(dataEntrada));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("***  Frequência deletada com sucesso do TitanFit!  ***");
            } else {
                System.out.println("*** Nenhum registro encontrado com esses dados.    ***");
            }

        } catch (SQLException e) {
            System.out.println("***          Erro ao deletar frequência:           ***" + e.getMessage());
        }
    }
}