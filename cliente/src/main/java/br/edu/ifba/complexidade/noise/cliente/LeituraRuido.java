package br.edu.ifba.complexidade.noise.cliente;

/** Representa uma linha do CSV a ser enviada ao servidor. */
public final class LeituraRuido {
    private final String linha; private final int setorId;
    /** Complexidade O(1): valida cinco campos. */
    public LeituraRuido(String timestamp, int setorId, String setorNome, int numero, int decibeis) {
        if (timestamp == null || timestamp.isBlank() || setorId < 1 || setorId > 10 || setorNome == null || setorNome.isBlank() || numero < 1 || decibeis < 0) throw new IllegalArgumentException("Leitura inválida.");
        this.setorId = setorId; this.linha = timestamp + "," + setorId + "," + setorNome + "," + numero + "," + decibeis;
    }
    /** Complexidade O(1): interpreta uma linha CSV. */
    public static LeituraRuido fromCsv(String linha) {
        String[] c = linha == null ? new String[0] : linha.trim().split(",", -1);
        if (c.length != 5) throw new IllegalArgumentException("Linha CSV inválida.");
        try { return new LeituraRuido(c[0].trim(), Integer.parseInt(c[1].trim()), c[2].trim(), Integer.parseInt(c[3].trim()), Integer.parseInt(c[4].trim())); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Valores numéricos inválidos no CSV."); }
    }
    /** Complexidade O(1): retorna a linha para o protocolo. */
    public String toCsv() { return linha; }
    /** Complexidade O(1): retorna o setor da leitura. */
    public int getSetorId() { return setorId; }
}
