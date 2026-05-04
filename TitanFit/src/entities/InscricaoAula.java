package entities;

public class InscricaoAula {
    private int codInscricao;
    private int codAula;
    private String cpfAluno;
    private String horario;

    public InscricaoAula() {
    }

    public InscricaoAula(int codInscricao, int codAula, String cpfAluno, String horario) {
        this.codInscricao = codInscricao;
        this.codAula = codAula;
        this.cpfAluno = cpfAluno;
        this.horario = horario; }

    public int getCodInscricao() {
        return codInscricao;
    }

    public void setCodInscricao(int codInscricao) {
        this.codInscricao = codInscricao;
    }

    public int getCodAula() {
        return codAula;
    }

    public void setCodAula(int codAula) {
        this.codAula = codAula;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "InscricaoAula{" +
                "codInscricao=" + codInscricao +
                ", codAula=" + codAula +
                ", cpfAluno='" + cpfAluno + '\'' +
                ", horario='" + horario + '\'' +
                '}';
    }
}
