package br.edu.ifba.complexidade.noise.model;

/**
 * Representa uma leitura numérica produzida por um decibelímetro.
 */
public final class LeituraRuido {
    private final int setorId;
    private final int numeroLeitura;
    private final int decibeis;

    /**
     * Complexidade: O(1), porque apenas atribui três valores.
     */
    public LeituraRuido(int setorId, int numeroLeitura, int decibeis) {
        if (setorId <= 0 || numeroLeitura <= 0 || decibeis < 0) {
            throw new IllegalArgumentException("Os dados da leitura são inválidos.");
        }

        this.setorId = setorId;
        this.numeroLeitura = numeroLeitura;
        this.decibeis = decibeis;
    }

    /** Complexidade: O(1), pois retorna um valor simples. */
    public int getSetorId() {
        return setorId;
    }

    /** Complexidade: O(1), pois retorna um valor simples. */
    public int getNumeroLeitura() {
        return numeroLeitura;
    }

    /** Complexidade: O(1), pois retorna um valor simples. */
    public int getDecibeis() {
        return decibeis;
    }
}
