package interfaceMenu;

import util.Validador;
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
            System.out.println("==================================================");
            System.out.println("***             TITANFIT - ALUNOS              ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Aluno                          ***");
            System.out.println("[2] - Listar Alunos                            ***");
            System.out.println("[3] - Atualizar Aluno                          ***");
            System.out.println("[4] - Gerenciar Frequencia                     ***");
            System.out.println("[5] - Remover Aluno                            ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("***             CADASTRO DE ALUNOS             ***");
                    System.out.println("***           Digite o nome do aluno:          ***");
                    String nomeAluno = sc.nextLine();
                    if
                    (!Validador.campoObrigatorio(nomeAluno)) {
                        System.out.println("***         Nome nao pode ser vazio!           ***");
                        break;
                    }

                    System.out.println("***    Digite o CPF do aluno (11 dígitos):     ***");
                    String cpfAluno = sc.nextLine();
                    if (!Validador.validarCpf(cpfAluno)) {
                        System.out.println("**  CPF invalido! Digite 11 digitos numericos.  **");
                        break;
                    }

                    System.out.println("***              Sexo (M/F/I):                 ***");
                    String sexo = sc.nextLine();
                    if (!Validador.validarSexo(sexo)) {
                        System.out.println("***      Sexo invalido! Digite M, F ou I.      ***");
                        break;
                    }

                    System.out.println("***      Data de nascimento (dd/mm/aaaa):      ***");
                    String dataNascStr = sc.nextLine();
                    if (!Validador.validarData(dataNascStr)) {
                        System.out.println("**   Data invalida! Use o formato dd/mm/aaaa.   **");
                        break;
                    }
                    LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    System.out.println("***                 Telefone:                  ***");
                    String telefoneAluno = sc.nextLine();
                    if (!Validador.validarTelefone(telefoneAluno)) {
                        System.out.println("*  Telefone invalido! Use 11 digitos numericos.  *");
                        break;
                    }

                    System.out.println("***       Digite um endereço de e-mail:        ***");
                    String emailAluno = sc.nextLine();
                    if (!Validador.validarEmail(emailAluno)) {
                        System.out.println("***  Email invalido! Digite um email valido.   ***");
                        break;
                    }

                    LocalDate dataMatricula = LocalDate.now();

                    Aluno novoAluno = new Aluno(cpfAluno, telefoneAluno, nomeAluno, emailAluno, dataNascimentoAluno, dataMatricula, sexo);

                    System.out.println("***              Aluno criado:                 ***\n" + novoAluno);
                    AlunoDB alunoDB = new AlunoDB();
                    alunoDB.inserir(novoAluno);
                    break;

                case 2:
                    System.out.println("***              LISTA DE ALUNOS               ***");
                    AlunoDB ListarDB = new AlunoDB();
                    var lista = ListarDB.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("***          Nenhum aluno cadastrado.          ***");
                    } else {
                        for (Aluno a : lista) {
                            System.out.println("Nome: " + a.getNomeAluno() + " | CPF: " + a.getCpfAluno());
                        }
                    }
                    break;

                case 3:
                    System.out.println(" Atualizacao em breve");
                    break;

                case 4:
                    System.out.println(" aguarde ...");
                    break;

                case 5:
                    System.out.print("*** Digite o CPF do aluno que deseja remover:  ***");
                    String cpfRemover = sc.nextLine();
                    AlunoDB RemoverDB = new AlunoDB();
                    RemoverDB.deletar(cpfRemover);
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
