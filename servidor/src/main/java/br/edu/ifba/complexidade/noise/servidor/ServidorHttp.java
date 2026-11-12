package br.edu.ifba.complexidade.noise.servidor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

/** Servidor HTTP nativo que recebe e processa dados sem otimizações da versão 2. */
public final class ServidorHttp {
    private final ArmazenamentoLeituras armazenamento = new ArmazenamentoLeituras();
    private final HttpServer servidor;

    /** Complexidade O(1): registra um número fixo de rotas HTTP. */
    public ServidorHttp(String host, int porta) throws IOException {
        servidor = HttpServer.create(new InetSocketAddress(host, porta), 0);
        servidor.setExecutor(Executors.newFixedThreadPool(10));
        servidor.createContext("/status", this::status);
        servidor.createContext("/leituras", this::leituras);
        servidor.createContext("/sensores", this::sensores);
        servidor.createContext("/ordenar", this::ordenar);
        servidor.createContext("/analise/pares", this::analisar);
    }

    /** Complexidade O(1): inicia o servidor já configurado. */
    public void iniciar() { servidor.start(); }
    /** Complexidade O(1): interrompe o servidor imediatamente. */
    public void parar() { servidor.stop(0); }
    /** Complexidade O(1): retorna a porta, inclusive quando foi escolhida dinamicamente em testes. */
    public int getPorta() { return servidor.getAddress().getPort(); }

    /** Complexidade O(1): devolve o total já armazenado. */
    private void status(HttpExchange troca) throws IOException { responder(troca, "GET", "leituras=" + armazenamento.tamanho() + "\n", 200); }
    /** Complexidade O(N): lista os setores encontrados nas leituras. */
    private void sensores(HttpExchange troca) throws IOException { responder(troca, "GET", armazenamento.listarSensores(), 200); }
    /** Complexidade O(N): devolve todas as leituras mantidas. */
    private void leiturasListadas(HttpExchange troca) throws IOException { responder(troca, "GET", armazenamento.listarLeituras(), 200); }
    /** Complexidade O(N²): ordena manualmente todos os dados do servidor. */
    private void ordenar(HttpExchange troca) throws IOException { if (!"POST".equals(troca.getRequestMethod())) { responder(troca, "POST", "Método não permitido\n", 405); return; } armazenamento.ordenarPorDecibeis(); responder(troca, "", "ordenadas=" + armazenamento.tamanho() + "\n", 200); }
    /** Complexidade O(N²): executa no servidor a funcionalidade mais custosa da Avaliação I. */
    private void analisar(HttpExchange troca) throws IOException { if (!"POST".equals(troca.getRequestMethod())) { responder(troca, "POST", "Método não permitido\n", 405); return; } responder(troca, "", armazenamento.analisarParesCriticos(), 200); }
    /** Complexidade O(T): escolhe a operação por um método HTTP de tamanho constante. */
    private void leituras(HttpExchange troca) throws IOException { if ("POST".equals(troca.getRequestMethod())) { try { armazenamento.adicionar(LeituraRuido.fromCsv(new String(troca.getRequestBody().readAllBytes(), StandardCharsets.UTF_8))); responder(troca, "", "recebida\n", 201); } catch (IllegalArgumentException exception) { responder(troca, "", exception.getMessage() + "\n", 400); } } else if ("GET".equals(troca.getRequestMethod())) { leiturasListadas(troca); } else responder(troca, "POST ou GET", "Método não permitido\n", 405); }
    /** Complexidade O(L): escreve L bytes no corpo da resposta. */
    private void responder(HttpExchange troca, String metodoEsperado, String corpo, int status) throws IOException { byte[] bytes = corpo.getBytes(StandardCharsets.UTF_8); troca.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8"); troca.sendResponseHeaders(status, bytes.length); troca.getResponseBody().write(bytes); troca.close(); }
}
