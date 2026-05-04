package interfaceMenu;

import entities.Aula;
import util.Validador;
import java.util.Scanner;

public class AulaMenu {
	private Scanner sc;
	
	 public AulaMenu(Scanner sc) {
		 this.sc = sc;
	}

	 public void exibirMenu() {
	        int opcao;
	        do {
				System.out.println("");
	            System.out.println("========================================");
	            System.out.println("***       TITANFIT - AULAS           ***");
	            System.out.println("========================================");
	            System.out.println("[1] - Cadastrar Aula                 ***");
	            System.out.println("[2] - Listar Aulas                   ***");
	            System.out.println("[3] - Editar Aula/Treinos            ***");
	            System.out.println("[4] - Excluir Aula/Treinos           ***");
	            System.out.println("[0] - Voltar                         ***");
	            System.out.println("========================================");
	            System.out.print("Escolha uma opção: \n");
	            System.out.println("========================================");
	            
	            opcao = sc.nextInt(); sc.nextLine();

				switch (opcao) {
	                case 1:
						System.out.println("***               CADASTRAR AULA               ***");
						System.out.print("***              Código da Aula:               ***");
						int codAula = sc.nextInt(); sc.nextLine();

						System.out.print("***            Digite nome da aula:            ***");
						String nomeAula = sc.nextLine();
						if (!Validador.campoObrigatorio(nomeAula)) {
							System.out.println("**     Erro: O nome da aula é obrigatória!      **");
							break;
						}

						System.out.print("***                Modalidade:                 ***");
						String modalidade = sc.nextLine();
						if (!Validador.campoObrigatorio(modalidade)) {
							System.out.println("**  Erro: O nome da modalidade  é obrigatória!  **");
							break;
						}

						System.out.print("***             Descrição da Aula:             ***");
						String descricaoAula = sc.nextLine();
						if (!Validador.campoObrigatorio(descricaoAula)) {
							System.out.println("**    Erro: descricao da aula é obrigatória!    **");
							break;
						}

						System.out.print("***            Capacidade Máxima:              ***");
						int capacidadeMaxima = sc.nextInt(); sc.nextLine();
						if (!Validador.validarCapacidade(capacidadeMaxima)) {
							System.out.println("*  Erro: A capacidade deve estar entre 1 e 50.   *");
							break;
						}

						System.out.print("***       CPF do Instrutor responsável:        ***");
						String cpfInstrutor = sc.nextLine();
						if (!Validador.validarCpf(cpfInstrutor)) {
							System.out.println("**  CPF inválido! Digite 11 dígitos numéricos.  **");
							break;
						}

						Aula novaAula = new Aula(codAula, nomeAula, capacidadeMaxima, descricaoAula, modalidade, cpfInstrutor);
						System.out.println("***      Aula cadastrada com sucesso!          ***");
	                    break;

	                case 2:
	                    System.out.println(" Listagem em breve");
	                    break;
	                case 3:
	                    System.out.println(" Resgistrando em breve");
	                    break;
	                case 4:
	                    System.out.println(" Treinos em breve");
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
