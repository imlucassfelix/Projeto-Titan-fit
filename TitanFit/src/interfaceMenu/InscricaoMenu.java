package interfaceMenu;

import java.util.ArrayList;
import java.util.List;

import util.MetodosInscricao;
import util.Validador;
import java.util.Scanner;
import entities.InscricaoAula;


public class InscricaoMenu {
    private Scanner sc;
    private List<InscricaoAula> listaInscricoes = new ArrayList<>();

    public InscricaoMenu(Scanner sc) {
            this.sc = sc;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("==================================================");
            System.out.println("***          INSCRICOES e MATRICULAS           ***");
            System.out.println("[1] - Realizar Nova Inscrição                  ***");
            System.out.println("[2] - Listar Turmas                            ***");
            System.out.println("[3] - Alterar Turma/Horario                    ***");
            System.out.println("[4] - Remover Matrícula                        ***");
            System.out.println("[0] - Voltar                                   ***");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: \n");
            System.out.println("==================================================");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    MetodosInscricao.novaInscricao();

                case 2:
                    MetodosInscricao.listarTurma();

                case 3:
                    MetodosInscricao.alterarTurma();

                case 4:
                    MetodosInscricao.removerTurma();

                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
