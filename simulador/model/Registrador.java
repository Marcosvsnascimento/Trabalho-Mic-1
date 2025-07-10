package simulador.model;

import java.util.HashMap;
import java.util.Map;

public class Registrador {
    private final Map<String, Integer> registradores;

    public Registrador() {
        registradores = new HashMap<>();
        for (String nome : new String[]{"PC", "AC", "SP", "IR", "TIR", "A", "B", "C", "D", "E", "F"}) {
            registradores.put(nome, 0);
        }
    }

    public int get(String nome) {
        return registradores.getOrDefault(nome, 0);
    }

    public void set(String nome, int valor) {
        registradores.put(nome, valor & 0xFFFF);
    }

    public Map<String, Integer> getTodos() {
        return new HashMap<>(registradores);
    }
}
