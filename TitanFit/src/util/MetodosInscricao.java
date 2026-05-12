package util;

import entities.Aula;
import entities.InscricaoAula;
import entities.Plano;
import entities.Status;
import repositoryDB.AulaDB;
import repositoryDB.InscricaoAulaDB;
import repositoryDB.PlanoDB;
import repositoryDB.StatusDB;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MetodosInscricao {

    public static void novaInscricao(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** NOVA INSCRICAO               ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Código de Inscrição:             ***\n");
            int codInscricao = sc.nextInt(); sc.nextLine();

            System.out.print("*** Código da Aula:               ***\n");
            int codAula = sc.nextInt(); sc.nextLine();

            System.out.print("*** Digite o CPF do aluno (11 dígitos):     ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("*** Horário (HH:mm):                 ***\n");
            String horario = sc.nextLine();
            Validador.validarHorario(horario);

            // -------------------------------------------------------
            // REGRA 1: Verificar se o plano do aluno está ativo
            // -------------------------------------------------------
            StatusDB statusDB = new StatusDB();
            Status status = statusDB.buscarPorCpf(cpfAluno);

            if (status == null) {
                System.out.println("** Erro: Aluno não possui plano vinculado.     **");
                System.out.println("** Cadastre um status/plano antes de inscrever.**");
                return;
            }

            PlanoDB planoDB = new PlanoDB();
            Plano plano = planoDB.buscarPorId(status.getCodPlano());

            if (plano == null) {
                System.out.println("** Erro: Plano vinculado ao aluno não encontrado.**");
                return;
            }

            // Buscar dataMatricula do aluno para calcular vencimento
            repositoryDB.AlunoDB alunoDB = new repositoryDB.AlunoDB();
            entities.Aluno aluno = alunoDB.buscarPorId(cpfAluno);
            if (aluno == null) {
                System.out.println("** Erro: Aluno com CPF informado não encontrado.**");
                return;
            }

            LocalDate dataVencimento = aluno.getDataMatricula().plusMonths(plano.getDuracaoMeses());
            if (LocalDate.now().isAfter(dataVencimento)) {
                System.out.println("** Erro: Plano do aluno está VENCIDO!          **");
                System.out.println("** Data de vencimento: " + dataVencimento + "       **");
                System.out.println("** Renove o plano antes de realizar a inscrição.**");
                return;
            }

            // -------------------------------------------------------
            // REGRA 2: Verificar capacidade máxima da aula
            // -------------------------------------------------------
            AulaDB aulaDB = new AulaDB();
            Aula aula = aulaDB.buscarPorId(codAula);

            if (aula == null) {
                System.out.println("** Erro: Aula com código " + codAula + " não encontrada.**");
                return;
            }

            InscricaoAulaDB inscricaoAulaDB = new InscricaoAulaDB();
            int totalInscritos = inscricaoAulaDB.contarInscritosPorAula(codAula);

            if (totalInscritos >= aula.getCapacidadeMaxima()) {
                System.out.println("** Erro: Aula atingiu a capacidade máxima!     **");
                System.out.printf("** Capacidade: %d/%d alunos.%n", totalInscritos, aula.getCapacidadeMaxima());
                return;
            }

            // -------------------------------------------------------
            // REGRA 3: Verificar conflito de horário
            // -------------------------------------------------------
            List<InscricaoAula> inscricoesDoAluno = inscricaoAulaDB.listarPorCpf(cpfAluno);
            for (InscricaoAula i : inscricoesDoAluno) {
                if (horario.equals(i.getHorario()) && i.getCodAula() != codAula) {
                    System.out.println("** Erro: Conflito de horário detectado!        **");
                    System.out.println("** O aluno já está inscrito em outra aula      **");
                    System.out.println("** no horário " + horario + " (Aula cód.: " + i.getCodAula() + ").  **");
                    return;
                }
            }

            // -------------------------------------------------------
            // Todas as condições atendidas — confirmar inscrição
            // -------------------------------------------------------
            InscricaoAula novaInscricao = new InscricaoAula(codInscricao, codAula, cpfAluno, horario);
            inscricaoAulaDB.inserir(novaInscricao);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void listarTurma(Scanner sc){
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

    public static void alterarTurma(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** ALTERAR TURMA/HORÁRIO             ***");
        System.out.println("==================================================");
        try {
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
                Validador.validarHorario(novoHorario);
                inscricao.setHorario(novoHorario);
            }

            db.atualizar(inscricao);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void removerTurma(Scanner sc){
        System.out.println("==================================================");
        System.out.println("*** REMOVER MATRÍCULA              ***");
        System.out.println("==================================================");
        System.out.print("*** Digite o Código da Inscrição para remover: ***\n");
        int codRemover = sc.nextInt(); sc.nextLine();
        new InscricaoAulaDB().deletar(codRemover);
    }
}
