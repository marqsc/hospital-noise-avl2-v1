package br.edu.ifba.complexidade.noise;

import br.edu.ifba.complexidade.noise.service.MonitoramentoRuidoService;
import br.edu.ifba.complexidade.noise.ui.MenuConsole;

/**
 * Ponto de entrada da aplicação.
 */
public final class Main {
    private Main() {
    }

    /**
     * Complexidade: depende das operações escolhidas no menu; a inicialização é O(1).
     * O custo total inclui geração O(N), impressão O(N), ordenação O(N²) ou análise O(N²).
     * Para entradas grandes, as opções quadráticas podem ficar lentas.
     */
    public static void main(String[] args) {
        MenuConsole menu = new MenuConsole(new MonitoramentoRuidoService());
        menu.executar();
    }
}
