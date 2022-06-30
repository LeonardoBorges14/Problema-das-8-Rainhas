public class Tabuleiro {

    private int qtdAtaques;
    private int[][] tabuleiro;

    public Tabuleiro(int qtdAtaques, int[][] tabuleiro) {
        this.qtdAtaques = qtdAtaques;
        this.tabuleiro = tabuleiro;
    }

    public int getQtdAtaques() {
        return qtdAtaques;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

}