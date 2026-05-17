package connection;

public class TesteConexao {
    public static void main(String[] args) {
        var conn = ConexaoBancoDados.conectar();

        if (conn != null) {
            System.out.println("Conexao realizada com sucesso!");
        } else {
            System.out.println("Falha na conexao.");
        }
    }
}