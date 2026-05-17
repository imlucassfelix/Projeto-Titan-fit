package util;

/**
 * Metodos de negocio para o modulo de Equipamentos do Titan Fit.
 *
 * <p>Gerencia as operacoes de cadastro, listagem, atualizacao
 * de estado e remocao de equipamentos.</p>
 *
 * <p>Alteracao v2.0: o campo cod_maquina foi removido do formulario
 * de cadastro. O codigo e gerado automaticamente por
 * {@link repositoryDB.EquipamentosDB#proximoCodigo()}.</p>
 *
 * @author Mateus Santos
 * @version 2.0
 * @see interfaceMenu.EquipamentosMenu
 */

import entities.Equipamentos;
import repositoryDB.EquipamentosDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosEquipamentos {

    public static void cadastrarEquipamentos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***         CADASTRAR EQUIPAMENTO              ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Nome do Equipamento:                       ***\n");
            String nomeEquipamento = sc.nextLine();
            Validador.campoObrigatorio(nomeEquipamento, "Nome do equipamento");

            System.out.print("*** Modelo:                                    ***\n");
            String modelo = sc.nextLine();
            Validador.campoObrigatorio(modelo, "Modelo");

            System.out.print("*** Estado (Bom/Regular/Ruim):                 ***\n");
            String estado = sc.nextLine();
            Validador.campoObrigatorio(estado, "Estado");

            System.out.print("*** Data de Aquisicao (dd/mm/aaaa):            ***\n");
            String dataAquisStr = sc.nextLine();
            Validador.validarData(dataAquisStr);
            LocalDate dataAquisicao = LocalDate.parse(dataAquisStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // cod_maquina = 0 aqui — sera sobrescrito por proximoCodigo() dentro de EquipamentosDB.inserir()
            Equipamentos novoEquipamento = new Equipamentos(0, nomeEquipamento, modelo, estado, dataAquisicao);
            new EquipamentosDB().inserir(novoEquipamento);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    public static void listarEquipamentos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***         LISTA DE EQUIPAMENTOS              ***");
        System.out.println("==================================================");
        var lista = new EquipamentosDB().listarTodos();

        if (lista.isEmpty()) {
            System.out.println("***      Nenhum equipamento cadastrado.        ***");
        } else {
            for (Equipamentos e : lista) {
                System.out.println("Codigo: " + e.getCodMaquina());
                System.out.println("Nome: " + e.getNome());
                System.out.println("Modelo: " + e.getModelo());
                System.out.println("Estado: " + e.getEstado());
                System.out.println("Data Aquisicao: " + e.getDataAquisicao());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void gerenciarEquipamentos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***         ATUALIZAR EQUIPAMENTO              ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Digite o Codigo do Equipamento para editar:***\n");
            int codBusca = sc.nextInt(); sc.nextLine();

            EquipamentosDB db = new EquipamentosDB();
            Equipamentos equipamento = db.buscarPorId(codBusca);

            if (equipamento == null) {
                System.out.println("Erro: Equipamento com codigo " + codBusca + " nao encontrado!");
                return;
            }

            System.out.println("Editando Equipamento: " + equipamento.getNome());

            System.out.print("*** Novo Estado (Bom/Regular/Ruim) (Enter para manter): ***\n");
            String novoEstado = sc.nextLine();
            if (!novoEstado.isEmpty()) {
                Validador.campoObrigatorio(novoEstado, "Estado");
                equipamento.setEstado(novoEstado);
            }

            db.atualizar(equipamento);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    public static void removerEquipamentos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***          REMOVER EQUIPAMENTO               ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Codigo do Equipamento para remover:***\n");
        int codRemover = sc.nextInt(); sc.nextLine();
        new EquipamentosDB().deletar(codRemover);
    }
}