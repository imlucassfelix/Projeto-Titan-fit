package util;

import entities.Equipamentos;
import repositoyDB.EquipamentosDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosEquipamentos {
    private static Scanner sc = new Scanner(System.in);

    public static void cadastrarEquipamentos(){
        System.out.println("==================================================");
        System.out.println("*** CADASTRAR EQUIPAMENTO             ***");
        System.out.println("==================================================");
        System.out.print("*** Código da Máquina:            ***\n");
        int codMaquina = sc.nextInt(); sc.nextLine();

        System.out.print("*** Nome do Equipamento:            ***\n");
        String nomeEquipamento = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeEquipamento)) {
            System.out.println("** Erro: O nome do equipamento é obrigatório!  **");
            return;
        }

        System.out.print("*** Modelo:                   ***\n");
        String modelo = sc.nextLine();
        if (!Validador.campoObrigatorio(modelo)) {
            System.out.println("** Erro: O modelo é obrigatório!           **");
            return;
        }

        System.out.print("*** Estado (Bom/Regular/Ruim):        ***\n");
        String estado = sc.nextLine();
        if (!Validador.campoObrigatorio(estado)) {
            System.out.println("** Erro: O estado é obrigatório!           **");
            return;
        }

        System.out.print("*** Data de Aquisição (dd/mm/aaaa):    ***\n");
        String dataAquisStr = sc.nextLine();
        if (!Validador.validarData(dataAquisStr)) {
            System.out.println("** Data inválida! Use o formato dd/mm/aaaa.  **");
            return;
        }
        LocalDate dataAquisicao = LocalDate.parse(dataAquisStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Equipamentos novoEquipamento = new Equipamentos(codMaquina, nomeEquipamento, modelo, estado, dataAquisicao);
        new EquipamentosDB().inserir(novoEquipamento);
    }

    public static void listarEquipamentos(){
        System.out.println("==================================================");
        System.out.println("*** LISTA DE EQUIPAMENTOS             ***");
        System.out.println("==================================================");
        var lista = new EquipamentosDB().listarTodos();

        if (lista.isEmpty()) {
            System.out.println("*** Nenhum equipamento cadastrado.      ***");
        } else {
            for (Equipamentos e : lista) {
                System.out.println("Código: " + e.getCodMaquina());
                System.out.println("Nome: " + e.getNome());
                System.out.println("Modelo: " + e.getModelo());
                System.out.println("Estado: " + e.getEstado());
                System.out.println("Data Aquisição: " + e.getDataAquisicao());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void gerenciarEquipamentos(){
        System.out.println("==================================================");
        System.out.println("*** ATUALIZAR EQUIPAMENTO              ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código do Equipamento para editar:***\n");
        int codBusca = sc.nextInt(); sc.nextLine();

        EquipamentosDB db = new EquipamentosDB();
        Equipamentos equipamento = db.buscarPorId(codBusca);

        if (equipamento == null) {
            System.out.println("Erro: Equipamento com código " + codBusca + " não encontrado!");
            return;
        }

        System.out.println("Editando Equipamento: " + equipamento.getNome());

        System.out.print("*** Novo Estado (Bom/Regular/Ruim) (Enter para manter): ***\n");
        String novoEstado = sc.nextLine();
        if (!novoEstado.isEmpty()) equipamento.setEstado(novoEstado);

        db.atualizar(equipamento);
    }

    public static void removerEquipamentos(){
        System.out.println("==================================================");
        System.out.println("*** REMOVER EQUIPAMENTO              ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código do Equipamento para remover:***\n");
        int codRemover = sc.nextInt(); sc.nextLine();
        new EquipamentosDB().deletar(codRemover);
    }
}