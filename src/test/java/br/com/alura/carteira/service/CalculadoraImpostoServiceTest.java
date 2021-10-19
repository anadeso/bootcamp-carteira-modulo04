package br.com.alura.carteira.service;

import br.com.alura.carteira.entities.TipoTransacao;
import br.com.alura.carteira.entities.Transacao;
import br.com.alura.carteira.entities.Usuario;
import br.com.alura.carteira.services.CalculadoraImpostoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculadoraImpostoServiceTest {

    @Test
    public void transacaoDoTipoCompraNaoDeveriaTerImposto(){
        Transacao transacao = new Transacao(
                120L,
                "BBSE3",
                new BigDecimal("30.00"),
                10,
                LocalDate.now(),
                TipoTransacao.COMPRA,
                new Usuario(1L, "Rafaela", "rafaela@gmail.com", "12344")
        );

        CalculadoraImpostoService calculadoraImpostoService = new CalculadoraImpostoService();
        BigDecimal imposto = calculadoraImpostoService.calcular(transacao);

        Assertions.assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    public void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
        Transacao transacao = new Transacao(
                120L,
                "BBSE3",
                new BigDecimal("30.00"),
                10,
                LocalDate.now(),
                TipoTransacao.VENDA,
                new Usuario(1L, "Rafaela", "rafaela@gmail.com", "12344"));

        CalculadoraImpostoService calculadoraImpostoService = new CalculadoraImpostoService();
        BigDecimal imposto = calculadoraImpostoService.calcular(transacao);

        Assertions.assertEquals(BigDecimal.ZERO, imposto);
    }

    @Test
    public  void transacaoDoTipoVendaComValorMaiorQueVinteMilDeveriaTerImposto() {
        Transacao transacao = new Transacao(
                120L,
                "BBSE3",
                new BigDecimal("1000.00"),
                30,
                LocalDate.now(),
                TipoTransacao.VENDA,
                new Usuario(1L, "Rafaela", "rafaela@gmail.com", "12344"));

        CalculadoraImpostoService calculadoraImpostoService = new CalculadoraImpostoService();
        BigDecimal imposto = calculadoraImpostoService.calcular(transacao);

        Assertions.assertEquals(new BigDecimal("4500.00"), imposto);

    }
}
