package br.edu.ifba.complexidade.noise.cliente;
import java.nio.file.*;
/** Teste da leitura do CSV. */
public final class LeitorCsvTest { public static void main(String[] args)throws Exception{Path p=Files.createTempFile("leituras",".csv");StringBuilder s=new StringBuilder("timestamp,setorId,setorNome,numeroLeitura,decibeis\n");for(int i=1;i<=10;i++)s.append("2026-11-11T08:00:00Z,").append(i).append(",Setor ").append(i).append(",1,50\n");Files.writeString(p,s);LeituraRuido[][] r=new LeitorCsv().carregarPorSetor(p);if(r.length!=10||r[9][0].getSetorId()!=10)throw new AssertionError("CSV inválido");Files.deleteIfExists(p);System.out.println("Teste do CSV passou.");} }
