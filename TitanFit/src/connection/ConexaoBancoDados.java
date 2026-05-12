package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDados {

    // Lê a system property; se não existir, usa "titanfit" como padrão
    private static final String DB_NAME = System.getProperty("titanfit.db.name", "titanfit");
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco [" + DB_NAME + "]: " + e.getMessage());
        }
        return conexao;
    }

    // Método usado pelos testes para verificar qual banco está ativo
    public static String getDbName() {
        return DB_NAME;
    }
}
