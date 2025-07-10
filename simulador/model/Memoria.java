package simulador.model;

public class Memoria {
    private final int[] memoria;

    public Memoria(int tamanho) {
        memoria = new int[tamanho];
    }

    public int ler(int endereco) {
        return memoria[endereco];
    }

    public void escrever(int endereco, int valor) {
        memoria[endereco] = valor & 0xFFFF;
    }
}
