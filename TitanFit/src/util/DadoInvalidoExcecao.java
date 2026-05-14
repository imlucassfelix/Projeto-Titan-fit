package util;

/**
 * Excecao personalizada para erros de validacao de dados.
 *
 * <p>Lancada pelos metodos de {@link Validador} sempre que um valor
 * informado pelo usuario nao atende as regras de negocio definidas
 * para o sistema Titan Fit (CPF invalido, email mal formatado,
 * campo obrigatorio em branco, etc.).</p>
 *
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * try {
 *     Validador.validarCpf(cpf);
 * } catch (DadoInvalidoExcecao e) {
 *     System.out.println("Erro: " + e.getMessage());
 * }
 * }</pre>
 *
 * @author Lucas Felix, Lucas Rodrigues, Mateus Santos, Ryan Vinicius
 * @version 1.1
 * @see Validador
 */
public class DadoInvalidoExcecao extends Exception {

    /**
     * Constroi uma nova excecao com a mensagem de erro informada.
     *
     * @param mensagem Descricao do erro de validacao
     */
    public DadoInvalidoExcecao(String mensagem) {
        super(mensagem);
    }
}