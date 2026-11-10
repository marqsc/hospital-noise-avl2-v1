package br.edu.ifba.complexidade.noise.service;

import br.edu.ifba.complexidade.noise.model.LeituraRuido;
import br.edu.ifba.complexidade.noise.model.ParCritico;
import br.edu.ifba.complexidade.noise.model.SetorHospitalar;
import java.util.Random;

/**
 * Contém as regras de geração, ordenação e análise das leituras de ruído.
 */
public final class MonitoramentoRuidoService {
    public static final int QUANTIDADE_SETORES = 10;
    public static final int LIMITE_CRITICO_DECIBEIS = 70;
    public static final int DIFERENCA_MAXIMA_SEMELHANTE = 5;
    private static final int DECIBEIS_MINIMOS = 30;
    private static final int DECIBEIS_MAXIMOS = 100;
    private static final String[] NOMES_SETORES = {
        "UTI Adulto", "UTI Neonatal", "Enfermaria A", "Enfermaria B", "Recepção",
        "Corredor Central", "Centro Cirúrgico", "Laboratório", "Radiologia", "Farmácia"
    };

    private final Random random;

    /** Complexidade: O(1), porque instancia somente o gerador de números aleatórios. */
    public MonitoramentoRuidoService() {
        this(new Random());
    }

