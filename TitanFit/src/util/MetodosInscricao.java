package util;

/**
 * Metodos de negocio para o modulo de Inscricoes do Titan Fit.
 *
 * <p>Implementa as tres regras de negocio criticas para inscricao:</p>
 * <ol>
 *   <li>Verificacao de plano ativo e nao vencido do aluno</li>
 *   <li>Verificacao de capacidade maxima da aula</li>
 *   <li>Verificacao de conflito de horario do aluno</li>
 * </ol>
 *
 * <p>Alteracao v2.0: o campo cod_inscricao foi removido do formulario.
 * O codigo e gerado automaticamente por
 * {@link repositoryDB.InscricaoAulaDB#proximoCodigo()}.</p>
 *
 * @author Lucas Rodrigues
 * @version 2.0
 * @see interfaceMenu.InscricaoMenu
 */

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

    public static void novaInscricao(Scanner sc) {
        System.out.println("=======================================================");
        System.out.println("***                NOVA INSCRICAO                   ***");
        System.out.println("=======================================================");
        try {
            System.out.print("***      Codigo da Aula:                            ***\n");
            int codAula = sc.nextInt(); sc.nextLine();

            System.out.print("***      Digite o CPF do aluno (11 digitos):        ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("***      Horario (HH:mm):                           ***\n");
            String horario = sc.nextLine();
            Validador.validarHorario(horario);

            // -------------------------------------------------------
            // REGRA 1: Verificar se o plano do aluno esta ativo
            // -------------------------------------------------------
            StatusDB statusDB = new StatusDB();
            Status status = statusDB.buscarPorCpf(cpfAluno);

            if (status == null) {
                System.out.println("Erro: Aluno nao possui plano vinculado.     ");
                System.out.println("Cadastre um status/plano antes de inscrever.");
                return;
            }

            PlanoDB planoDB = new PlanoDB();
            Plano plano = planoDB.buscarPorId(status.getCodPlano());

            if (plano == null) {
                System.out.println("Erro: Plano vinculado ao aluno nao encontrado.");
                return;
            }

            repositoryDB.AlunoDB alunoDB = new repositoryDB.AlunoDB();
            entities.Aluno aluno = alunoDB.buscarPorId(cpfAluno);
            if (aluno == null) {
                System.out.println("Erro: Aluno com CPF informado nao encontrado.");
                return;
            }

            LocalDate dataVencimento = aluno.getDataMatricula().plusMonths(plano.getDuracaoMeses());
            if (LocalDate.now().isAfter(dataVencimento)) {
                System.out.println("Erro: Plano do aluno esta VENCIDO!");
                System.out.println("Data de vencimento: " + dataVencimento + "");
                System.out.println("Renove o plano antes de realizar a inscricao.");
                return;
            }

            // -------------------------------------------------------
            // REGRA 2: Verificar capacidade maxima da aula
            // -------------------------------------------------------
            AulaDB aulaDB = new AulaDB();
            Aula aula = aulaDB.buscarPorId(codAula);

            if (aula == null) {
                System.out.println("Erro: Aula com codigo " + codAula + " nao encontrada.");
                return;
            }

            InscricaoAulaDB inscricaoAulaDB = new InscricaoAulaDB();
            int totalInscritos = inscricaoAulaDB.contarInscritosPorAula(codAula);

            if (totalInscritos >= aula.getCapacidadeMaxima()) {
                System.out.println("Erro: Aula atingiu a capacidade maxima!");
                System.out.printf("Capacidade: %d/%d alunos.%n", totalInscritos, aula.getCapacidadeMaxima());
                return;
            }

            // -------------------------------------------------------
            // REGRA 3: Verificar conflito de horario
            // -------------------------------------------------------
            List<InscricaoAula> inscricoesDoAluno = inscricaoAulaDB.listarPorCpf(cpfAluno);
            for (InscricaoAula i : inscricoesDoAluno) {
                if (horario.equals(i.getHorario()) && i.getCodAula() != codAula) {
                    System.out.println("Erro: Conflito de horario detectado! ");
                    System.out.println("O aluno ja esta inscrito em outra aula");
                    System.out.println("no horario " + horario + " (Aula cod.: " + i.getCodAula() + ").");
                    return;
                }
            }

            // -------------------------------------------------------
            // Todas as condicoes atendidas — confirmar inscricao
            // cod_inscricao = 0 aqui, sera gerado por proximoCodigo()
            // -------------------------------------------------------
            InscricaoAula novaInscricao = new InscricaoAula(0, codAula, cpfAluno, horario);
            inscricaoAulaDB.inserir(novaInscricao);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("Erro de validacao: " + e.getMessage() + " ");
        }
    }

    public static void listarTurma(Scanner sc) {
        System.out.println("=======================================================");
        System.out.println("***              LISTA DE INSCRICOES                ***");
        System.out.println("=======================================================");

        var lista = new InscricaoAulaDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("***          Nenhuma inscricao registrada.          ***");
        } else {
            for (InscricaoAula i : lista) {
                System.out.println("Cod. Inscricao: " + i.getCodInscricao());
                System.out.println("Cod. Aula: " + i.getCodAula());
                System.out.println("CPF Aluno: " + i.getCpfAluno());
                System.out.println("Horario: " + i.getHorario());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void alterarTurma(Scanner sc) {
        System.out.println("=======================================================");
        System.out.println("***              ALTERAR TURMA/HORARIO              ***");
        System.out.println("=======================================================");
        try {
            System.out.print("***      Digite o Codigo da Inscricao para editar:  ***\n");
            int codBusca = sc.nextInt(); sc.nextLine();

            InscricaoAulaDB db = new InscricaoAulaDB();
            InscricaoAula inscricao = db.buscarPorId(codBusca);

            if (inscricao == null) {
                System.out.println("Erro: Inscricao com codigo " + codBusca + " nao encontrada!");
                return;
            }

            System.out.print("***      Novo Horario HH:mm (Enter para manter):    ***\n");
            String novoHorario = sc.nextLine();
            if (!novoHorario.isEmpty()) {
                Validador.validarHorario(novoHorario);
                inscricao.setHorario(novoHorario);
            }

            db.atualizar(inscricao);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("Erro de validacao: " + e.getMessage() + " ");
        }
    }

    public static void removerTurma(Scanner sc) {
        System.out.println("=======================================================");
        System.out.println("***               REMOVER MATRICULA                 ***");
        System.out.println("=======================================================");
        System.out.print("***      Digite o Codigo da Inscricao para remover: ***\n");
        int codRemover = sc.nextInt(); sc.nextLine();
        new InscricaoAulaDB().deletar(codRemover);
    }
}