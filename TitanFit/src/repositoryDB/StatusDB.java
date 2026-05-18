package repositoryDB;

import entities.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Status.
 * Realiza operacoes CRUD na tabela 'status_aluno' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * <p>Alteracao v2.0: coluna {@code cod_fidelidade} adicionada (nullable).
 * Indica se o aluno aderiu a uma fidelidade com desconto no plano.</p>
 *
 * @author Mateus Santos
 * @version 2.0
 */
public class StatusDB implements Persistivel<Status, Integer> {

    @Override
    public void inserir(Status status) {
        status.setCodStatus(proximoCodigo());
        String sql = "INSERT INTO status_aluno (cod_status, cpf_aluno, cod_plano, plano_ativo, cod_fidelidade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, status.getCodStatus());

            String cpfLimpo = status.getCpfAluno() != null
                    ? status.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(2, cpfLimpo);

            stmt.setInt(3, status.getCodPlano());
            stmt.setBoolean(4, status.isPlanoAtivo());

            if (status.getCodFidelidade() != null) {
                stmt.setInt(5, status.getCodFidelidade());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("***    Status inserido com sucesso no TitanFit!   ***");

        } catch (SQLException e) {
            System.out.println("***       Erro ao inserir status no banco:        ***" + e.getMessage());
        }
    }

    @Override
    public Status buscarPorId(Integer codStatus) {
        String sql = "SELECT * FROM status_aluno WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codStatus);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("***           Erro ao buscar status:              ***" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Status> listarTodos() {
        String sql = "SELECT * FROM status_aluno";
        List<Status> listaStatus = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaStatus.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao listar status:              ***" + e.getMessage());
        }

        return listaStatus;
    }

    @Override
    public void atualizar(Status status) {
        String sql = "UPDATE status_aluno SET " +
                "cpf_aluno = ?, " +
                "cod_plano = ?, " +
                "plano_ativo = ?, " +
                "cod_fidelidade = ? " +
                "WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = status.getCpfAluno() != null
                    ? status.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);
            stmt.setInt(2, status.getCodPlano());
            stmt.setBoolean(3, status.isPlanoAtivo());

            if (status.getCodFidelidade() != null) {
                stmt.setInt(4, status.getCodFidelidade());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, status.getCodStatus());

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer codStatus) {
        String sql = "DELETE FROM status_aluno WHERE cod_status = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codStatus);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("***    Status deletado com sucesso do TitanFit!   ***");
            } else {
                System.out.println("***  Nenhum status encontrado com esse codigo.    ***");
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao deletar status:             ***" + e.getMessage());
        }
    }

    /**
     * Busca o status do aluno pelo CPF.
     * Usado para verificar plano ativo na inscricao.
     *
     * @param cpfAluno CPF do aluno
     * @return Status encontrado, ou null se nao existir
     */
    public Status buscarPorCpf(String cpfAluno) {
        String sql = "SELECT * FROM status_aluno WHERE cpf_aluno = ? LIMIT 1";
        String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfLimpo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("***       Erro ao buscar status do aluno:         ***" + e.getMessage());
        }
        return null;
    }

    /**
     * Retorna o proximo codigo de status disponivel (max + 1).
     * Usado para auto-incremento no cadastro de alunos.
     *
     * @return Proximo codigo disponivel (1 se tabela vazia)
     */
    public int proximoCodigo() {
        String sql = "SELECT COALESCE(MAX(cod_status), 0) + 1 FROM status_aluno";
        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("***     Erro ao obter proximo codigo de status:   ***" + e.getMessage());
        }
        return 1;
    }

    private Status mapear(ResultSet rs) throws SQLException {
        Status status = new Status();
        status.setCodStatus(rs.getInt("cod_status"));
        status.setCpfAluno(rs.getString("cpf_aluno"));
        status.setCodPlano(rs.getInt("cod_plano"));
        status.setPlanoAtivo(rs.getBoolean("plano_ativo"));

        int codFid = rs.getInt("cod_fidelidade");
        status.setCodFidelidade(rs.wasNull() ? null : codFid);

        return status;
    }
}
 