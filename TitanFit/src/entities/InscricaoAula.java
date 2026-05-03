package entities;

public class InscricaoAula {
    private int codInscricao;
    private int codAula;
    private String cpfAluno;
    private String horario;

    public InscricaoAula() {
    }

    public InscricaoAula(String cpfAluno, int codAula) {
        this.cpfAluno = cpfAluno;
        this.codAula = codAula;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public int getCodAula() {
        return codAula;
    }

    public void setCodAula(int codAula) {
        this.codAula = codAula;
    }

    @Override
    public String toString() {
        return "InscricaoAula{" +
                "cpfAluno='" + cpfAluno + '\'' +
                ", codAula=" + codAula +
                '}';
    }
}
