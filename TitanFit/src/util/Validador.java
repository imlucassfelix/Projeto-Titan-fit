package util;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validador {
    /**
     * Valida se o CPF tem exatamente 11 digitos numericos. String com o CPF digitado  retorna true se valido, false caso contrario
     */

    public static boolean validarCpf(String cpf) {
        if
        (cpf == null) return false;

        cpf = cpf.replaceAll("[^0-9]", "");
        // remove pontos e tracos
        return cpf.length() == 11;
    }

    /**
     * * Valida se o sexo for M, F ou I. retorna true se valido, false caso contrario
     */

    public static boolean validarSexo(String sexo) {
        if (sexo == null) return false;
        return
                sexo.equalsIgnoreCase("M") || sexo.equalsIgnoreCase("F") || sexo.equalsIgnoreCase("I");
    }

    /**
     * Valida se a data esta no formato dd/MM/yyyy. String com a data
     digitada retorna true se valido, false caso contrario
     */

    public static boolean validarData(String data) {
        try { DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd/MM/yyyy"); LocalDate.parse(data, fmt); return true;
        }
    catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valida se um campo obrigatorio nao esta vazio. String com o valor digitado retorna true se preenchido,
     false se vazio
     */

    public static boolean campoObrigatorio(String valor) {
        return
            valor != null && !valor.trim().isEmpty();
    }

    /**
     * Valida se o telefone tem um formato correto (10 ou 11 dígitos). Retorna true se válido, false caso contrário.
     */
    public static boolean validarTelefone(String telefoneAluno) {
        if (telefoneAluno == null) return false;

        // Remove tudo que não for dígito
        String telAluno = telefoneAluno.replaceAll("[^0-9]", "");

        // Telefone com DDD (10) ou Celular com nono dígito e DDD (11)
        return telAluno.length() == 10 || telAluno.length() == 11;
    }

    /**
     * Valida se o formato do e-mail é válido. Retorna true se for um e-mail válido, false caso contrário.
     */
    public static boolean validarEmail(String email) {
        if (email == null) return false;

        // Regex simples para verificar formato de e-mail
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean validarCapacidade(int capacidade) {
        final int MINIMO = 1;
        final int MAXIMO = 50;

        return capacidade >= MINIMO && capacidade <= MAXIMO;
    }

    /**
     * Valida se o horário está no formato HH:mm (ex: 08:30, 23:45). Retorna true se válido, false caso contrário.
     */
    public static boolean validarHorario(String horario) {
        if (horario == null) return false;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime.parse(horario, fmt);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}



