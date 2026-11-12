package br.edu.ifba.complexidade.noise.servidor;

/** Armazena todas as leituras recebidas pela versão 1, sem fila limitada ou descarte. */
public final class ArmazenamentoLeituras {
    private LeituraRuido[] dados = new LeituraRuido[1024];
    private int tamanho;

    /** Complexidade amortizada O(1): adiciona uma leitura; em redimensionamentos isolados é O(N). */
    public synchronized void adicionar(LeituraRuido leitura) {
        if (tamanho == dados.length) redimensionar();
        dados[tamanho++] = leitura;
    }

    /** Complexidade O(1): retorna o contador já mantido. */
    public synchronized int tamanho() { return tamanho; }

    /** Complexidade O(N): percorre todas as leituras para identificar os dez setores. */
    public synchronized String listarSensores() {
        String[] nomes = new String[10];
        for (int indice = 0; indice < tamanho; indice++) {
            LeituraRuido leitura = dados[indice];
            if (leitura.getSetorId() <= 10 && nomes[leitura.getSetorId() - 1] == null) nomes[leitura.getSetorId() - 1] = leitura.getSetorNome();
        }
        StringBuilder resposta = new StringBuilder();
        for (int setor = 0; setor < nomes.length; setor++) resposta.append(setor + 1).append(",").append(nomes[setor] == null ? "sem leituras" : nomes[setor]).append("\n");
        return resposta.toString();
    }

    /** Complexidade O(N): imprime cada leitura preservada no servidor. Para N grande, a resposta fica extensa. */
    public synchronized String listarLeituras() {
        StringBuilder resposta = new StringBuilder();
        for (int indice = 0; indice < tamanho; indice++) resposta.append(dados[indice].toCsv()).append("\n");
        return resposta.toString();
    }

    /** Complexidade O(N²): Bubble Sort manual em todas as leituras. Muitas leituras tornam a ordenação lenta. */
    public synchronized void ordenarPorDecibeis() {
        for (int limite = tamanho - 1; limite > 0; limite--) {
            for (int indice = 0; indice < limite; indice++) {
                if (dados[indice].getDecibeis() > dados[indice + 1].getDecibeis()) {
                    LeituraRuido temporaria = dados[indice]; dados[indice] = dados[indice + 1]; dados[indice + 1] = temporaria;
                }
            }
        }
    }

    /** Complexidade O(N²): compara cada par possível e conta leituras críticas semelhantes. */
    public synchronized String analisarParesCriticos() {
        int pares = 0;
        StringBuilder exemplos = new StringBuilder();
        for (int primeiro = 0; primeiro < tamanho - 1; primeiro++) {
            for (int segundo = primeiro + 1; segundo < tamanho; segundo++) {
                if (formamParCritico(dados[primeiro], dados[segundo])) {
                    pares++;
                    if (pares <= 20) exemplos.append(dados[primeiro].toCsv()).append(" <> ").append(dados[segundo].toCsv()).append("\n");
                }
            }
        }
        return "pares=" + pares + "\n" + exemplos;
    }

    /** Complexidade O(1): compara dois valores e seus identificadores. */
    private boolean formamParCritico(LeituraRuido primeira, LeituraRuido segunda) {
        return primeira.getSetorId() == segunda.getSetorId() && primeira.getDecibeis() >= 70 && segunda.getDecibeis() >= 70 && Math.abs(primeira.getDecibeis() - segunda.getDecibeis()) <= 5;
    }

    /** Complexidade O(N): copia todas as leituras quando a capacidade física acaba. */
    private void redimensionar() {
        LeituraRuido[] novo = new LeituraRuido[dados.length * 2];
        for (int indice = 0; indice < dados.length; indice++) novo[indice] = dados[indice];
        dados = novo;
    }
}
