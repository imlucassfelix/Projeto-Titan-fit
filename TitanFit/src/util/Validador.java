package util;

/**
 * Classe utilitaria com metodos estaticos de validacao de dados.
 *
 * <p>Centraliza todas as regras de validacao de entrada do sistema
 * Titan Fit — CPF, email, telefone, sexo, data, horario e capacidade.
 * Todos os metodos lancam {@link DadoInvalidoExcecao} em caso de
 * dados invalidos.</p>
 *
 * <p>Nao instanciavel — todos os metodos sao estaticos.</p>
 *
 * @author Lucas Felix, Lucas Rodrigues, Mateus Santos, Ryan Vinicius
 * @version 1.0
 * @see DadoInvalidoExcecao
 */

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Validador {

    public static void validarCpf(String cpf) throws DadoInvalidoExcecao {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new DadoInvalidoExcecao("CPF nao pode ser vazio.");
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            throw new DadoInvalidoExcecao("CPF invalido! Digite 11 digitos.");
        }
    }

    public static void validarSexo(String sexo) throws DadoInvalidoExcecao {
        if (sexo == null || sexo.trim().isEmpty()) {
            throw new DadoInvalidoExcecao("Sexo nao pode ser vazio.");
        }

        if (!sexo.equalsIgnoreCase("M") &&
                !sexo.equalsIgnoreCase("F") &&
                !sexo.equalsIgnoreCase("I")) {
            throw new DadoInvalidoExcecao("Sexo invalido! Digite M, F ou I.");
        }
    }

    public static void validarData(String data) throws DadoInvalidoExcecao {
        if (data == null) {
            throw new DadoInvalidoExcecao("Data nao pode ser nula.");
        }

        try {
            LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoExcecao("Data invalida! Use dd/mm/aaaa.");
        }
    }

    public static void campoObrigatorio(String valor, String nomeCampo) throws DadoInvalidoExcecao {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DadoInvalidoExcecao(nomeCampo + " nao pode ser vazio.");
        }
    }

    public static void validarTelefone(String tel) throws DadoInvalidoExcecao {
        if (tel == null) {
            throw new DadoInvalidoExcecao("Telefone nao pode ser nulo.");
        }

        String t = tel.replaceAll("[^0-9]", "");

        if (t.length() != 10 && t.length() != 11) {
            throw new DadoInvalidoExcecao("Telefone invalido! Use 10 ou 11 digitos.");
        }
    }

    public static void validarEmail(String email) throws DadoInvalidoExcecao {
        if (email == null) {
            throw new DadoInvalidoExcecao("Email nao pode ser nulo.");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new DadoInvalidoExcecao("Email invalido!");
        }
    }

    public static void validarCapacidade(int cap) throws DadoInvalidoExcecao {
        if (cap < 1 || cap > 50) {
            throw new DadoInvalidoExcecao("Capacidade deve estar entre 1 e 50.");
        }
    }

    public static void validarHorario(String h) throws DadoInvalidoExcecao {
        if (h == null) {
            throw new DadoInvalidoExcecao("Horario nao pode ser nulo.");
        }

        try {
            LocalTime.parse(h, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoExcecao("Horario invalido! Use HH:mm.");
        }
    }
}

