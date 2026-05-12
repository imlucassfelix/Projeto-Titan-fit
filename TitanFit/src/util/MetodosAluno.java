package util;

import entities.Aluno;
import entities.Frequenta;
import entities.Plano;
import entities.Status;
import repositoryDB.AlunoDB;
import repositoryDB.FrequentaDB;
import repositoryDB.InscricaoAulaDB;
import repositoryDB.PlanoDB;
import repositoryDB.StatusDB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MetodosAluno {

    public static void cadastrarAlunos(Scanner sc){
        System.out.println("==================================================");
        System.out.println("***           CADASTRO DE ALUNOS               ***");
        System.out.println("==================================================");
        try {
            System.out.print("***      Digite o nome do aluno:               ***\n");
            String nomeAluno = sc.nextLine();
            Validador.campoObrigatorio(nomeAluno, "Nome");

            System.out.print("***  Digite o CPF do aluno (11 dígitos):       ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("***        Sexo (M/F/I):                      ***\n");
            String sexo = sc.nextLine();
            Validador.validarSexo(sexo);

            System.out.print("*** Data de nascimento (dd/mm/aaaa):      ***\n");
            String dataNascStr = sc.nextLine();
            Validador.validarData(dataNascStr);
            LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("*** Telefone:                  ***\n");
            String telefoneAluno = sc.nextLine();
            Validador.validarTelefone(telefoneAluno);

            System.out.print("*** Digite um endereço de e-mail:        ***\n");
            String emailAluno = sc.nextLine();
            Validador.validarEmail(emailAluno);

            LocalDate dataMatricula = LocalDate.now();

            Aluno novoAluno = new Aluno(cpfAluno, nomeAluno, emailAluno, telefoneAluno, dataNascimentoAluno, dataMatricula, sexo);
            new AlunoDB().inserir(novoAluno);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void listarAlunos(Scanner sc){
        System.out.println("==================================================");
        System.out.println("***           LISTA DE ALUNOS                  ***");
        System.out.println("==================================================");
        var lista = new AlunoDB().listarTodos();
        if (lista.isEmpty()) {
            System.out.println("***         Nenhum aluno cadastrado.           ***");
        } else {
            FrequentaDB frequentaDB = new FrequentaDB();
            InscricaoAulaDB inscricaoAulaDB = new InscricaoAulaDB();
            StatusDB statusDB = new StatusDB();
            PlanoDB planoDB = new PlanoDB();

            for (Aluno a : lista) {
                System.out.println(a.obterIdentificacao());
                System.out.println("Email: " + a.getEmailAluno());
                System.out.println("Telefone: " + a.getTelefoneAluno());
                System.out.println("Matrícula: " + a.getDataMatricula());

                // Estatística: frequências
                List<Frequenta> frequencias = frequentaDB.listarPorCpf(a.getCpfAluno());
                System.out.println("Total de visitas: " + frequencias.size());
                if (!frequencias.isEmpty()) {
                    // listarPorCpf já retorna ordenado por data DESC
                    System.out.println("Última visita: " + frequencias.get(0).getDataEntrada());
                } else {
                    System.out.println("Última visita: nenhuma registrada");
                }

                // Estatística: aulas inscritas
                int totalAulas = inscricaoAulaDB.contarInscricoesPorCpf(a.getCpfAluno());
                System.out.println("Aulas inscritas: " + totalAulas);

                // Estatística: status do plano
                Status status = statusDB.buscarPorCpf(a.getCpfAluno());
                if (status != null) {
                    Plano plano = planoDB.buscarPorId(status.getCodPlano());
                    if (plano != null) {
                        LocalDate vencimento = a.getDataMatricula().plusMonths(plano.getDuracaoMeses());
                        String statusPlano = LocalDate.now().isAfter(vencimento) ? "VENCIDO" : "ATIVO";
                        System.out.println("Plano: " + plano.getCategoria()
                                + " | Status: " + statusPlano
                                + " | Vencimento: " + vencimento);
                    } else {
                        System.out.println("Plano: não encontrado (cód. " + status.getCodPlano() + ")");
                    }
                } else {
                    System.out.println("Plano: nenhum plano vinculado");
                }

                System.out.println("--------------------------------------------------");
            }
        }
    }

    public static void atualizarAlunos(Scanner sc){
        System.out.println("==================================================");
        System.out.println("***          ATUALIZAR DADOS DO ALUNO          ***");
        System.out.println("==================================================");
        try {
            System.out.print("*  Digite o CPF do aluno que deseja atualizar:   *\n");
            String cpfBusca = sc.nextLine();
            Validador.validarCpf(cpfBusca);

            AlunoDB db = new AlunoDB();
            Aluno alunoParaAtualizar = db.buscarPorId(cpfBusca);

            if (alunoParaAtualizar == null) {
                System.out.println("Erro: Aluno com CPF " + cpfBusca + " não encontrado!");
                return;
            }

            System.out.println("Editando aluno: " + alunoParaAtualizar.getNomeAluno());

            System.out.print("*** Novo Nome (ou aperte Enter para manter):   ***\n");
            String novoNome = sc.nextLine();
            if (!novoNome.isEmpty()) alunoParaAtualizar.setNomeAluno(novoNome);

            System.out.print("*** Novo Email (ou aperte Enter para manter):  ***\n");
            String novoEmail = sc.nextLine();
            if (!novoEmail.isEmpty()) {
                Validador.validarEmail(novoEmail);
                alunoParaAtualizar.setEmailAluno(novoEmail);
            }

            System.out.print("** Novo Telefone (ou aperte Enter para manter): **\n");
            String novoTelefone = sc.nextLine();
            if (!novoTelefone.isEmpty()) {
                Validador.validarTelefone(novoTelefone);
                alunoParaAtualizar.setTelefoneAluno(novoTelefone);
            }

            db.atualizar(alunoParaAtualizar);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void frequenciaAlunos(Scanner sc){
        System.out.println("==================================================");
        System.out.println("***          REGISTRAR FREQUÊNCIA              ***");
        System.out.println("==================================================");
        try {
            System.out.print("***        Digite o CPF do aluno:              ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("***        Digite o Código da Aula:            ***\n");
            int codAula = sc.nextInt(); sc.nextLine();

            LocalDate dataHoje = LocalDate.now();
            LocalTime horaAgora = LocalTime.now();

            Frequenta freq = new Frequenta(cpfAluno, codAula, dataHoje, horaAgora);
            new FrequentaDB().inserir(freq);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }

    public static void removerAlunos(Scanner sc){
        System.out.println("==================================================");
        System.out.println("***             REMOVER ALUNO                  ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Digite o CPF do aluno que deseja remover:  ***\n");
            String cpfRemover = sc.nextLine();
            Validador.validarCpf(cpfRemover);
            new AlunoDB().deletar(cpfRemover);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validação: " + e.getMessage() + " ***");
        }
    }
}