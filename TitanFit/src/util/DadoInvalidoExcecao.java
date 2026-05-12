package util;

/** * Excecao personalizada para erros de validacao de dados. * Personalizando Excecoes. *  * @version 1.0 */
public class DadoInvalidoExcecao extends Exception {
    public DadoInvalidoExcecao(String mensagem) {
        super(mensagem);
    }

}
