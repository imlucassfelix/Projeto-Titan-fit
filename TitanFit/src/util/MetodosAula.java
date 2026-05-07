package util;

import entities.Aula;
import java.util.Scanner;

public class MetodosAula {
    private static Scanner sc = new Scanner(System.in);

    public static void cadastrarAula(){
        System.out.println("*** CADASTRAR AULA               ***");
        System.out.print("*** Código da Aula:               ***\n");
        int codAula = sc.nextInt(); sc.nextLine();
        if (!Validador.campoObrigatorio(String.valueOf(codAula))) {
            System.out.println("*** Erro: Código é obrigatório!         ***");
            return;
        }

        System.out.print("*** Digite nome da aula:            ***\n");
        String nomeAula = sc.nextLine();
        if (!Validador.campoObrigatorio(nomeAula)) {
            System.out.println("** Erro: O nome da aula é obrigatória!      **");
            return;
        }

        System.out.print("*** Modalidade:                 ***\n");
        String modalidade = sc.nextLine();
        if (!Validador.campoObrigatorio(modalidade)) {
            System.out.println("** Erro: O nome da modalidade é obrigatório!   **");
            return;
        }

        System.out.print("*** Descrição da Aula:             ***\n");
        String descricaoAula = sc.nextLine();
        if (!Validador.campoObrigatorio(descricaoAula)) {
            System.out.println("** Erro: descricao da aula é obrigatória!    **");
            return;
        }

        System.out.print("*** Capacidade Máxima:              ***\n");
        int capacidadeMaxima = sc.nextInt(); sc.nextLine();
        if (!Validador.validarCapacidade(capacidadeMaxima)) {
            System.out.println("* Erro: A capacidade deve estar entre 1 e 50.   *");
            return;
        }

        System.out.print("*** CPF do Instrutor responsável:        ***\n");
        String cpfInstrutor = sc.nextLine();
        if (!Validador.validarCpf(cpfInstrutor)) {
            System.out.println("** CPF inválido! Digite 11 dígitos numéricos.  **");
            return;
        }

        Aula novaAula = new Aula(codAula, nomeAula, capacidadeMaxima, descricaoAula, modalidade, cpfInstrutor);
        new repositoyDB.AulaDB().inserir(novaAula);
    }

    public static void listarAula(){
        System.out.println("==================================================");
        System.out.println("*** LISTA DE AULAS               ***");
        System.out.println("==================================================");

        var lista = new repositoyDB.AulaDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("*** Nenhuma aula cadastrada.         ***");
        } else {
            for (Aula a : lista) {
                System.out.println("Código: " + a.getCodAula());
                System.out.println("Nome da Aula: " + a.getNomeAula());
                System.out.println("Modalidade: " + a.getModalidade());
                System.out.println("Capacidade Máxima: " + a.getCapacidadeMaxima());
                System.out.println("CPF Instrutor: " + a.getCpfInstrutor());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void editarAula(){
        System.out.println("==================================================");
        System.out.println("*** EDITAR AULA                ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código da Aula para editar:       ***\n");
        int codBusca = sc.nextInt(); sc.nextLine();

        repositoyDB.AulaDB db = new repositoyDB.AulaDB();
        Aula aula = db.buscarPorId(codBusca);

        if (aula == null) {
            System.out.println("Erro: Aula com código " + codBusca + " não encontrada!");
            return;
        }

        System.out.println("Editando Aula: " + aula.getNomeAula());

        System.out.print("*** Novo Nome (Enter para manter):             ***\n");
        String novoNome = sc.nextLine();
        if (!novoNome.isEmpty()) aula.setNomeAula(novoNome);

        System.out.print("*** Nova Modalidade (Enter para manter):       ***\n");
        String novaMod = sc.nextLine();
        if (!novaMod.isEmpty()) aula.setModalidade(novaMod);

        db.atualizar(aula);
    }

    public static void excluirAula(){
        System.out.println("==================================================");
        System.out.println("*** EXCLUIR AULA                ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código da Aula para remover:      ***\n");
        int codRemover = sc.nextInt(); sc.nextLine();

        new repositoyDB.AulaDB().deletar(codRemover);
    }
}