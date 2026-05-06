package entities;

public class Status {
	private int codStatus;
	private String cpfAluno;
	private int codPlano;
	private boolean planoAtivo;
	
	public Status() {
	}

	public Status(int codStatus, String cpfAluno, int codPlano, boolean planoAtivo) {
		this.codStatus = codStatus;
		this.cpfAluno = cpfAluno;
		this.codPlano = codPlano;
		this.planoAtivo = planoAtivo;

	}

	public int getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(int codStatus) {
		this.codStatus = codStatus;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public int getCodPlano() {
		return codPlano;
	}

	public void setCodPlano(int codPlano) {
		this.codPlano = codPlano;
	}

	public boolean isPlanoAtivo() {
		return planoAtivo;
	}

	public void setPlanoAtivo(boolean planoAtivo) {
		this.planoAtivo = planoAtivo;
	}



	@Override
	public String toString() {
		return "Status [codStatus=" + codStatus + ", cpfAluno=" + cpfAluno + ", codPlano=" + codPlano + ", planoAtivo="
				+ planoAtivo + ", planoInativo=" + "]";
	}


}
