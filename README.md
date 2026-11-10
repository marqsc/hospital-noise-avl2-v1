# Monitoramento de Nível de Ruído em Ambientes Hospitalares

Projeto acadêmico da disciplina **Complexidade de Algoritmos**.

O programa será uma aplicação Java de console que simula decibelímetros em setores de um hospital. Ele deverá gerar, listar, ordenar e analisar leituras de ruído.

## Requisitos locais

- JDK 21 ou superior (o ambiente atual possui JDK 26);
- Git;
- Maven é opcional nesta etapa; o projeto pode ser compilado diretamente com o JDK.

## Estrutura

```text
src/main/java/     código da aplicação
src/test/java/     testes automatizados
scripts/           comandos locais para compilar, executar e testar
```

## Executar

No PowerShell:

```powershell
.\scripts\run.ps1
```

## Executar testes

```powershell
.\scripts\test.ps1
```

## Convenções

- Pacote-base: `br.edu.ifba.complexidade.noise`.
- Cada método deverá informar, em comentário, sua complexidade Big-O, a justificativa e o impacto de entradas grandes.
- Não utilizar métodos prontos de ordenação, filtragem ou acumulação para as funcionalidades exigidas.
- Começar os testes com poucas leituras e aumentar gradualmente o volume de dados.

## Funcionalidades implementadas

- Geração de leituras aleatórias para 10 setores hospitalares;
- listagem dos setores e de suas leituras;
- ordenação manual crescente por Bubble Sort;
- identificação de pares de leituras críticas semelhantes, em O(N²);
- menu de console com validação de entradas;
- testes automatizados executáveis somente com o JDK.
