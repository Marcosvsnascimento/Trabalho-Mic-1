package simulador.simulator;

import simulador.model.Instrucoes;
import simulador.model.Memoria;
import simulador.model.Registrador;
import simulador.model.ULA;

public class Simulador {
    private final Memoria memoria;
    private final Registrador registrador;
    private final ULA ula;

    public Simulador() {
        this.memoria = new Memoria(2048);
        this.registrador = new Registrador();
        this.ula = new ULA();
        this.registrador.set("SP", 2048);
    }

    public void carregarPrograma(int[] programa) {
        for (int i = 0; i < programa.length; i++) {
            memoria.escrever(i, programa[i]);
        }
    }

    public void executar() {
        while (true) {
            int pc = registrador.get("PC");
            int instrucao = memoria.ler(pc);
            registrador.set("IR", instrucao);
            registrador.set("PC", pc + 1);

            int opcode = (instrucao >> 8) & 0xFF;
            int operando = instrucao & 0xFF;

            switch (Instrucoes.values()[opcode]) {
                case LODD:
                    registrador.set("AC", memoria.ler(operando));
                    break;
                case STOD:
                    memoria.escrever(operando, registrador.get("AC"));
                    break;
                case LOCO:
                    registrador.set("AC", operando);
                    break;
                case ADDD:
                    registrador.set("AC", ula.somar(registrador.get("AC"), memoria.ler(operando)));
                    break;
                case SUBD:
                    registrador.set("AC", ula.subtrair(registrador.get("AC"), memoria.ler(operando)));
                    break;
                case JUMP:
                    registrador.set("PC", operando);
                    break;
                case JZER:
                    if (registrador.get("AC") == 0) registrador.set("PC", operando);
                    break;
                case JPOS:
                    if ((registrador.get("AC") & 0x8000) == 0 && registrador.get("AC") != 0) registrador.set("PC", operando);
                    break;
                case CALL:
                    int sp = registrador.get("SP") - 1;
                    memoria.escrever(sp, registrador.get("PC"));
                    registrador.set("SP", sp);
                    registrador.set("PC", operando);
                    break;
                case RETN:
                    int retorno = memoria.ler(registrador.get("SP"));
                    registrador.set("SP", registrador.get("SP") + 1);
                    registrador.set("PC", retorno);
                    break;
                case PUSH:
                    sp = registrador.get("SP") - 1;
                    memoria.escrever(sp, registrador.get("AC"));
                    registrador.set("SP", sp);
                    break;
                case POP:
                    registrador.set("AC", memoria.ler(registrador.get("SP")));
                    registrador.set("SP", registrador.get("SP") + 1);
                    break;
                case PSHI:
                    sp = registrador.get("SP") - 1;
                    memoria.escrever(sp, operando);
                    registrador.set("SP", sp);
                    break;
                case JNEG:
                    if ((registrador.get("AC") & 0x8000) != 0) registrador.set("PC", operando);
                    break;
                case POPI:
                    memoria.escrever(operando, memoria.ler(registrador.get("SP")));
                    registrador.set("SP", registrador.get("SP") + 1);
                    break;
                default:
                    System.out.println("Instrução inválida");
                    return;
            }
        }
    }
}
