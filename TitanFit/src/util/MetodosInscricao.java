package util;

import entities.InscricaoAula;
import repositoyDB.InscricaoAulaDB;
import java.util.Scanner;

public class MetodosInscricao {
    private static Scanner sc = new Scanner(System.in);

    public static void novaInscricao(){
        System.out.println("==================================================");
        System.out.println("*** NOVA INSCRICAO               ***");
        System.out.println("==================================================");

        System.out.print("*** Código de Inscrição:             ***\n");
        int codInscricao = sc.nextInt(); sc.nextLine();

        System.out.print("*** Código da Aula:               ***\n");
        int codAula = sc.nextInt(); sc.nextLine();

        System.out.print("*** Digite o CPF do aluno (11 dígitos):     ***\n");
        String cpfAluno = sc.nextLine();
        if (!Validador.validarCpf(cpfAluno)) {
            System.out.println("** CPF invalido! Digite 11 digitos numericos.  **");
            return;
        }

        System.out.print("*** Horário (HH:mm):                 ***\n");
        String horario = sc.nextLine();
        if (!Validador.validarHorario(horario)) {
            System.out.println("Erro: Horário inválido! (Use o formato HH:mm).");
            return;
        }

        InscricaoAula novaInscricao = new InscricaoAula(codInscricao, codAula, cpfAluno, horario);
        new InscricaoAulaDB().inserir(novaInscricao);
    }

    public static void listarTurma(){
        System.out.println("==================================================");
        System.out.println("*** LISTA DE INSCRIÇÕES            ***");
        System.out.println("==================================================");

        var lista = new InscricaoAulaDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("*** Nenhuma inscrição registrada.      ***");
        } else {
            for (InscricaoAula i : lista) {
                System.out.println("Cod. Inscrição: " + i.getCodInscricao());
                System.out.println("Cod. Aula: " + i.getCodAula());
                System.out.println("CPF Aluno: " + i.getCpfAluno());
                System.out.println("Horário: " + i.getHorario());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void alterarTurma(){
        System.out.println("==================================================");
        System.out.println("*** ALTERAR TURMA/HORÁRIO             ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Codigo da Inscricao para editar:  ***\n");
        int codBusca = sc.nextInt(); sc.nextLine();

        InscricaoAulaDB db = new InscricaoAulaDB();
        InscricaoAula inscricao = db.buscarPorId(codBusca);

        if (inscricao == null) {
            System.out.println("Erro: Inscrição com código " + codBusca + " não encontrada!");
            return;
        }

        System.out.print("*** Novo Horário HH:mm (Enter para manter):    ***\n");
        String novoHorario = sc.nextLine();
        if (!novoHorario.isEmpty()) {
            if(Validador.validarHorario(novoHorario)){
                inscricao.setHorario(novoHorario);
            } else {
                System.out.println("Horário inválido, alteração cancelada.");
                return;
            }
        }

        db.atualizar(inscricao);
    }

    public static void removerTurma(){
        System.out.println("==================================================");
        System.out.println("*** REMOVER MATRÍCULA              ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código da Inscrição para remover: ***\n");
        int codRemover = sc.nextInt(); sc.nextLine();
        new InscricaoAulaDB().deletar(codRemover);
    }
}