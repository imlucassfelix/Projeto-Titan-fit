package util;

/**
 * Metodos de negocio para o modulo de Alunos do Titan Fit.
 *
 * <p>Encapsula todas as operacoes de cadastro, listagem,
 * atualizacao, controle de frequencia e remocao de alunos,
 * orquestrando chamadas a {@link repositoryDB.AlunoDB} e
 * aos validadores.</p>
 *
 * <p>Alteracao v2.0: o cadastro de aluno agora inclui selecao de
 * plano e fidelidade (opcional), criando o {@link entities.Status}
 * automaticamente ao final do cadastro.</p>
 *
 * @author Lucas Rodrigues
 * @version 2.0
 * @see interfaceMenu.AlunoMenu
 */

import entities.Aluno;
import entities.Fidelidade;
import entities.Frequenta;
import entities.Plano;
import entities.Status;
import repositoryDB.AlunoDB;
import repositoryDB.FidelidadeDB;
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

    // -------------------------------------------------------
    // CADASTRAR ALUNO — inclui selecao de plano e fidelidade
    // -------------------------------------------------------
    public static void cadastrarAlunos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***           CADASTRO DE ALUNOS               ***");
        System.out.println("==================================================");
        try {
            // ── Dados pessoais ──────────────────────────────
            System.out.print("***      Digite o nome do aluno:               ***\n");
            String nomeAluno = sc.nextLine();
            Validador.campoObrigatorio(nomeAluno, "Nome");

            System.out.print("***  Digite o CPF do aluno (11 digitos):       ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("***        Sexo (M/F/I):                       ***\n");
            String sexo = sc.nextLine();
            Validador.validarSexo(sexo);

            System.out.print("*** Data de nascimento (dd/mm/aaaa):            ***\n");
            String dataNascStr = sc.nextLine();
            Validador.validarData(dataNascStr);
            LocalDate dataNascimentoAluno = LocalDate.parse(dataNascStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("*** Telefone:                                   ***\n");
            String telefoneAluno = sc.nextLine();
            Validador.validarTelefone(telefoneAluno);

            System.out.print("*** Digite um endereco de e-mail:               ***\n");
            String emailAluno = sc.nextLine();
            Validador.validarEmail(emailAluno);

            // ── Selecao de Plano ────────────────────────────
            System.out.println("==================================================");
            System.out.println("***            SELECAO DE PLANO               ***");
            System.out.println("==================================================");

            PlanoDB planoDB = new PlanoDB();
            List<Plano> planos = planoDB.listarTodos();

            if (planos.isEmpty()) {
                System.out.println("** ATENCAO: Nenhum plano cadastrado no sistema. **");
                System.out.println("** Cadastre um plano antes de registrar alunos. **");
                return;
            }

            System.out.println("Planos disponíveis:");
            System.out.println("--------------------------------------------------");
            for (Plano p : planos) {
                System.out.printf("[%d] %s — R$ %.2f/mes (%d meses)%n",
                        p.getCodPlano(), p.getCategoria(), p.getValor(), p.getDuracaoMeses());
                if (p.getBeneficios() != null && !p.getBeneficios().isEmpty()) {
                    System.out.println("    Beneficios: " + String.join(", ", p.getBeneficios()));
                }
            }
            System.out.println("--------------------------------------------------");
            System.out.print("*** Digite o codigo do plano desejado:         ***\n");
            int codPlanoEscolhido = sc.nextInt(); sc.nextLine();

            Plano planoEscolhido = planoDB.buscarPorId(codPlanoEscolhido);
            if (planoEscolhido == null) {
                System.out.println("** Erro: Plano com codigo " + codPlanoEscolhido + " nao encontrado! **");
                return;
            }

            // ── Selecao de Fidelidade (opcional) ───────────
            System.out.println("==================================================");
            System.out.println("***        FIDELIDADE (OPCIONAL)              ***");
            System.out.println("==================================================");

            FidelidadeDB fidelidadeDB = new FidelidadeDB();
            List<Fidelidade> fidelidades = fidelidadeDB.listarTodos();

            Integer codFidelidadeEscolhida = null;
            double desconto = 0.0;

            if (fidelidades.isEmpty()) {
                System.out.println("*** Nenhuma fidelidade disponivel no sistema.  ***");
            } else {
                System.out.print("*** Deseja aderir a uma fidelidade? (S/N):     ***\n");
                String querFidelidade = sc.nextLine().trim();

                if (querFidelidade.equalsIgnoreCase("S")) {
                    System.out.println("Fidelidades disponíveis:");
                    System.out.println("--------------------------------------------------");
                    for (Fidelidade f : fidelidades) {
                        System.out.printf("[%d] %s — Desconto: R$ %.2f — Periodo ate: %s%n",
                                f.getCodFidelidade(), f.getDescricao(),
                                f.getValorDesconto(), f.getPeriodo());
                    }
                    System.out.println("--------------------------------------------------");
                    System.out.print("*** Digite o codigo da fidelidade:            ***\n");
                    int codFid = sc.nextInt(); sc.nextLine();

                    Fidelidade fidEscolhida = fidelidadeDB.buscarPorId(codFid);
                    if (fidEscolhida == null) {
                        System.out.println("** Aviso: Fidelidade nao encontrada. Prosseguindo sem fidelidade. **");
                    } else {
                        codFidelidadeEscolhida = fidEscolhida.getCodFidelidade();
                        desconto = fidEscolhida.getValorDesconto();
                        System.out.printf("*** Fidelidade selecionada: %s (desconto R$ %.2f) ***%n",
                                fidEscolhida.getDescricao(), desconto);
                    }
                }
            }

            // ── Resumo do cadastro ──────────────────────────
            double valorFinal = Math.max(0, planoEscolhido.getValor() - desconto);
            System.out.println("==================================================");
            System.out.println("***           RESUMO DO CADASTRO              ***");
            System.out.println("==================================================");
            System.out.println("Aluno:   " + nomeAluno);
            System.out.println("CPF:     " + cpfAluno);
            System.out.printf("Plano:   %s — R$ %.2f/mes%n", planoEscolhido.getCategoria(), planoEscolhido.getValor());
            if (desconto > 0) {
                System.out.printf("Desconto fidelidade: -R$ %.2f%n", desconto);
                System.out.printf("Valor final:          R$ %.2f/mes%n", valorFinal);
            }
            System.out.println("Duracao: " + planoEscolhido.getDuracaoMeses() + " meses");
            System.out.println("--------------------------------------------------");
            System.out.print("*** Confirma o cadastro? (S/N):                ***\n");
            String confirmacao = sc.nextLine().trim();

            if (!confirmacao.equalsIgnoreCase("S")) {
                System.out.println("***          Cadastro cancelado.               ***");
                return;
            }

            // ── Persistencia: Aluno ────────────────────────
            LocalDate dataMatricula = LocalDate.now();
            Aluno novoAluno = new Aluno(cpfAluno, nomeAluno, emailAluno, telefoneAluno,
                    dataNascimentoAluno, dataMatricula, sexo);
            AlunoDB alunoDB = new AlunoDB();
            alunoDB.inserir(novoAluno);

            // ── Persistencia: Status (automatico) ──────────
            StatusDB statusDB = new StatusDB();
            int novoCodStatus = statusDB.proximoCodigo();
            Status novoStatus = new Status(novoCodStatus, cpfAluno, codPlanoEscolhido, true, codFidelidadeEscolhida);
            statusDB.inserir(novoStatus);

            System.out.println("==================================================");
            System.out.println("***     Aluno e plano cadastrados com sucesso! ***");
            System.out.println("==================================================");

        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    // -------------------------------------------------------
    // LISTAR ALUNOS
    // -------------------------------------------------------
    public static void listarAlunos(Scanner sc) {
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
            FidelidadeDB fidelidadeDB = new FidelidadeDB();

            for (Aluno a : lista) {
                System.out.println(a.obterIdentificacao());
                System.out.println("Email: " + a.getEmailAluno());
                System.out.println("Telefone: " + a.getTelefoneAluno());
                System.out.println("Matricula: " + a.getDataMatricula());

                // Frequencias
                List<Frequenta> frequencias = frequentaDB.listarPorCpf(a.getCpfAluno());
                System.out.println("Total de visitas: " + frequencias.size());
                if (!frequencias.isEmpty()) {
                    System.out.println("Ultima visita: " + frequencias.get(0).getDataEntrada());
                } else {
                    System.out.println("Ultima visita: nenhuma registrada");
                }

                // Aulas inscritas
                int totalAulas = inscricaoAulaDB.contarInscricoesPorCpf(a.getCpfAluno());
                System.out.println("Aulas inscritas: " + totalAulas);

                // Status do plano
                Status status = statusDB.buscarPorCpf(a.getCpfAluno());
                if (status != null) {
                    Plano plano = planoDB.buscarPorId(status.getCodPlano());
                    if (plano != null) {
                        LocalDate vencimento = a.getDataMatricula().plusMonths(plano.getDuracaoMeses());
                        String statusPlano = LocalDate.now().isAfter(vencimento) ? "VENCIDO" : "ATIVO";

                        double valorFinal = plano.getValor();
                        String infoFidelidade = "nenhuma";

                        if (status.temFidelidade()) {
                            Fidelidade fid = fidelidadeDB.buscarPorId(status.getCodFidelidade());
                            if (fid != null) {
                                valorFinal = Math.max(0, plano.getValor() - fid.getValorDesconto());
                                infoFidelidade = fid.getDescricao()
                                        + " (-R$ " + String.format("%.2f", fid.getValorDesconto()) + ")";
                            }
                        }

                        System.out.printf("Plano:       %s | R$ %.2f/mes%n",
                                plano.getCategoria(), valorFinal);
                        System.out.println("Fidelidade:  " + infoFidelidade);
                        System.out.println("Status:      " + statusPlano + " | Vencimento: " + vencimento);
                    } else {
                        System.out.println("Plano: nao encontrado (cod. " + status.getCodPlano() + ")");
                    }
                } else {
                    System.out.println("Plano: nenhum plano vinculado");
                }

                System.out.println("--------------------------------------------------");
            }
        }
    }

    // -------------------------------------------------------
    // ATUALIZAR ALUNO
    // -------------------------------------------------------
    public static void atualizarAlunos(Scanner sc) {
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
                System.out.println("Erro: Aluno com CPF " + cpfBusca + " nao encontrado!");
                return;
            }

            System.out.println("Editando aluno: " + alunoParaAtualizar.getNomeAluno());

            System.out.print("*** Novo Nome (Enter para manter):             ***\n");
            String novoNome = sc.nextLine();
            if (!novoNome.isEmpty()) alunoParaAtualizar.setNomeAluno(novoNome);

            System.out.print("*** Novo Email (Enter para manter):            ***\n");
            String novoEmail = sc.nextLine();
            if (!novoEmail.isEmpty()) {
                Validador.validarEmail(novoEmail);
                alunoParaAtualizar.setEmailAluno(novoEmail);
            }

            System.out.print("** Novo Telefone (Enter para manter):          **\n");
            String novoTelefone = sc.nextLine();
            if (!novoTelefone.isEmpty()) {
                Validador.validarTelefone(novoTelefone);
                alunoParaAtualizar.setTelefoneAluno(novoTelefone);
            }

            db.atualizar(alunoParaAtualizar);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    // -------------------------------------------------------
    // FREQUENCIA
    // -------------------------------------------------------
    public static void frequenciaAlunos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***          REGISTRAR FREQUENCIA              ***");
        System.out.println("==================================================");
        try {
            System.out.print("***        Digite o CPF do aluno:              ***\n");
            String cpfAluno = sc.nextLine();
            Validador.validarCpf(cpfAluno);

            System.out.print("***        Digite o Codigo da Aula:            ***\n");
            int codAula = sc.nextInt(); sc.nextLine();

            LocalDate dataHoje = LocalDate.now();
            LocalTime horaAgora = LocalTime.now();

            Frequenta freq = new Frequenta(cpfAluno, codAula, dataHoje, horaAgora);
            new FrequentaDB().inserir(freq);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }

    // -------------------------------------------------------
    // REMOVER ALUNO
    // -------------------------------------------------------
    public static void removerAlunos(Scanner sc) {
        System.out.println("==================================================");
        System.out.println("***             REMOVER ALUNO                  ***");
        System.out.println("==================================================");
        try {
            System.out.print("*** Digite o CPF do aluno que deseja remover:  ***\n");
            String cpfRemover = sc.nextLine();
            Validador.validarCpf(cpfRemover);
            new AlunoDB().deletar(cpfRemover);
        } catch (DadoInvalidoExcecao e) {
            System.out.println("*** Erro de validacao: " + e.getMessage() + " ***");
        }
    }
}