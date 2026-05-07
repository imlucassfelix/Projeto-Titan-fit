package entities;

import java.time.LocalDate;

// 1. ABSTRAÇÃO: Uma classe que não pode ser instanciada diretamente, serve de molde.
public abstract class Pessoa {
    protected String email;
    protected LocalDate dataNascimento;
    protected String sexo;

    public Pessoa() {
    }

    public Pessoa(String email, LocalDate dataNascimento, String sexo) {
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    // 2. POLIMORFISMO (Preparação): Método abstrato que cada filho vai implementar do seu jeito
    public abstract String obterIdentificacao();
}