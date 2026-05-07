package interfaceMenu;
import entities.Equipamentos;
import repositoyDB.EquipamentosDB;
import util.MetodosAula;
import util.MetodosEquipamentos;
import util.Validador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class EquipamentosMenu {
	private Scanner sc;
	
	public EquipamentosMenu(Scanner sc) {
		this.sc = sc;
	}

	public void exibirMenu() {
        int opcao;
        do {
            System.out.println("");
            System.out.println("==================================================");
            System.out.println("***          TITANFIT - EQUIPAMENTOS           ***");
            System.out.println("==================================================");
            System.out.println("[1] - Cadastrar Equipamento                    ***");
            System.out.println("[2] - Listar Equipamentos                      ***");
            System.out.println("[3] - Gerenciar Equipamento                    ***");
            System.out.println("[4] - Remover Equipamento                      ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("==================================================");
                    System.out.println("***          CADASTRAR EQUIPAMENTO             ***");
                    System.out.println("==================================================");
                    System.out.print("***              Código da Máquina:            ***");
                    int codMaquina = sc.nextInt(); sc.nextLine();

                    System.out.print("***            Nome do Equipamento:            ***");
                    String nomeEquipamento = sc.nextLine();
                    if (!Validador.campoObrigatorio(nomeEquipamento)) {
                        System.out.println("**  Erro: O nome do equipamento é obrigatório!  **");
                        break;
                    }

                    System.out.print("***                  Modelo:                   ***");
                    String modelo = sc.nextLine();
                    if (!Validador.campoObrigatorio(modelo)) {
                        System.out.println("**     Erro: O modelo é obrigatório!           **");
                        break;
                    }

                    System.out.print("***          Estado (Bom/Regular/Ruim):        ***");
                    String estado = sc.nextLine();
                    if (!Validador.campoObrigatorio(estado)) {
                        System.out.println("**     Erro: O estado é obrigatório!           **");
                        break;
                    }

                    System.out.print("***         Data de Aquisição (dd/mm/aaaa):    ***");
                    String dataAquisStr = sc.nextLine();
                    if (!Validador.validarData(dataAquisStr)) {
                        System.out.println("**   Data inválida! Use o formato dd/mm/aaaa.  **");
                        break;
                    }
                    LocalDate dataAquisicao = LocalDate.parse(dataAquisStr,
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Equipamentos novoEquipamento = new Equipamentos(codMaquina, nomeEquipamento, modelo, estado, dataAquisicao);
                    EquipamentosDB equipamentosDB = new EquipamentosDB();
                    equipamentosDB.inserir(novoEquipamento);
                    System.out.println("***     Equipamento cadastrado com sucesso!    ***");
                    break;

                case 2:
                    MetodosEquipamentos.listarEquipamentos();
                case 3:
                    MetodosEquipamentos.gerenciarEquipamentos();
                case 4:
                    MetodosEquipamentos.removerEquipamentos();
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
