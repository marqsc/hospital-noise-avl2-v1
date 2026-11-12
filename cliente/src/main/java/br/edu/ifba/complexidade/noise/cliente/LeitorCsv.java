package br.edu.ifba.complexidade.noise.cliente;
import java.io.*; import java.nio.file.*;

/** Lê o CSV e agrupa as leituras por sensor. */
public final class LeitorCsv {
    /** Complexidade O(N): processa cada linha do CSV uma única vez. */
    public LeituraRuido[][] carregarPorSetor(Path arquivo) throws IOException {
        LeituraRuido[][] grupos = new LeituraRuido[10][]; int[] tamanhos = new int[10];
        for (int i=0;i<10;i++) grupos[i]=new LeituraRuido[1024];
        try (BufferedReader br=Files.newBufferedReader(arquivo)) {
            if (!"timestamp,setorId,setorNome,numeroLeitura,decibeis".equals(br.readLine())) throw new IllegalArgumentException("Cabeçalho CSV inválido.");
            String linha; while ((linha=br.readLine())!=null) { LeituraRuido leitura=LeituraRuido.fromCsv(linha); int i=leitura.getSetorId()-1; if(tamanhos[i]==grupos[i].length) grupos[i]=ampliar(grupos[i]); grupos[i][tamanhos[i]++]=leitura; }
        }
        for(int i=0;i<10;i++){ if(tamanhos[i]==0) throw new IllegalArgumentException("CSV sem setor "+(i+1)); LeituraRuido[] exato=new LeituraRuido[tamanhos[i]]; for(int j=0;j<tamanhos[i];j++) exato[j]=grupos[i][j]; grupos[i]=exato; }
        return grupos;
    }
    /** Complexidade O(N): copia o vetor quando a capacidade se esgota. */
    private LeituraRuido[] ampliar(LeituraRuido[] original){ LeituraRuido[] novo=new LeituraRuido[original.length*2]; for(int i=0;i<original.length;i++) novo[i]=original[i]; return novo; }
}
