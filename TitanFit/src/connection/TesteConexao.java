package connection;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

public class TesteConexao {
    public static void main(MysqlxDatatypes.Scalar.String[] args) {
        // Como estão no mesmo pacote, basta chamar direto:
        var conn = ConexaoBancoDados.conectar();

        if (conn != null) {
            System.out.println("Conexão realizada com sucesso!");
        } else {
            System.out.println("Falha na conexão.");
        }
    }
}
