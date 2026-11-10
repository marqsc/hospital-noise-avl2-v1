package br.edu.ifba.complexidade.noise.model;

/**
 * Agrupa o identificador, o nome e as leituras de um setor monitorado.
 */
public final class SetorHospitalar {
    private final int id;
    private final String nome;
    private final LeituraRuido[] leituras;

    /**
     * Complexidade: O(1), porque recebe referências já preparadas.
     */
    public SetorHospitalar(int id, String nome, LeituraRuido[] leituras) {
        if (id <= 0 || nome == null || nome.isBlank() || leituras == null) {
            throw new IllegalArgumentException("Os dados do setor são inválidos.");
        }

        this.id = id;
        this.nome = nome;
        this.leituras = leituras;
    }

    /** Complexidade: O(1), pois retorna um valor simples. */
    public int getId() {
        return id;
    }

    /** Complexidade: O(1), pois retorna uma referência imutável. */
    public String getNome() {
        return nome;
    }

    /**
     * Complexidade: O(1), pois retorna a referência do vetor de leituras.
     * A ordenação é realizada nesse mesmo vetor pelo algoritmo próprio do projeto.
     */
    public LeituraRuido[] getLeituras() {
        return leituras;
    }
}
