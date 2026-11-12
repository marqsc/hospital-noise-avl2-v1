# Monitoramento de Ruído Hospitalar - Avaliação II, Versão 1

Versão cliente-servidor sem otimizações da disciplina Complexidade de Algoritmos. O cliente lê um CSV com 10.000 leituras de 10 decibelímetros e envia **cada** leitura ao servidor por HTTP. O servidor mantém todas as leituras, permite listar e ordenar os dados e executa a análise de pares críticos semelhantes em O(N²).

## Estrutura

```text
cliente/    programa que lê o CSV e cria 10 threads de envio
servidor/   programa HTTP que recebe e processa as leituras
```

Cada pasta é um projeto Java independente, com código, testes e scripts próprios.

## Execução

Em dois terminais PowerShell, execute primeiro o servidor:

```powershell
cd servidor
.\scripts\run.ps1
```

Em seguida, execute o cliente:

```powershell
cd cliente
.\scripts\run.ps1
```

Os valores padrão são `127.0.0.1` e porta `5000`. Os scripts aceitam `-Host` e `-Port` para alterar o destino. No menu do cliente, envie o CSV e depois use as opções de consulta, ordenação e análise.

## Protocolo HTTP

- `POST /leituras`: recebe uma linha CSV em `text/plain`.
- `GET /status`: retorna quantidade de leituras armazenadas.
- `GET /sensores`: lista os 10 setores.
- `GET /leituras`: imprime todas as leituras armazenadas.
- `POST /ordenar`: executa Bubble Sort manual no servidor.
- `POST /analise/pares`: executa a funcionalidade O(N²) no servidor.

## Testes

```powershell
cd servidor
.\scripts\test.ps1

cd ..\cliente
.\scripts\test.ps1
```

Não há controle por limiar, fila limitada ou deslocamento da análise ao cliente nesta versão. Essas otimizações pertencem à versão 2.
