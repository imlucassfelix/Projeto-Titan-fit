package repositoryDB;

import java.util.List;

/**
 * Interface que define o contrato comum dos repositorios de dados.
 * Todos os DAOs implementam estes metodos basicos de persistencia.
 *
 * <p>Aplicacao do conceito de Interface (POO) — define um contrato
 * que todos os repositories devem seguir, garantindo padronizacao
 * e facilitando manutencao futura.</p>
 *
 * @param <T>  Tipo da entidade gerenciada pelo repositorio (ex: Aluno, Instrutor)
 * @param <ID> Tipo do identificador da entidade (Integer para codigos, String para CPFs)
 *
 * @author Lucas Felix, Lucas Rodrigues, Mateus Santos, Ryan Vinicius
 * @version 1.0
 */
public interface Persistivel<T, ID> {

    /**
     * Insere uma nova entidade no banco de dados.
     *
     * @param entidade Objeto a ser persistido
     */
    void inserir(T entidade);

    /**
     * Busca uma entidade pelo seu identificador unico.
     *
     * @param id Identificador unico da entidade
     * @return Entidade encontrada, ou null se nao existir
     */
    T buscarPorId(ID id);

    /**
     * Lista todas as entidades cadastradas no banco de dados.
     *
     * @return Lista de entidades (vazia se nao houver registros)
     */
    List<T> listarTodos();

    /**
     * Atualiza os dados de uma entidade existente no banco de dados.
     *
     * @param entidade Objeto com os dados atualizados
     */
    void atualizar(T entidade);

    /**
     * Remove uma entidade do banco de dados pelo seu identificador.
     *
     * @param id Identificador da entidade a ser removida
     */
    void deletar(ID id);
}