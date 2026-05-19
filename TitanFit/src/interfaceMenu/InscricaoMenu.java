package interfaceMenu;

/**
 * Submenu de inscricoes e matriculas no Titan Fit.
 *
 * @author Lucas Rodrigues
 * @version 1.0
 */

import util.MetodosInscricao;
import java.util.Scanner;

public class InscricaoMenu {
    private Scanner sc;

    public InscricaoMenu(Scanner sc) {
            this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("=======================================================");
            System.out.println("***             INSCRICOES e MATRICULAS             ***");
            System.out.println("=======================================================");
            System.out.println("[1] - Realizar Nova Inscrição                       ***");
            System.out.println("[2] - Listar Turmas                                 ***");
            System.out.println("[3] - Alterar Turma/Horario                         ***");
            System.out.println("[4] - Remover Matrícula                             ***");
            System.out.println("[0] - Voltar                                        ***");
            System.out.println("=======================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("=======================================================");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosInscricao.novaInscricao(sc);
                    break;

                case 2:
                    MetodosInscricao.listarTurma(sc);
                    break;

                case 3:
                    MetodosInscricao.alterarTurma(sc);
                    break;

                case 4:
                    MetodosInscricao.removerTurma(sc);
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
