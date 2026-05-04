package interfaceMenu;

import entities.Instrutor;
import repositoyDB.InstrutorDB;
import util.Validador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InstrutorMenu {
	private Scanner sc;

	public InstrutorMenu(Scanner sc) {
		this.sc = sc;
	}

	public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("***            TITANFIT - INSTRUTOR            ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Instrutor                      ***");
            System.out.println("[2] - Listar Instrutores                       ***");
            System.out.println("[3] - Atualizar Instrutor                      ***");
            System.out.println("[4] - Remover Instrutor                        ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("==================================================");
                    System.out.println("***          CADASTRAR INSTRUTOR               ***");
                    System.out.println("==================================================");

                    System.out.print("***             CPF do Instrutor:              ***");
                    String cpfInstrutor = sc.nextLine();
                    if (!Validador.validarCpf(cpfInstrutor)) {
                        System.out.println("**  CPF inválido! Digite 11 dígitos numéricos.  **");
                        break;
                    }

                    System.out.print("***               Nome completo:               ***");
                    String nomeInstrutor = sc.nextLine();
                    if (!Validador.campoObrigatorio(nomeInstrutor)) {
                        System.out.println("**     Erro: O nome é obrigatório!             **");
                        break;
                    }

                    System.out.print("***                   Email:                   ***");
                    String emailInstrutor = sc.nextLine();
                    if (!Validador.validarEmail(emailInstrutor)) {
                        System.out.println("**   Email inválido! Digite um email válido.   **");
                        break;
                    }

                    System.out.print("***          Data de nascimento (dd/mm/aaaa):  ***");
                    String dataNascStr = sc.nextLine();
                    if (!Validador.validarData(dataNascStr)) {
                        System.out.println("**   Data inválida! Use o formato dd/mm/aaaa.  **");
                        break;
                    }
                    LocalDate dataNascimento = LocalDate.parse(dataNascStr,
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    System.out.print("***                Sexo (M/F/I):               ***");
                    String sexo = sc.nextLine();
                    if (!Validador.validarSexo(sexo)) {
                        System.out.println("**     Sexo inválido! Digite M, F ou I.        **");
                        break;
                    }

                    System.out.print("***               Especialidade:               ***");
                    String especialidade = sc.nextLine();
                    if (!Validador.campoObrigatorio(especialidade)) {
                        System.out.println("**   Erro: A especialidade é obrigatória!      **");
                        break;
                    }

                    System.out.print("***          Salário do Instrutor:             ***");
                    double salario = sc.nextDouble(); sc.nextLine();

                    Instrutor novoInstrutor = new Instrutor(cpfInstrutor, nomeInstrutor, emailInstrutor,
                            dataNascimento, sexo, especialidade, salario);

                    InstrutorDB instrutorDB = new InstrutorDB();
                    instrutorDB.inserir(novoInstrutor);
                    System.out.println("***     Instrutor cadastrado com sucesso!      ***");
                    break;

                case 2:
                    System.out.println(" Listagem em breve");
                    break;
                case 3:
                    System.out.println(" Atualizacao em breve");
                    break;
                case 4:
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
