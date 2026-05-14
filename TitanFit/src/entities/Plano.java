package entities;

/**
 * Representa um plano de assinatura disponivel para os alunos.
 *
 * <p>Cada plano possui categoria, valor, beneficios, forma de pagamento,
 * vinculo com {@link Fidelidade} e duracao em meses. A duracao e usada
 * para calcular o vencimento do plano do aluno.</p>
 *
 * @author Lucas Felix
 * @version 1.0
 * @see Fidelidade
 * @see Status
 * @see repositoryDB.PlanoDB
 */

import java.util.List;
import java.util.ArrayList;

public class Plano {
	private int codPlano;
	private String categoria;
	private double valor;
	private List<String> beneficios;
	private int codFidelidade; // Corrigido: int para corresponder a Fidelidade.codFidelidade
	private int duracaoMeses;  // Duração do plano em meses — usado para calcular vencimento

	public Plano() {
		this.beneficios = new ArrayList<>();
	}

	public Plano(int codPlano, String categoria, double valor, List<String> beneficios, int codFidelidade, int duracaoMeses) {
		this.codPlano = codPlano;
		this.categoria = categoria;
		this.valor = valor;
		this.beneficios = beneficios;
		this.codFidelidade = codFidelidade;
		this.duracaoMeses = duracaoMeses;
	}

	public int getCodPlano() {
		return codPlano;
	}

	public void setCodPlano(int codPlano) {
		this.codPlano = codPlano;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<String> getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(List<String> beneficios) {
		this.beneficios = beneficios;
	}

	public int getCodFidelidade() {
		return codFidelidade;
	}

	public void setCodFidelidade(int codFidelidade) {
		this.codFidelidade = codFidelidade;
	}

	public int getDuracaoMeses() {
		return duracaoMeses;
	}

	public void setDuracaoMeses(int duracaoMeses) {
		this.duracaoMeses = duracaoMeses;
	}

	@Override
	public String toString() {
		return "Plano [codPlano=" + codPlano + ", categoria=" + categoria + ", valor=" + valor + ", beneficios="
				+ beneficios + ", codFidelidade=" + codFidelidade
				+ ", duracaoMeses=" + duracaoMeses + "]";
	}
}