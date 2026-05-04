package connection;

public class TesteConexao {
    public static void main(String[] args) {
        // Como estão no mesmo pacote, basta chamar direto:
        var conn = ConexaoBancoDados.conectar();

        if (conn != null) {
            System.out.println("Conexão realizada com sucesso!");
        } else {
            System.out.println("Falha na conexão.");
        }
    }
}
