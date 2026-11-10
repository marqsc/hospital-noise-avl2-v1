package br.edu.ifba.complexidade.noise.service;

import br.edu.ifba.complexidade.noise.model.LeituraRuido;
import br.edu.ifba.complexidade.noise.model.ParCritico;
import br.edu.ifba.complexidade.noise.model.SetorHospitalar;
import java.util.Random;

/**
 * Testes automatizados sem dependência externa, executáveis diretamente pelo JDK.
 */
public final class MonitoramentoRuidoServiceTest {
    private MonitoramentoRuidoServiceTest() {
    }

    public static void main(String[] args) {
        deveGerarDezSetoresComQuantidadeSolicitada();
        deveOrdenarLeiturasEmOrdemCrescente();
        deveEncontrarSomenteParesCriticosSemelhantes();
        deveRejeitarQuantidadeInvalida();
        System.out.println("Todos os testes passaram.");
    }

    private static void deveGerarDezSetoresComQuantidadeSolicitada() {
        MonitoramentoRuidoService service = new MonitoramentoRuidoService(new Random(7));
        SetorHospitalar[] setores = service.gerarMonitoramento(3);

        verificar(setores.length == 10, "A geração deve criar 10 setores.");
        for (SetorHospitalar setor : setores) {
            verificar(setor.getLeituras().length == 3, "Cada setor deve receber 3 leituras.");
            for (LeituraRuido leitura : setor.getLeituras()) {
                verificar(leitura.getSetorId() == setor.getId(), "A leitura deve identificar o seu setor.");
                verificar(leitura.getDecibeis() >= 30 && leitura.getDecibeis() <= 100, "O valor deve estar no intervalo permitido.");
            }
        }
    }

    private static void deveOrdenarLeiturasEmOrdemCrescente() {
        MonitoramentoRuidoService service = new MonitoramentoRuidoService(new Random(1));
        SetorHospitalar[] setores = criarSetoresComLeituras(90, 40, 70);

        service.ordenarLeiturasCrescentemente(setores);

        LeituraRuido[] leituras = setores[0].getLeituras();
        verificar(leituras[0].getDecibeis() == 40, "A menor leitura deve ficar no início.");
        verificar(leituras[1].getDecibeis() == 70, "A leitura intermediária deve ficar no meio.");
        verificar(leituras[2].getDecibeis() == 90, "A maior leitura deve ficar no fim.");
    }

    private static void deveEncontrarSomenteParesCriticosSemelhantes() {
        MonitoramentoRuidoService service = new MonitoramentoRuidoService(new Random(1));
        SetorHospitalar[] setores = criarSetoresComLeituras(70, 74, 80);

        ParCritico[] pares = service.encontrarParesCriticosSemelhantes(setores);

        verificar(pares.length == 10, "Cada setor deve produzir somente o par crítico formado pelas leituras 70 e 74.");
        verificar(pares[0].getSetorId() == 1, "O par deve indicar o setor correto.");
    }

    private static void deveRejeitarQuantidadeInvalida() {
        MonitoramentoRuidoService service = new MonitoramentoRuidoService(new Random(1));
        try {
            service.gerarMonitoramento(0);
            throw new AssertionError("A geração deveria rejeitar zero leituras.");
        } catch (IllegalArgumentException exception) {
            verificar("A quantidade de leituras deve ser maior que zero.".equals(exception.getMessage()), "A mensagem de validação deve ser clara.");
        }
    }

    private static SetorHospitalar[] criarSetoresComLeituras(int primeiraLeitura, int segundaLeitura, int terceiraLeitura) {
        SetorHospitalar[] setores = new SetorHospitalar[10];
        for (int indice = 0; indice < setores.length; indice++) {
            LeituraRuido[] leituras = {
                new LeituraRuido(indice + 1, 1, primeiraLeitura),
                new LeituraRuido(indice + 1, 2, segundaLeitura),
                new LeituraRuido(indice + 1, 3, terceiraLeitura)
            };
            setores[indice] = new SetorHospitalar(indice + 1, "Setor " + (indice + 1), leituras);
        }
        return setores;
    }

    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
