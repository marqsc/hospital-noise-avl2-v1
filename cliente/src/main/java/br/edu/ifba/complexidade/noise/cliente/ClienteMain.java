package br.edu.ifba.complexidade.noise.cliente;
import java.nio.file.*; import java.util.*;
/** Ponto de entrada do cliente. */
public final class ClienteMain {
    private ClienteMain(){}
    /** Complexidade depende da opção: envio O(N), ordenação e análise O(N²) no servidor. */
    public static void main(String[] args)throws Exception{
        ClienteHttp cliente=new ClienteHttp(valor(args,"--host","127.0.0.1"),Integer.parseInt(valor(args,"--port","5000")));Path csv=Path.of(valor(args,"--csv","dados/leituras-ruido.csv"));Scanner in=new Scanner(System.in);
        while(true){System.out.println("\n1 Enviar | 2 Sensores | 3 Leituras | 4 Ordenar | 5 Analisar O(N²) | 0 Sair");String op=in.nextLine().trim();if("0".equals(op))break;if("1".equals(op))System.out.println("Enviadas "+new EnvioParalelo().enviarTudo(new LeitorCsv().carregarPorSetor(csv),cliente)+" leituras por 10 threads.");else if("2".equals(op))System.out.print(cliente.obter("/sensores"));else if("3".equals(op))System.out.print(cliente.obter("/leituras"));else if("4".equals(op))System.out.print(cliente.postar("/ordenar"));else if("5".equals(op))System.out.print(cliente.postar("/analise/pares"));else System.out.println("Opção inválida.");}in.close();
    }
    /** Complexidade O(A): percorre os argumentos configurados. */ private static String valor(String[] a,String nome,String padrao){for(int i=0;i<a.length-1;i++)if(nome.equals(a[i]))return a[i+1];return padrao;}
}
