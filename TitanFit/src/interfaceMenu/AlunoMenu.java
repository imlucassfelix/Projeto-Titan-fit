package interfaceMenu;

import entities.Aluno;
import repositoyDB.AlunoDB;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AlunoMenu {
    private Scanner sc;

	public AlunoMenu(Scanner sc) {
        this.sc = sc;
    }
	
	public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("***      TITANFIT - ALUNOS           ***");
            System.out.println("========================================");
            System.out.println("[1] - Cadastrar Aluno                ***");
            System.out.println("[2] - Listar Alunos                  ***");
            System.out.println("[3] - Atualizar Aluno                ***");
            System.out.println("[4] - Gerenciar Frequencia           ***");
            System.out.println("[5] - Remover Aluno                  ***");
            System.out.println("[0] - Voltar                         ***");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("========================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    sc.nextLine();
                    System.out.println(" CADASTRO DE ALUNOS");
                    System.out.println(" Digite o nome do aluno: ");
                    String nomeAluno = sc.nextLine();
                    System.out.println(" Digite o CPF do aluno (11 dígitos): ");
                    String cpfAluno = sc.nextLine();
                    System.out.println(" Sexo (M/F/I): ");
                    String sexo = sc.nextLine();
                    System.out.println(" Data de nascimento (dd/mm/aaaa): ");
                    String dataNascStr = sc.nextLine();
                    LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.println(" Telefone: ");
                    String telefoneAluno = sc.nextLine();
                    System.out.println(" Digite um endereço de e-mail: ");
                    String emailAluno = sc.nextLine();
                    LocalDate dataMatricula = LocalDate.now();

                    Aluno novoAluno = new Aluno(cpfAluno, telefoneAluno, nomeAluno, emailAluno, dataNascimentoAluno, dataMatricula, sexo);

                    System.out.println("Aluno criado: " + novoAluno);
                    AlunoDB alunoDB = new AlunoDB();
                    alunoDB.inserir(novoAluno);
                    break;

                case 2:
                    System.out.println(" Listagem em breve");
                    break;
                case 3:
                    System.out.println(" Atualizacao em breve");
                    break;
                case 4:
                    System.out.println(" aguarde ...");
                    break;
                case 5:
                    System.out.println(" Remocao em breve");
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
            System.out.println();
        } while (opcao != 0);
    }
}
