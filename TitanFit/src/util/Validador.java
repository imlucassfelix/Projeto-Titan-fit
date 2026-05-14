package util;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validador {

    public static void validarCpf(String cpf) throws DadoInvalidoExcecao {
        if (cpf == null || cpf.trim().isEmpty())
            throw new DadoInvalidoExcecao("CPF nao pode ser vazio.");
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11)
            throw new DadoInvalidoExcecao("CPF invalido! Digite 11 digitos.");
    }

    public static void validarSexo(String sexo) throws DadoInvalidoExcecao {
        if (sexo == null || sexo.trim().isEmpty())
            throw new DadoInvalidoExcecao("Sexo nao pode ser vazio.");
        if (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F") && !sexo.equalsIgnoreCase("I"))
            throw new DadoInvalidoExcecao("Sexo invalido! Digite M, F ou I.");
    }

    // BUG #1 e #2 CORRIGIDOS: ResolverStyle.STRICT + padrao uuuu
    public static void validarData(String data) throws DadoInvalidoExcecao {
        if (data == null)
            throw new DadoInvalidoExcecao("Data nao pode ser nula.");
        try {
            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(data, fmt);
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoExcecao("Data invalida! Use dd/mm/aaaa.");
        }
    }

    public static void campoObrigatorio(String valor, String nomeCampo) throws DadoInvalidoExcecao {
        if (valor == null || valor.trim().isEmpty())
            throw new DadoInvalidoExcecao(nomeCampo + " nao pode ser vazio.");
    }

    public static void validarTelefone(String tel) throws DadoInvalidoExcecao {
        if (tel == null)
            throw new DadoInvalidoExcecao("Telefone nao pode ser nulo.");
        String t = tel.replaceAll("[^0-9]", "");
        if (t.length() != 10 && t.length() != 11)
            throw new DadoInvalidoExcecao("Telefone invalido! Use 10 ou 11 digitos.");
    }

    // BUG #4 e #5 CORRIGIDOS: regex reescrita
    public static void validarEmail(String email) throws DadoInvalidoExcecao {
        if (email == null)
            throw new DadoInvalidoExcecao("Email nao pode ser nulo.");
        String regex =
                "^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*"
                        + "@"
                        + "[A-Za-z0-9]([A-Za-z0-9-]*[A-Za-z0-9])?"
                        + "(\\.[A-Za-z0-9]([A-Za-z0-9-]*[A-Za-z0-9])?)*"
                        + "\\.[A-Za-z]{2,}$";
        if (!email.matches(regex))
            throw new DadoInvalidoExcecao("Email invalido!");
    }

    public static void validarCapacidade(int cap) throws DadoInvalidoExcecao {
        if (cap < 1 || cap > 50)
            throw new DadoInvalidoExcecao("Capacidade deve estar entre 1 e 50.");
    }

    // BUG #3 CORRIGIDO: ResolverStyle.STRICT rejeita "24:00"
    public static void validarHorario(String h) throws DadoInvalidoExcecao {
        if (h == null)
            throw new DadoInvalidoExcecao("Horario nao pode ser nulo.");
        try {
            DateTimeFormatter fmt = DateTimeFormatter
                    .ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalTime.parse(h, fmt);
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoExcecao("Horario invalido! Use HH:mm.");
        }
    }

    // BUG #6 CORRIGIDO: desconto negativo infla o valor final do plano
    public static void validarDesconto(double desconto) throws DadoInvalidoExcecao {
        if (desconto < 0)
            throw new DadoInvalidoExcecao("Desconto nao pode ser negativo.");
    }
}