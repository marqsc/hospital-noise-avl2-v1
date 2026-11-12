package br.edu.ifba.complexidade.noise.cliente;
import java.io.*; import java.net.*; import java.net.http.*;
/** Comunicação HTTP nativa com o servidor. */
public final class ClienteHttp {
    private final HttpClient http=HttpClient.newHttpClient(); private final String base;
    /** Complexidade O(1): monta a URL-base. */ public ClienteHttp(String host,int porta){base="http://"+host+":"+porta;}
    /** Complexidade O(1), sem rede: envia uma leitura individual. */ public void enviar(LeituraRuido l)throws IOException,InterruptedException{ executar(HttpRequest.newBuilder(URI.create(base+"/leituras")).header("Content-Type","text/plain; charset=utf-8").POST(HttpRequest.BodyPublishers.ofString(l.toCsv())).build(),201); }
    /** Complexidade O(1), sem rede: consulta uma rota. */ public String obter(String rota)throws IOException,InterruptedException{return executar(HttpRequest.newBuilder(URI.create(base+rota)).GET().build(),200);}
    /** Complexidade O(1), sem rede: chama uma operação do servidor. */ public String postar(String rota)throws IOException,InterruptedException{return executar(HttpRequest.newBuilder(URI.create(base+rota)).POST(HttpRequest.BodyPublishers.noBody()).build(),200);}
    /** Complexidade O(1), sem rede: valida status e devolve texto. */ private String executar(HttpRequest r,int esperado)throws IOException,InterruptedException{HttpResponse<String>x=http.send(r,HttpResponse.BodyHandlers.ofString());if(x.statusCode()!=esperado)throw new IOException(x.body());return x.body();}
}
