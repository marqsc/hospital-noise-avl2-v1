package br.edu.ifba.complexidade.noise.model;

/**
 * Representa duas leituras críticas semelhantes encontradas no mesmo setor.
 */
public final class ParCritico {
    private final int setorId;
    private final LeituraRuido primeiraLeitura;
    private final LeituraRuido segundaLeitura;

    /**
     * Complexidade: O(1), porque guarda três referências.
     */
    public ParCritico(int setorId, LeituraRuido primeiraLeitura, LeituraRuido segundaLeitura) {
        this.setorId = setorId;
        this.primeiraLeitura = primeiraLeitura;
        this.segundaLeitura = segundaLeitura;
    }

    /** Complexidade: O(1), pois retorna um valor simples. */
    public int getSetorId() {
        return setorId;
    }

    /** Complexidade: O(1), pois retorna uma referência. */
    public LeituraRuido getPrimeiraLeitura() {
        return primeiraLeitura;
    }

    /** Complexidade: O(1), pois retorna uma referência. */
    public LeituraRuido getSegundaLeitura() {
        return segundaLeitura;
    }
}
