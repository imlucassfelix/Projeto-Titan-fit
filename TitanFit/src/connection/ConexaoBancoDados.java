package connection;

/**
 * Fabrica de conexoes JDBC para o banco de dados MySQL do Titan Fit.
 *
 * <p>As configuracoes de conexao sao lidas do arquivo {@code db.properties}
 * localizado no classpath. Cada desenvolvedor mantem seu proprio arquivo
 * local — ele nao deve ser commitado no repositorio Git.</p>
 *
 * <p>Exemplo de db.properties:</p>
 * <pre>
 * db.host=localhost
 * db.port=3306
 * db.name=titanfit
 * db.user=root
 * db.password=suaSenha
 * </pre>
 *
 * <p>Nao instanciavel — todos os metodos sao estaticos.</p>
 *
 * @author Lucas Félix
 * @version 2.0
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBancoDados {

    private static final Properties props = carregarPropriedades();

    private static Properties carregarPropriedades() {
        Properties p = new Properties();
        try (InputStream in = ConexaoBancoDados.class
                .getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                p.load(in);
            } else {
                System.out.println("*** db.properties nao encontrado, usando configuracao padrao ***");
            }
        } catch (IOException e) {
            System.out.println("*** Erro ao ler db.properties: " + e.getMessage() + " ***");
        }
        return p;
    }

    private static final String HOST = props.getProperty("db.host", "localhost");
    private static final String PORT = props.getProperty("db.port", "3306");
    private static final String DB   = props.getProperty("db.name", "titanfit");
    private static final String USER = props.getProperty("db.user", "root");
    private static final String PASS = props.getProperty("db.password", "");

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB
            + "?useTimezone=true&serverTimezone=UTC";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco [" + DB + "]: " + e.getMessage());
            return null;
        }
    }

    public static String getDbName() {
        return DB;
    }
}