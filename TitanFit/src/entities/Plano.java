package entities;

import java.util.List;
import java.util.ArrayList;

public class Plano {
	private int codPlano;
	private String categoria;
	private double valor;
	private List<String> beneficios;
	private double pagamento;
	private int codFidelidade; // Corrigido: int para corresponder a Fidelidade.codFidelidade

	public Plano() {
		this.beneficios = new ArrayList<>();
	}

	public Plano(int codPlano, String categoria, double valor, List<String> beneficios, double pagamento,
	             int codFidelidade) {
		this.codPlano = codPlano;
		this.categoria = categoria;
		this.valor = valor;
		this.beneficios = beneficios;
		this.pagamento = pagamento;
		this.codFidelidade = codFidelidade;
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

	public double getPagamento() {
		return pagamento;
	}

	public void setPagamento(double pagamento) {
		this.pagamento = pagamento;
	}

	public int getCodFidelidade() {
		return codFidelidade;
	}

	public void setCodFidelidade(int codFidelidade) {
		this.codFidelidade = codFidelidade;
	}

	public void adicionarBeneficio(String beneficio) {
		this.beneficios.add(beneficio);
	}

	@Override
	public String toString() {
		return "Plano [codPlano=" + codPlano + ", categoria=" + categoria + ", valor=" + valor + ", beneficios="
				+ beneficios + ", pagamento=" + pagamento + ", codFidelidade=" + codFidelidade + "]";
	}
}