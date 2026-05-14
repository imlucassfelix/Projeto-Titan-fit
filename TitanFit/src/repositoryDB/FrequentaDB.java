package repositoryDB;

import entities.Frequenta;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import connection.ConexaoBancoDados;

/**
 * Repositorio de acesso ao banco para a entidade Frequenta.
 * Realiza operacoes CRUD na tabela 'frequenta' do MySQL.
 * Implementa {@link Persistivel} para padronizacao dos repositorios.
 *
 * <p>A chave primaria desta tabela e composta por (cpf_aluno, cod_aula,
 * data_entrada, hora_entrada), portanto o metodo {@link #buscarPorId(Object)}
 * nao e aplicavel diretamente — use {@link #buscar(String, int, LocalDate)}
 * para buscas precisas.</p>
 *
 * @author Lucas Rodrigues
 * @version 2.0
 */
public class FrequentaDB implements Persistivel<Frequenta, Object> {

    // -------------------------------------------------------
    // Persistivel — metodos obrigatorios pela interface
    // -------------------------------------------------------

    /**
     * Insere um novo registro de frequencia no banco de dados.
     *
     * @param frequenta Objeto Frequenta com os dados a inserir
     */
    @Override
    public void inserir(Frequenta frequenta) {
        String sql = "INSERT INTO frequenta (cpf_aluno, cod_aula, data_entrada, hora_entrada) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = frequenta.getCpfAluno() != null
                    ? frequenta.getCpfAluno().replace(".", "").replace("-", "") : null;
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

    /**
     * Nao aplicavel diretamente — a tabela 'frequenta' usa chave primaria composta.
     * Use {@link #buscar(String, int, LocalDate)} para buscas por chave composta.
     *
     * @param id ignorado
     * @return sempre {@code null}; use o metodo {@code buscar} especifico
     */
    @Override
    public Frequenta buscarPorId(Object id) {
        System.out.println("*** Use buscar(cpf, codAula, data) para tabela frequenta. ***");
        return null;
    }

    /**
     * Lista todos os registros de frequencia cadastrados no banco de dados.
     *
     * @return Lista de Frequenta (vazia se nao houver registros)
     */
    @Override
    public List<Frequenta> listarTodos() {
        String sql = "SELECT * FROM frequenta";
        List<Frequenta> listaFrequencias = new ArrayList<>();

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaFrequencias.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("***           Erro ao listar frequências:            ***" + e.getMessage());
        }

        return listaFrequencias;
    }

    /**
     * Atualiza a hora de entrada de um registro de frequencia existente.
     * A atualizacao e feita pela chave composta (cpf_aluno, cod_aula, data_entrada).
     *
     * @param frequenta Objeto Frequenta com a nova hora de entrada
     */
    @Override
    public void atualizar(Frequenta frequenta) {
        String sql = "UPDATE frequenta SET hora_entrada = ? " +
                "WHERE cpf_aluno = ? AND cod_aula = ? AND data_entrada = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTime(1, Time.valueOf(frequenta.getHoraEntrada()));
            String cpfLimpo = frequenta.getCpfAluno() != null
                    ? frequenta.getCpfAluno().replace(".", "").replace("-", "") : null;
            stmt.setString(2, cpfLimpo);
            stmt.setInt(3, frequenta.getCodAula());
            stmt.setDate(4, Date.valueOf(frequenta.getDataEntrada()));

            stmt.executeUpdate();
            System.out.println("*** Dados atualizados com sucesso no TitanFit! ***");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    /**
     * Nao aplicavel diretamente — use {@link #deletar(String, int, LocalDate)}.
     *
     * @param id ignorado
     */
    @Override
    public void deletar(Object id) {
        System.out.println("*** Use deletar(cpf, codAula, data) para tabela frequenta. ***");
    }

    // -------------------------------------------------------
    // Metodos especificos da tabela frequenta (chave composta)
    // -------------------------------------------------------

    /**
     * Busca um registro de frequencia pela chave composta.
     *
     * @param cpfAluno   CPF do aluno
     * @param codAula    Codigo da aula
     * @param dataEntrada Data de entrada
     * @return Frequenta encontrada, ou null se nao existir
     */
    public Frequenta buscar(String cpfAluno, int codAula, LocalDate dataEntrada) {
        String sql = "SELECT * FROM frequenta WHERE cpf_aluno = ? AND cod_aula = ? AND data_entrada = ?";

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;
            stmt.setString(1, cpfLimpo);
            stmt.setInt(2, codAula);
            stmt.setDate(3, Date.valueOf(dataEntrada));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("***           Erro ao buscar frequência:             ***" + e.getMessage());
        }
        return null;
    }

    /**
     * Remove um registro de frequencia pela chave composta.
     *
     * @param cpfAluno    CPF do aluno
     * @param codAula     Codigo da aula
     * @param dataEntrada Data de entrada
     */
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

    /**
     * Retorna todas as frequencias de um aluno, ordenadas da mais recente para a mais antiga.
     * Usado para calcular total de visitas e ultima visita.
     *
     * @param cpfAluno CPF do aluno
     * @return Lista de frequencias do aluno
     */
    public List<Frequenta> listarPorCpf(String cpfAluno) {
        String sql = "SELECT * FROM frequenta WHERE cpf_aluno = ? ORDER BY data_entrada DESC, hora_entrada DESC";
        List<Frequenta> lista = new ArrayList<>();
        String cpfLimpo = cpfAluno != null ? cpfAluno.replace(".", "").replace("-", "") : null;

        try (Connection conn = ConexaoBancoDados.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfLimpo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("***       Erro ao listar frequências do aluno:      ***" + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------
    // Utilitario privado
    // -------------------------------------------------------

    /**
     * Mapeia uma linha do ResultSet para um objeto Frequenta.
     *
     * @param rs ResultSet posicionado na linha desejada
     * @return Objeto Frequenta populado
     * @throws SQLException em caso de erro de leitura do ResultSet
     */
    private Frequenta mapear(ResultSet rs) throws SQLException {
        Frequenta f = new Frequenta();
        f.setCpfAluno(rs.getString("cpf_aluno"));
        f.setCodAula(rs.getInt("cod_aula"));
        f.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        f.setHoraEntrada(rs.getTime("hora_entrada").toLocalTime());
        return f;
    }
}