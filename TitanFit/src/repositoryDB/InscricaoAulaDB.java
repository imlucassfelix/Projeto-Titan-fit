package repositoryDB;

import entities.InscricaoAula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade InscricaoAula.
 * Realiza operacoes CRUD na tabela 'inscricao_aula' do MySQL.
 * Implementa Persistivel para padronizacao dos repositorios.
 *
 * @author Lucas Rodrigues
 * @version 1.0
 */
public class InscricaoAulaDB implements Persistivel<InscricaoAula, Integer> {

    @Override
    public void inserir(InscricaoAula inscricao) {
        String sql = "INSERT INTO inscricao_aula (cod_inscricao, cod_aula, cpf_aluno, horario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscricao.getCodInscricao());
            stmt.setInt(2, inscricao.getCodAula());

            String cpfLimpo = inscricao.getCpfAluno() != null ? inscricao.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(3, cpfLimpo);

            stmt.setString(4, inscricao.getHorario());

            stmt.executeUpdate();
            System.out.println("***  Inscricao inserida com sucesso no TitanFit!  ***");

        } catch (SQLException e) {
            System.out.println("***     Erro ao inserir inscricao no banco:       ***" + e.getMessage());
        }
    }

    @Override
    public InscricaoAula buscarPorId(Integer codInscricao) {
        String sql = "SELECT * FROM inscricao_aula WHERE cod_inscricao = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codInscricao);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    InscricaoAula inscricao = new InscricaoAula();
                    inscricao.setCodInscricao(rs.getInt("cod_inscricao"));
                    inscricao.setCodAula(rs.getInt("cod_aula"));
                    inscricao.setCpfAluno(rs.getString("cpf_aluno"));
                    inscricao.setHorario(rs.getString("horario"));
                    return inscricao;
                }
            }
        } catch (SQLException e) {
            System.out.println("***          Erro ao buscar inscricao:            ***" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<InscricaoAula> listarTodos() {
        String sql = "SELECT * FROM inscricao_aula";
        List<InscricaoAula> listaInscricoes = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InscricaoAula inscricao = new InscricaoAula();
                inscricao.setCodInscricao(rs.getInt("cod_inscricao"));
                inscricao.setCodAula(rs.getInt("cod_aula"));
                inscricao.setCpfAluno(rs.getString("cpf_aluno"));
                inscricao.setHorario(rs.getString("horario"));

                listaInscricoes.add(inscricao);
            }

        } catch (SQLException e) {
            System.out.println("***          Erro ao listar inscricoes:           ***" + e.getMessage());
        }

        return listaInscricoes;
    }

    @Override
    public void atualizar(InscricaoAula inscricao) {
        String sql = "UPDATE inscricao_aula SET " +
                "cod_aula = ?, " +
                "cpf_aluno = ?, " +
                "horario = ? " +
                "WHERE cod_inscricao = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscricao.getCodAula());

            String cpfLimpo = inscricao.getCpfAluno() != null ? inscricao.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(2, cpfLimpo);

            stmt.setString(3, inscricao.getHorario());
            stmt.setInt(4, inscricao.getCodInscricao());

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer codInscricao) {
        String sql = "DELETE FROM inscricao_aula WHERE cod_inscricao = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codInscricao);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("***  Inscricao deletada com sucesso do TitanFit!  ***");
            } else {
                System.out.println("*** Nenhum registro encontrado com esse codigo.   ***");
            }

        } catch (SQLException e) {
            System.out.println("***          Erro ao deletar inscricao:           ***" + e.getMessage());
        }
    }

    /**
     * Conta quantos alunos estao inscritos em uma aula.
     * Usado para verificar a capacidade maxima antes de uma nova inscricao.
     *
     * @param codAula Codigo da aula
     * @return Total de alunos inscritos
     */
    public int contarInscritosPorAula(int codAula) {
        String sql = "SELECT COUNT(*) FROM inscricao_aula WHERE cod_aula = ?";
        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codAula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("***     Erro ao contar inscritos na aula:         ***" + e.getMessage());
        }
        return 0;
    }

    /**
     * Lista todas as inscricoes de um aluno pelo CPF.
     * Usado para verificar conflito de horario antes de uma nova inscricao.
     *
     * @param cpfAluno CPF do aluno
     * @return Lista de inscricoes do aluno
     */
    public List<InscricaoAula> listarPorCpf(String cpfAluno) {
        String sql = "SELECT * FROM inscricao_aula WHERE cpf_aluno = ?";
        List<InscricaoAula> lista = new ArrayList<>();
        String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;
        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfLimpo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InscricaoAula i = new InscricaoAula();
                    i.setCodInscricao(rs.getInt("cod_inscricao"));
                    i.setCodAula(rs.getInt("cod_aula"));
                    i.setCpfAluno(rs.getString("cpf_aluno"));
                    i.setHorario(rs.getString("horario"));
                    lista.add(i);
                }
            }
        } catch (SQLException e) {
            System.out.println("***     Erro ao listar inscricoes do aluno:       ***" + e.getMessage());
        }
        return lista;
    }

    /**
     * Conta o total de inscricoes de um aluno.
     * Usado para exibir estatisticas na listagem de alunos.
     *
     * @param cpfAluno CPF do aluno
     * @return Total de inscricoes
     */
    public int contarInscricoesPorCpf(String cpfAluno) {
        String sql = "SELECT COUNT(*) FROM inscricao_aula WHERE cpf_aluno = ?";
        String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;
        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfLimpo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("***   Erro ao contar inscricoes do aluno:         ***" + e.getMessage());
        }
        return 0;
    }
}