package br.edu.ifba.complexidade.noise.ui;

import br.edu.ifba.complexidade.noise.model.ParCritico;
import br.edu.ifba.complexidade.noise.model.SetorHospitalar;
import br.edu.ifba.complexidade.noise.service.MonitoramentoRuidoService;
import java.util.Scanner;

/**
 * Interface de console responsável por receber entradas e mostrar resultados ao usuário.
 */
public final class MenuConsole {
    private final MonitoramentoRuidoService monitoramentoService;
    private final Scanner scanner;
    private SetorHospitalar[] setores;

    /** Complexidade: O(1), pois prepara as dependências do menu. */
    public MenuConsole(MonitoramentoRuidoService monitoramentoService) {
        if (monitoramentoService == null) {
            throw new IllegalArgumentException("O serviço de monitoramento é obrigatório.");
        }
        this.monitoramentoService = monitoramentoService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Complexidade: depende da opção escolhida. O laço mantém o programa em execução até o usuário sair.
     * Em entradas grandes, as opções de impressão e análise podem levar mais tempo conforme suas próprias complexidades.
     */
    public void executar() {
        gerarNovoMonitoramento();
        int opcao;
        do {
            mostrarOpcoes();
            opcao = lerInteiro("Escolha uma opção: ");
            executarOpcao(opcao);
        } while (opcao != 0);
        scanner.close();
    }

    /** Complexidade: O(1), pois imprime um número fixo de opções. */
    private void mostrarOpcoes() {
        System.out.println("\n=== Monitoramento de Ruído Hospitalar ===");
        System.out.println("1 - Listar setores monitorados");
        System.out.println("2 - Imprimir leituras de todos os setores");
        System.out.println("3 - Ordenar leituras em ordem crescente");
        System.out.println("4 - Encontrar pares de leituras críticas semelhantes (O(N²))");
        System.out.println("5 - Gerar novo monitoramento");
        System.out.println("0 - Sair");
    }

    /** Complexidade: depende da opção; apenas direciona a execução para uma funcionalidade. */
    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> monitoramentoService.imprimirSetores(setores);
            case 2 -> monitoramentoService.imprimirLeituras(setores);
            case 3 -> {
                monitoramentoService.ordenarLeiturasCrescentemente(setores);
                System.out.println("Leituras ordenadas em ordem crescente para todos os setores.");
            }
            case 4 -> {
                ParCritico[] pares = monitoramentoService.encontrarParesCriticosSemelhantes(setores);
                monitoramentoService.imprimirParesCriticos(pares);
            }
            case 5 -> gerarNovoMonitoramento();
            case 0 -> System.out.println("Programa encerrado.");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    /**
     * Complexidade: O(N), pois chama a geração de N leituras para cada um dos 10 setores.
     * Uma quantidade grande aumenta proporcionalmente o tempo e a memória necessários.
     */
    private void gerarNovoMonitoramento() {
        int quantidadeLeituras = lerQuantidadeLeituras();
        setores = monitoramentoService.gerarMonitoramento(quantidadeLeituras);
        System.out.println("Monitoramento gerado com " + quantidadeLeituras + " leituras por setor.");
    }

    /** Complexidade: O(T), sendo T o número de tentativas inválidas antes de uma entrada válida. */
    private int lerQuantidadeLeituras() {
        int quantidade;
        do {
            quantidade = lerInteiro("Quantidade de leituras por setor: ");
            if (quantidade <= 0) {
                System.out.println("Informe uma quantidade maior que zero.");
            }
        } while (quantidade <= 0);
        return quantidade;
    }

    /** Complexidade: O(T), pois lê até receber um número inteiro válido. */
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException exception) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }
}
