import java.util.Locale;
import java.util.Scanner;

public class Main {
	
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        int geracoes, populacao;
        double pc, pm;
        String repetir = "N";

        System.out.print("Tamanho da popula��o: ");
        populacao = sc.nextInt();
        System.out.println();

        System.out.print("Quantidade de gera��es: ");
        geracoes = sc.nextInt();;
        System.out.println();
        
        System.out.print("Probabilidade de muta��o: ");
        pm = sc.nextDouble();
        System.out.println();

        System.out.print("Probabilidade de cruzamento: ");
        pc = sc.nextDouble();
        System.out.println();
        
        System.out.print("Repetir at� encontrar solu��o (S / N): ");
        repetir = sc.next();
        System.out.println();


        int ataques = 0;
        Algoritmo algoritmo = null;
        int tentativas = 0;
        
        if(repetir.equalsIgnoreCase("S")) {
        	do {
                algoritmo = new Algoritmo(populacao, geracoes, pc, pm);
            	ataques = algoritmo.resolverProblema();
                System.out.println();
                tentativas++;
            } while(ataques > 0);
            System.out.println();
            System.out.println("Tentativas: " + tentativas);
        }
        else {
            algoritmo = new Algoritmo(populacao, geracoes, pc, pm);
        	ataques = algoritmo.resolverProblema();
        }

        sc.close();
    }
    
}
