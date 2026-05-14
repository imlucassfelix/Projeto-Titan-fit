package application;

/**
 * Classe principal do sistema Titan Fit.
 *
 * <p>Ponto de entrada da aplicacao ({@code main}). Exibe a mensagem
 * de boas-vindas, instancia o {@link java.util.Scanner} compartilhado
 * e inicia o {@link interfaceMenu.Menu} principal.</p>
 *
 * @author Lucas Rodrigues, Mateus Santos, Lucas Felix, Ryan Vinicius
 * @version 1.0
 * @see interfaceMenu.Menu
 * @see BoasVindas
 */

import java.util.Scanner;
import interfaceMenu.Menu;

public class Programa {
	public static void main(String[]args) throws InterruptedException{
		
		new BoasVindas().iniciar();
		// construtor recebe o Scanner
		Scanner sc = new Scanner(System.in);
		//chama o menu principal
		Menu menu = new Menu(sc);
		
		//metodo para exibir menu
		menu.exibirMenu();
		
		sc.close(); 
	}

}
