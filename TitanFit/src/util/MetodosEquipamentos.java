package util;

import entities.Equipamentos;
import repositoyDB.EquipamentosDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MetodosEquipamentos {
    private static Scanner sc;

    public static void cadastrarEquipamentos(){
        System.out.println("==================================================");
        System.out.println("***          CADASTRAR EQUIPAMENTO             ***");
        System.out.println("==================================================");
        System.out.print("***              Código da Máquina:            ***");
        int codMaquina = sc.nextInt(); sc.nextLine();

        System.out.print("***            Nome do Equipamento:            ***");
        String nomeEquipamento = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeEquipamento)) {
            System.out.println("**  Erro: O nome do equipamento é obrigatório!  **");
        }

        System.out.print("***                  Modelo:                   ***");
        String modelo = sc.nextLine();
        if (!Validador.campoObrigatorio(modelo)) {
            System.out.println("**     Erro: O modelo é obrigatório!           **");
        }

        System.out.print("***          Estado (Bom/Regular/Ruim):        ***");
        String estado = sc.nextLine();
        if (!Validador.campoObrigatorio(estado)) {
            System.out.println("**     Erro: O estado é obrigatório!           **");

        }

        System.out.print("***         Data de Aquisição (dd/mm/aaaa):    ***");
        String dataAquisStr = sc.nextLine();
        if (!Validador.validarData(dataAquisStr)) {
            System.out.println("**   Data inválida! Use o formato dd/mm/aaaa.  **");
        }
        LocalDate dataAquisicao = LocalDate.parse(dataAquisStr,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Equipamentos novoEquipamento = new Equipamentos(codMaquina, nomeEquipamento, modelo, estado, dataAquisicao);
        EquipamentosDB equipamentosDB = new EquipamentosDB();
        equipamentosDB.inserir(novoEquipamento);
        System.out.println("***     Equipamento cadastrado com sucesso!    ***");
    }

    public static void listarEquipamentos(){

    }

    public static void gerenciarEquipamentos(){

    }

    public static void removerEquipamentos(){

    }
}
