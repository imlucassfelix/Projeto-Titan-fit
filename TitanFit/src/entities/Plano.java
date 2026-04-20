package entities;

public class Plano {
	private int codPlano;
	private String categoria;
	private double valor;
	private String beneficios;
	private double pagamento;
	private String codFidelidade;
	
	public Plano() {
	}

	public Plano(int codPlano, String categoria, double valor, String beneficios, double pagamento,
			String codFidelidade) {
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

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public double getPagamento() {
		return pagamento;
	}

	public void setPagamento(double pagamento) {
		this.pagamento = pagamento;
	}

	public String getCodFidelidade() {
		return codFidelidade;
	}

	public void setCodFidelidade(String codFidelidade) {
		this.codFidelidade = codFidelidade;
	}

	@Override
	public String toString() {
		return "Plano [codPlano=" + codPlano + ", categoria=" + categoria + ", valor=" + valor + ", beneficios="
				+ beneficios + ", pagamento=" + pagamento + ", codFidelidade=" + codFidelidade + "]";
	}
	
	
}
