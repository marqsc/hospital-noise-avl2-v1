package br.edu.ifba.complexidade.noise.cliente;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/** Teste de integração que confirma o envio de dez grupos em paralelo. */
public final class EnvioParaleloTest {
    public static void main(String[] args) throws Exception {
        AtomicInteger recebidas = new AtomicInteger(); HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        servidor.createContext("/leituras", troca -> { troca.getRequestBody().readAllBytes(); recebidas.incrementAndGet(); troca.sendResponseHeaders(201, 0); troca.close(); }); servidor.start();
        LeituraRuido[][] dados = new LeituraRuido[10][]; for(int i=0;i<10;i++) dados[i]=new LeituraRuido[]{new LeituraRuido("2026-11-11T08:00:00Z",i+1,"Setor "+(i+1),1,60),new LeituraRuido("2026-11-11T08:00:01Z",i+1,"Setor "+(i+1),2,72)};
        int enviadas=new EnvioParalelo().enviarTudo(dados,new ClienteHttp("127.0.0.1",servidor.getAddress().getPort())); servidor.stop(0);
        if(enviadas!=20||recebidas.get()!=20)throw new AssertionError("Envio concorrente incompleto."); System.out.println("Teste do envio paralelo passou.");
    }
}
