package br.edu.ifba.complexidade.noise.cliente;
import java.io.*;
/** Cria uma Thread por sensor e não aplica controle por limiar. */
public final class EnvioParalelo {
    /** Complexidade O(N): inicia 10 threads e aguarda o envio de todas as leituras. */
    public int enviarTudo(LeituraRuido[][] grupos,ClienteHttp cliente)throws InterruptedException{
        if(grupos==null||grupos.length!=10)throw new IllegalArgumentException("São necessários dez sensores."); Thread[] ts=new Thread[10];String[] erros=new String[10];int[] totais=new int[10];
        for(int i=0;i<10;i++){final int setor=i;ts[i]=new Thread(()->enviarSetor(grupos[setor],cliente,totais,erros,setor),"sensor-"+(i+1));ts[i].start();}
        int total=0;for(int i=0;i<10;i++){ts[i].join();if(erros[i]!=null)throw new IllegalStateException(erros[i]);total+=totais[i];}return total;
    }
    /** Complexidade O(N): envia a sequência integral de um sensor. */
    private void enviarSetor(LeituraRuido[] leituras,ClienteHttp cliente,int[] totais,String[] erros,int setor){try{for(LeituraRuido l:leituras){cliente.enviar(l);totais[setor]++;}}catch(IOException|InterruptedException e){Thread.currentThread().interrupt();erros[setor]="Falha no sensor "+(setor+1)+": "+e.getMessage();}}
}