    /** Complexidade: O(1), pois recebe uma dependência já criada. */
    public MonitoramentoRuidoService(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("O gerador aleatório é obrigatório.");
        }
        this.random = random;
    }

    /**
     * Gera N leituras aleatórias para cada um dos 10 setores.
     * Complexidade: O(D x N); como D é fixo em 10 setores, o crescimento é O(N).
     * Para valores muito grandes de N, o tempo e a memória usados aumentam linearmente.
     */
    public SetorHospitalar[] gerarMonitoramento(int quantidadeLeiturasPorSetor) {
        validarQuantidadeLeituras(quantidadeLeiturasPorSetor);
        SetorHospitalar[] setores = new SetorHospitalar[QUANTIDADE_SETORES];

        for (int indiceSetor = 0; indiceSetor < QUANTIDADE_SETORES; indiceSetor++) {
            LeituraRuido[] leituras = new LeituraRuido[quantidadeLeiturasPorSetor];
            for (int indiceLeitura = 0; indiceLeitura < quantidadeLeiturasPorSetor; indiceLeitura++) {
                int decibeis = DECIBEIS_MINIMOS + random.nextInt(DECIBEIS_MAXIMOS - DECIBEIS_MINIMOS + 1);
                leituras[indiceLeitura] = new LeituraRuido(indiceSetor + 1, indiceLeitura + 1, decibeis);
            }
            setores[indiceSetor] = new SetorHospitalar(indiceSetor + 1, NOMES_SETORES[indiceSetor], leituras);
        }
        return setores;
    }

    /**
     * Ordena as leituras de cada setor com Bubble Sort, sem usar bibliotecas de ordenação.
     * Complexidade: O(D x N²), que equivale a O(N²) com 10 setores fixos.
     * Para muitos dados, esse algoritmo pode ficar lento; por isso, a demonstração deve começar com N pequeno.
     */
    public void ordenarLeiturasCrescentemente(SetorHospitalar[] setores) {
        validarSetores(setores);
        for (SetorHospitalar setor : setores) {
            LeituraRuido[] leituras = setor.getLeituras();
            for (int limite = leituras.length - 1; limite > 0; limite--) {
                for (int indice = 0; indice < limite; indice++) {
                    if (leituras[indice].getDecibeis() > leituras[indice + 1].getDecibeis()) {
                        LeituraRuido temporaria = leituras[indice];
                        leituras[indice] = leituras[indice + 1];
                        leituras[indice + 1] = temporaria;
                    }
                }
            }
        }
    }

    /**
     * Encontra pares de leituras de um mesmo setor que são críticas e semelhantes.
     * Complexidade: O(D x N²), ou O(N²) com quantidade fixa de setores. Dois laços comparam cada par possível.
     * Em entradas grandes, o número de comparações e pares encontrados pode crescer rapidamente.
     */
    public ParCritico[] encontrarParesCriticosSemelhantes(SetorHospitalar[] setores) {
        validarSetores(setores);
        int quantidadePares = contarParesCriticos(setores);
        ParCritico[] pares = new ParCritico[quantidadePares];
        int proximaPosicao = 0;

        for (SetorHospitalar setor : setores) {
            LeituraRuido[] leituras = setor.getLeituras();
            for (int primeira = 0; primeira < leituras.length - 1; primeira++) {
                for (int segunda = primeira + 1; segunda < leituras.length; segunda++) {
                    if (formamParCriticoSemelhante(leituras[primeira], leituras[segunda])) {
                        pares[proximaPosicao] = new ParCritico(setor.getId(), leituras[primeira], leituras[segunda]);
                        proximaPosicao++;
                    }
                }
            }
        }
        return pares;
    }

    /**
     * Complexidade: O(D x N), pois percorre cada setor e cada leitura uma vez.
     * Com mais leituras, a impressão aumenta linearmente e pode deixar o terminal lento ou poluído.
     */
    public void imprimirSetores(SetorHospitalar[] setores) {
        validarSetores(setores);
        for (SetorHospitalar setor : setores) {
            System.out.println("[" + setor.getId() + "] " + setor.getNome());
        }
    }

    /**
     * Complexidade: O(D x N), pois imprime todas as leituras de todos os setores.
     * Para N alto, gera muitas linhas no terminal; use poucos dados na demonstração em vídeo.
     */
    public void imprimirLeituras(SetorHospitalar[] setores) {
        validarSetores(setores);
        for (SetorHospitalar setor : setores) {
            System.out.println("\nSetor [" + setor.getId() + "] " + setor.getNome());
            for (LeituraRuido leitura : setor.getLeituras()) {
                System.out.println("  Leitura " + leitura.getNumeroLeitura() + ": " + leitura.getDecibeis() + " dB");
            }
        }
    }

    /**
     * Complexidade: O(P), sendo P a quantidade de pares encontrada.
     * Quando N é grande, P pode se aproximar de N² e a impressão também se torna custosa.
     */
    public void imprimirParesCriticos(ParCritico[] pares) {
        if (pares == null) {
            throw new IllegalArgumentException("A lista de pares não pode ser nula.");
        }
        if (pares.length == 0) {
            System.out.println("Nenhum par crítico semelhante foi encontrado.");
            return;
        }
        for (ParCritico par : pares) {
            System.out.println(
                "Setor " + par.getSetorId() + ": leituras "
                    + par.getPrimeiraLeitura().getNumeroLeitura() + " (" + par.getPrimeiraLeitura().getDecibeis() + " dB) e "
                    + par.getSegundaLeitura().getNumeroLeitura() + " (" + par.getSegundaLeitura().getDecibeis() + " dB)"
            );
        }
    }

    /**
     * Complexidade: O(D x N²), pois compara todos os pares possíveis de leituras em cada setor.
     * É usado antes da criação do vetor final para reservar exatamente a quantidade necessária.
     */
    private int contarParesCriticos(SetorHospitalar[] setores) {
        int quantidade = 0;
        for (SetorHospitalar setor : setores) {
            LeituraRuido[] leituras = setor.getLeituras();
            for (int primeira = 0; primeira < leituras.length - 1; primeira++) {
                for (int segunda = primeira + 1; segunda < leituras.length; segunda++) {
                    if (formamParCriticoSemelhante(leituras[primeira], leituras[segunda])) {
                        quantidade++;
                    }
                }
            }
        }
        return quantidade;
    }

    /**
     * Complexidade: O(1), pois realiza comparações aritméticas constantes.
     */
    private boolean formamParCriticoSemelhante(LeituraRuido primeira, LeituraRuido segunda) {
        return primeira.getDecibeis() >= LIMITE_CRITICO_DECIBEIS
            && segunda.getDecibeis() >= LIMITE_CRITICO_DECIBEIS
            && Math.abs(primeira.getDecibeis() - segunda.getDecibeis()) <= DIFERENCA_MAXIMA_SEMELHANTE;
    }

    /** Complexidade: O(1), pois valida somente o valor recebido. */
    private void validarQuantidadeLeituras(int quantidadeLeiturasPorSetor) {
        if (quantidadeLeiturasPorSetor <= 0) {
            throw new IllegalArgumentException("A quantidade de leituras deve ser maior que zero.");
        }
    }

    /** Complexidade: O(1), pois verifica apenas se o vetor informado é válido. */
    private void validarSetores(SetorHospitalar[] setores) {
        if (setores == null || setores.length != QUANTIDADE_SETORES) {
            throw new IllegalArgumentException("Devem ser informados exatamente 10 setores monitorados.");
        }
    }
}
