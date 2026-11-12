package br.edu.ifba.complexidade.noise.servidor;

/** Uma leitura enviada por um decibelímetro remoto. */
public final class LeituraRuido {
    private final String timestamp;
    private final int setorId;
    private final String setorNome;
    private final int numeroLeitura;
    private final int decibeis;

    /** Complexidade O(1): valida e armazena uma quantidade fixa de campos. */
    public LeituraRuido(String timestamp, int setorId, String setorNome, int numeroLeitura, int decibeis) {
        if (timestamp == null || timestamp.isBlank() || setorId < 1 || setorNome == null || setorNome.isBlank() || numeroLeitura < 1 || decibeis < 0) {
            throw new IllegalArgumentException("Leitura inválida.");
        }
        this.timestamp = timestamp;
        this.setorId = setorId;
        this.setorNome = setorNome;
        this.numeroLeitura = numeroLeitura;
        this.decibeis = decibeis;
    }

    /** Complexidade O(1): separa uma única linha recebida pelo protocolo. */
    public static LeituraRuido fromCsv(String linha) {
        String[] campos = linha == null ? new String[0] : linha.trim().split(",", -1);
        if (campos.length != 5) throw new IllegalArgumentException("Formato esperado: timestamp,setorId,setorNome,numeroLeitura,decibeis");
        try {
            return new LeituraRuido(campos[0].trim(), Integer.parseInt(campos[1].trim()), campos[2].trim(), Integer.parseInt(campos[3].trim()), Integer.parseInt(campos[4].trim()));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Campos numéricos inválidos.");
        }
    }

    /** Complexidade O(1): monta uma linha de cinco campos. */
    public String toCsv() { return timestamp + "," + setorId + "," + setorNome + "," + numeroLeitura + "," + decibeis; }
    /** Complexidade O(1): retorna o identificador do setor. */
    public int getSetorId() { return setorId; }
    /** Complexidade O(1): retorna o nome do setor. */
    public String getSetorNome() { return setorNome; }
    /** Complexidade O(1): retorna o número da leitura. */
    public int getNumeroLeitura() { return numeroLeitura; }
    /** Complexidade O(1): retorna o valor em decibéis. */
    public int getDecibeis() { return decibeis; }
}
