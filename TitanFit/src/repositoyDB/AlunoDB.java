package repositoyDB;
import entities.Aluno;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import connection.ConexaoBancoDados;

public class AlunoDB {
	public void inserir(Aluno aluno) {
		// Dica: Liste as colunas explicitamente. É mais seguro se você mudar a tabela depois.
		String sql = "INSERT INTO aluno (cpf_aluno, nome_aluno, email_aluno, telefone_aluno, data_nascimento, data_matricula, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		// O try-with-resources garante que a conexão e o statement sejam fechados sozinhos
		try (Connection conn = ConexaoBancoDados.conectar();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, aluno.getCpfAluno());
			stmt.setString(2, aluno.getNomeAluno());
			stmt.setString(3, aluno.getEmailAluno());
			stmt.setString(4, aluno.getTelefoneAluno());
			stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
			stmt.setDate(6, Date.valueOf(aluno.getDataMatricula()));
			stmt.setString(7, aluno.getSexo());

			stmt.executeUpdate();
			System.out.println("***  Aluno inserido com sucesso no TitanFit!   ***");

		} catch (SQLException e) {
			// Em projetos profissionais, costumamos lançar uma exceção personalizada ou usar um Logger
			System.out.println("***       Erro ao inserir aluno no banco:      ***" + e.getMessage());
		}
	}
	
	public Aluno buscarPorId(String cpfAluno) {
		return null;
		
	}
	
	public List<Aluno> listarTodos(){
		return null;
		
	}
	
	public void atualizar(Aluno aluno) {
		
	}
	
	public void deletar(String cpfAluno) {
		
	}
}
