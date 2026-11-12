package br.edu.ifba.complexidade.noise.servidor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** Teste de integração dos endpoints e da análise O(N²) do servidor. */
public final class ServidorHttpTest {
    public static void main(String[] args) throws Exception {
        ServidorHttp servidor = new ServidorHttp("127.0.0.1", 0); servidor.iniciar();
        HttpClient http = HttpClient.newHttpClient(); String base = "http://127.0.0.1:" + servidor.getPorta();
        enviar(http, base, "2026-11-11T08:00:00Z,1,UTI,1,74"); enviar(http, base, "2026-11-11T08:00:01Z,1,UTI,2,70"); enviar(http, base, "2026-11-11T08:00:02Z,2,Recepcao,1,40");
        String status = obter(http, base + "/status", "GET"); if (!status.contains("leituras=3")) throw new AssertionError("Contagem incorreta.");
        String sensores = obter(http, base + "/sensores", "GET"); if (!sensores.contains("UTI")) throw new AssertionError("Setor ausente.");
        obter(http, base + "/ordenar", "POST"); String pares = obter(http, base + "/analise/pares", "POST"); if (!pares.contains("pares=1")) throw new AssertionError("Análise O(N²) incorreta.");
        servidor.parar(); System.out.println("Teste do servidor passou.");
    }
    private static void enviar(HttpClient h,String base,String corpo)throws Exception{HttpResponse<String> r=h.send(HttpRequest.newBuilder(URI.create(base+"/leituras")).POST(HttpRequest.BodyPublishers.ofString(corpo)).build(),HttpResponse.BodyHandlers.ofString());if(r.statusCode()!=201)throw new AssertionError(r.body());}
    private static String obter(HttpClient h,String url,String metodo)throws Exception{HttpRequest.Builder b=HttpRequest.newBuilder(URI.create(url));HttpRequest r="GET".equals(metodo)?b.GET().build():b.POST(HttpRequest.BodyPublishers.noBody()).build();HttpResponse<String>x=h.send(r,HttpResponse.BodyHandlers.ofString());if(x.statusCode()!=200)throw new AssertionError(x.body());return x.body();}
}
