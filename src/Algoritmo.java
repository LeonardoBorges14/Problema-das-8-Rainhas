import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algoritmo {
	
    private double pc, pm;
    private int geracao, posicaoTabuleiro, tamanhoPopulacao, maxGeracao;
    private int[][] populacao, novaPopulacao;
    private Tabuleiro tabuleiroPai1, tabuleiroPai2;
    private List<Tabuleiro> listaTabuleiro;
    
    public Algoritmo(int tamanhoPopulacao, int maxGeracao, double pc, double pm) {
		this.tamanhoPopulacao = tamanhoPopulacao;
		this.maxGeracao = maxGeracao;
		this.pc = pc;
		this.pm = pm;
        this.posicaoTabuleiro = 0;
        this.populacao = new int[tamanhoPopulacao][64];
        this.novaPopulacao = new int[tamanhoPopulacao][64];

	}


	public int resolverProblema() {
        populacaoInicial();

        do {
        	
            if (posicaoTabuleiro == tamanhoPopulacao) {
                populacao = novaPopulacao;
            }

            posicaoTabuleiro = 0;

            avaliacao();
            ordenacao();

            do {
                selecao();
                cruzamento();
                mutacao();
            } while(tamanhoPopulacao > posicaoTabuleiro);

            geracao++;
            
        } while (geracao < maxGeracao);

        printarPopulacao();
        
        return listaTabuleiro.get(0).getQtdAtaques();
    }
	
    public void populacaoInicial() {
    	
        Random random = new Random();

        for (int i = 0; i < tamanhoPopulacao; i++) {

			for (int j = 0; j < 8; j++) {
                int coluna = random.nextInt(64);
                boolean colunaValida = posicaoValida(coluna, populacao[i]);

                if (!colunaValida) continue;

                populacao[i][coluna] = 1;
                j++;
            }
        }
    }
    
    public void avaliacao() {
        List<Tabuleiro> tabuleiros = new ArrayList<>();

        for (int i = 0; i < tamanhoPopulacao; i++) {
            int posicao = 0;
            int[][] tabuleiro = new int[8][8];

            for (int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {
                    tabuleiro[linha][coluna] = populacao[i][posicao];
                    posicao++;
                }
            }

            int qtdAtaques = 0;
            for (int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {
                    int rainha = tabuleiro[linha][coluna];

                    if (rainha != 1)  {
                    	continue;
                    }

                    for (int j = 0; j < 8; j++) {
                        if (linha == j) {
                        	continue;
                        }
                        if (tabuleiro[j][coluna] == 1) {
                        	qtdAtaques += 1;
                        }
                    }
                    
                    for (int j = 0; j < 8; j++) {
                        if (coluna == j) {
                        	continue;
                        }
                        if (tabuleiro[linha][j] == 1) {
                        	qtdAtaques += 1;
                        }
                    }

                    boolean continuarLoop = true;
                    int x, y;
                                        
                    if(linha > 0) {
                    	x = linha - 1;
                    }
                    else {
                    	x = -1;
                    }
                    
                    if(coluna > 0) {
                    	y = coluna - 1;
                    }
                    else {
                    	y = -1;
                    }
                    
                    while (continuarLoop) {
                        
                        if (x < 0 || y < 0) {
                            continuarLoop = false;
                            break;
                        }
                        
                        if (tabuleiro[x][y] == 1) {
                        	qtdAtaques += 1;
                        }

                        if (x >= 0) {
                        	x--;
                        }
                        
                        if (y >= 0) {
                        	y--;
                        }

                    }
                    
                    continuarLoop = true;
                    
                    if(linha < 7) {
                    	x = linha + 1;
                    }
                    else {
                    	x = 8;
                    }
                    
                    if(coluna < 7) {
                    	y = coluna + 1;
                    }
                    else {
                    	y = 8;
                    }
                    
                    while (continuarLoop) {

                        if (x > 7 || y > 7) {
                            continuarLoop = false;
                            break;
                        }

                        if (tabuleiro[x][y] == 1) {
                        	qtdAtaques += 1;
                        }

                        if (x <= 7) {
                        	x++;
                        }
                        
                        if (y <= 7) {
                        	y++;
                        }
                    }
                    
                    continuarLoop = true;
                    
                    if(linha > 0) {
                    	x = linha - 1;
                    }
                    else {
                    	x = -1;
                    }
                    
                    if(coluna < 7) {
                    	y = coluna + 1;
                    }
                    else {
                    	y = 8;
                    }
                    
                    while (continuarLoop) {

                        if (x < 0 || y > 7) {
                            continuarLoop = false;
                            break;
                        }
                        
                        if (tabuleiro[x][y] == 1) {
                        	qtdAtaques += 1;
                        }

                        if (y <= 7) {
                        	y++;
                        }
                        
                        if (x >= 0) {
                        	x--;
                        }

                    }
                    
                    continuarLoop = true;
                    
                    if(linha < 7) {
                    	x = linha + 1;
                    }
                    else {
                    	x = 8;
                    }
                    
                    if(coluna > 0) {
                    	y = coluna - 1;
                    }
                    else {
                    	y = -1;
                    }
                    
                    while (continuarLoop) {

                        if (x > 7 || y < 0) {
                            continuarLoop = false;
                            break;
                        }

                        if (tabuleiro[x][y] == 1) {
                        	qtdAtaques += 1;
                        }

                        if (x <= 7) {
                        	x++;
                        }
                        
                        if (y >= 0) {
                        	y--;
                        }
                    }
                 
                }
            }

            Tabuleiro tab = new Tabuleiro(qtdAtaques, tabuleiro);
            tabuleiros.add(tab);
        }
        listaTabuleiro = tabuleiros;
    }
    
    public void ordenacao() {
        Collections.sort(listaTabuleiro, Comparator.comparing(Tabuleiro::getQtdAtaques));
    }

    public boolean posicaoValida(int posicaoAtual, int[] tabuleiro) {
        boolean valido = true;

        int posicaoInicio = 0;
        int posicaoFinal = 0;

        if (posicaoAtual >= 0 && posicaoAtual <= 7) {
            posicaoInicio = 0;
            posicaoFinal = 8;
            
        } else if (posicaoAtual >= 8 && posicaoAtual <= 15) {
            posicaoInicio = 8;
            posicaoFinal = 16;
            
        } else if (posicaoAtual >= 16 && posicaoAtual <= 23) {
            posicaoInicio = 16;
            posicaoFinal = 24;
            
        } else if (posicaoAtual >= 24 && posicaoAtual <= 31) {
            posicaoInicio = 24;
            posicaoFinal = 32;
            
        } else if (posicaoAtual >= 32 && posicaoAtual <= 39) {
            posicaoInicio = 32;
            posicaoFinal = 40;
            
        } else if (posicaoAtual >= 40 && posicaoAtual <= 47) {
            posicaoInicio = 40;
            posicaoFinal = 48;
            
        } else if (posicaoAtual >= 48 && posicaoAtual <= 55) {
            posicaoInicio = 48;
            posicaoFinal = 56;
            
        } else if (posicaoAtual >= 56 && posicaoAtual <= 63) {
            posicaoInicio = 56;
            posicaoFinal = 64;
        }

        for (int i = posicaoInicio; i < posicaoFinal; i++) {
            if (tabuleiro[i] == 1) {
                valido = false;
            }

        }

        return valido;
    }


    public void selecao() {
        Random random = new Random();

        int posicaoPai1 = random.nextInt(tamanhoPopulacao);
        int posicaoPai2 = random.nextInt(tamanhoPopulacao);

        while (posicaoPai2 == posicaoPai1) {
            posicaoPai2 = random.nextInt(tamanhoPopulacao);
        }

        Tabuleiro pai1 = listaTabuleiro.get(posicaoPai1);
        Tabuleiro pai2 = listaTabuleiro.get(posicaoPai2);

        tabuleiroPai1 = pai1;
        tabuleiroPai2 = pai2;
    }

    public void cruzamento() {
        Random r = new Random();

        double pcRandom = r.nextDouble();

        int[] pai1 = new int[64];
        int[] pai2 = new int[64];

        int posicao = 0;
        
        for (int i = 0; i < 8; i++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                pai1[posicao] = tabuleiroPai1.getTabuleiro()[i][coluna];
                pai2[posicao] = tabuleiroPai2.getTabuleiro()[i][coluna];
                posicao++;
            }
        }

        if (pcRandom < pc) {
            for (int i = 0; i < 2; i++) {
                int[] arrayFilho = new int[64];

                int x = ((r.nextInt(8) + 1) * 8) - 1;
                int y = x + 1;

                for (int j = 0; j < 64; j++) {
                	
                    if (j <= x) {
                        arrayFilho[j] = pai1[j];
                    }

                    if (j >= y) {
                        arrayFilho[j] = pai2[j];
                    }
                }

                novaPopulacao[posicaoTabuleiro] = arrayFilho;
                posicaoTabuleiro++;
            }
        } else {
            novaPopulacao[posicaoTabuleiro] = pai2;
            posicaoTabuleiro++;
            
            novaPopulacao[posicaoTabuleiro] = pai1;
            posicaoTabuleiro++;
        }
    }

    public void mutacao() {
        Random r = new Random();

        double pmRandom = r.nextDouble();

        if (pmRandom < pm) {
        	
            int posicaoUltimofilho = posicaoTabuleiro - 1;
            int posicaoPenultimofilho = posicaoTabuleiro - 2;

            int[] ultimoFilho = new int[64];
            
            int rainhas = 1;

            while (rainhas <= 8) {
                int posicao = r.nextInt(64);
                boolean posicaoValida = posicaoValida(posicao, ultimoFilho);

                if (!posicaoValida) {
                	continue;  
                }

                ultimoFilho[posicao] = 1;
                rainhas++;
            }
            
            novaPopulacao[posicaoUltimofilho] = ultimoFilho;
            
            int[] penultimoFilho = new int[64];
            
            rainhas = 1;

            while (rainhas <= 8) {
                int posicao = r.nextInt(64);
                boolean posicaoValida = posicaoValida(posicao, penultimoFilho);

                if (!posicaoValida) {
                	continue;  
                }

                penultimoFilho[posicao] = 1;
                rainhas++;
            }
            
            novaPopulacao[posicaoPenultimofilho] = penultimoFilho;
        }
    }

    public void printarPopulacao() {
        System.out.println("------------ RESULTADO ------------");
        System.out.println("");
        System.out.println("Gerações: "+ geracao);
        System.out.println("");
        
        
        if(listaTabuleiro.get(0).getQtdAtaques() > 0) {
            System.out.println("Não foi encontrada nenhuma solução!");
            System.out.println();
            System.out.print("Quantidade de ataques: " + listaTabuleiro.get(0).getQtdAtaques());
            System.out.println();
            System.out.println();
            System.out.println("Resultado Final:");
            System.out.println();
        }
        else {
            System.out.println("Solução encontrada!");
            System.out.println();
            System.out.println("Solução:");
            System.out.println();
        }


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(listaTabuleiro.get(0).getTabuleiro()[i][j] + " ");
            }
            System.out.println();
        }
    }

}