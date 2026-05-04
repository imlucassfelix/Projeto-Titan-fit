package interfaceMenu;

import entities.Plano;
import repositoyDB.PlanoDB;
import util.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanoMenu {
	private Scanner sc;
	
	public PlanoMenu(Scanner sc) {
		this.sc = sc;
	}

	public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("***             TITANFIT - PLANOS              ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Plano                          ***");
            System.out.println("[2] - Listar Planos                            ***");
            System.out.println("[3] - Alterar Plano                            ***");
            System.out.println("[4] - Remover Plano                            ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("==================================================");
                    System.out.println("***             CADASTRAR PLANO                ***");
                    System.out.println("==================================================");

                    System.out.print("***              Código do Plano:              ***");
                    int codPlano = sc.nextInt(); sc.nextLine();

                    System.out.print("***         Categoria do Plano:                ***");
                    String categoria = sc.nextLine();
                    if (!Validador.campoObrigatorio(categoria)) {
                        System.out.println("**   Erro: A categoria é obrigatória!          **");
                        break;
                    }

                    System.out.print("***              Valor do Plano: R$            ***");
                    double valor = sc.nextDouble(); sc.nextLine();

                    System.out.print("***      Quantos benefícios deseja adicionar?  ***");
                    int qtdBeneficios = sc.nextInt(); sc.nextLine();

                    List<String> beneficios = new ArrayList<>();
                    for (int i = 1; i <= qtdBeneficios; i++) {
                        System.out.print("***                Benefício " + i + ":               ***");
                        String beneficio = sc.nextLine();
                        if (Validador.campoObrigatorio(beneficio)) {
                            beneficios.add(beneficio);
                        }
                    }

                    System.out.print("***            Valor de Pagamento: R$          ***");
                    double pagamento = sc.nextDouble(); sc.nextLine();

                    System.out.print("***           Código de Fidelidade:            ***");
                    String codFidelidade = sc.nextLine();
                    if (!Validador.campoObrigatorio(codFidelidade)) {
                        System.out.println("**  Erro: O código de fidelidade é obrigatório! **");
                        break;
                    }

                    Plano novoPlano = new Plano(codPlano, categoria, valor, beneficios, pagamento, codFidelidade);
                    PlanoDB planoDB = new PlanoDB();
                    planoDB.inserir(novoPlano);
                    System.out.println("***       Plano cadastrado com sucesso!        ***");
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
