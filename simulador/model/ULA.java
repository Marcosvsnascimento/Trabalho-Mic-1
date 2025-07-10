package simulador.model;

public class ULA {
    public int somar(int a, int b) {
        return (a + b) & 0xFFFF;
    }

    public int subtrair(int a, int b) {
        return (a - b) & 0xFFFF;
    }
}