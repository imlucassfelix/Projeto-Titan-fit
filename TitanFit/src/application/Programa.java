package application;
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
