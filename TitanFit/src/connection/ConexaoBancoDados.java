package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDados {
	private static final String URL     = "jdbc:mysql://localhost:3306/titanfit";
    private static final String USUARIO = "root";
    private static final String SENHA   = "";
 
    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
        return conexao;
    }
}
