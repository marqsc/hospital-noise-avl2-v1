package br.edu.ifba.complexidade.noise.servidor;

/** Ponto de entrada do programa servidor. */
public final class ServidorMain {
    private ServidorMain() { }
    /** Complexidade O(1): interpreta dois argumentos e inicia as rotas HTTP. */
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1"; int porta = 5000;
        for (int indice = 0; indice < args.length - 1; indice += 2) { if ("--host".equals(args[indice])) host = args[indice + 1]; if ("--port".equals(args[indice])) porta = Integer.parseInt(args[indice + 1]); }
        ServidorHttp servidor = new ServidorHttp(host, porta); servidor.iniciar();
        System.out.println("Servidor ativo em http://" + host + ":" + servidor.getPorta() + " - Ctrl+C para encerrar.");
    }
}
