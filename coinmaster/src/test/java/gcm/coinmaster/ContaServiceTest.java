package gcm.coinmaster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaBonus;
import gcm.coinmaster.model.ContaPoupanca;
import gcm.coinmaster.model.DTO.ContaDTO;
import gcm.coinmaster.repository.ContaRepository;
import gcm.coinmaster.service.ContaService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    @Mock private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Nested
    class CadastrarConta{

        @Test
        void testarCadastrarConta() {
            String numero = "12345";
            Double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.save(any())).thenReturn(conta);
            assertEquals(new Conta(numero, saldo), contaService.cadastrarConta(numero, saldo));
        }

        @Test
        void testarCadastrarContaBonus() {
            String numero = "12345";
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(0.0);
            contaBonus.setPontuacao(10);
            when(contaRepository.save(any())).thenReturn(contaBonus);
            ContaBonus contaBonus2 = new ContaBonus();
            contaBonus2.setNumero(numero);
            contaBonus2.setSaldo(0.0);
            contaBonus2.setPontuacao(10);
            assertEquals(contaBonus2, contaService.cadastrarContaBonus(numero));
        }

        @Test
        void testarCadastrarContaPoupanca() {
            String numero = "12345";
            double saldo = 10.0;
            ContaPoupanca contaPoupanca = new ContaPoupanca();
            contaPoupanca.setNumero(numero);
            contaPoupanca.setSaldo(saldo);
            when(contaRepository.save(any())).thenReturn(contaPoupanca);
            ContaPoupanca contaPoupanca2 = new ContaPoupanca();
            contaPoupanca2.setNumero(numero);
            contaPoupanca2.setSaldo(saldo);
            assertEquals(contaPoupanca2, contaService.cadastrarContaPoupanca(numero, saldo));
        }

    }

    @Nested
    class ConsultarConta{

        @Test
        void testarConsultarConta() {
            String numero = "12345";
            double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            ContaDTO contaDTO = new ContaDTO(conta);
            ContaDTO contaDTO2 = new ContaDTO(conta);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(contaDTO2, contaService.consultarConta(numero));
        }

        @Test
        void testarConsultarContaBonus() {
            String numero = "12345";
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(0.0);
            contaBonus.setPontuacao(10);
            ContaDTO contaDTO = new ContaDTO(contaBonus);
            ContaDTO contaDTO2 = new ContaDTO(contaBonus);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaBonus));
            assertEquals(contaDTO2, contaService.consultarConta(numero));
        }

        @Test
        void testarConsultarContaPoupanca() {
            String numero = "12345";
            double saldo = 10.0;
            ContaPoupanca contaPoupanca = new ContaPoupanca();
            contaPoupanca.setNumero(numero);
            contaPoupanca.setSaldo(saldo);
            ContaDTO contaDTO = new ContaDTO(contaPoupanca);
            ContaDTO contaDTO2 = new ContaDTO(contaPoupanca);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaPoupanca));
            assertEquals(contaDTO2, contaService.consultarConta(numero));
        }

    }

    @Nested
    class ConsultarSaldo{
        @Test
        void testarConsultarSaldo() {
            String numero = "12345";
            Double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.consultarSaldo(numero));
        }        
    }

    @Nested
    class Credito{
        
        @Test
        void TestarCredito(){
            String numero = "12345";
            double saldo = 500.0;
            double credito = 200.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo+credito, contaService.porCredito(numero, credito).getSaldo());            
        }

        @Test
        void TestarCreditoNegativo(){
            String numero = "12345";
            double credito = -200.0;
            assertEquals(null, contaService.porCredito(numero, credito));            
        }

        @Test
        void TestarCreditoContaBonus(){
            String numero = "12345";
            double saldo = 500.0;
            double credito = 200.0;
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(saldo);
            contaBonus.setPontuacao(0);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaBonus));
            ContaBonus contaRetornada = (ContaBonus) contaService.porCredito(numero, credito);
            assertEquals(credito/100, contaRetornada.getPontuacao(), 0.00001);            
        }
    }

    @Nested
    class Debito{
        
        @Test
        void TestarDebito(){
            String numero = "12345";
            double saldo = 500.0;
            double debito = 200.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo-debito, contaService.debito(numero, debito).getSaldo());            
        }

        @Test
        void TestarDebitoNegativo(){
            String numero = "12345";
            double debito = -200.0;
            assertEquals(null, contaService.debito(numero, debito));            
        }

        // O saldo pode ficar até -1000.0, pois é o limite máximo de saldo negativo 
        @Test
        void TestarDebitoSaldoNegativoMenosMilContaSimples(){
            String numero = "12345";
            double saldo = 500.0;
            double debito = 12000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.debito(numero, debito).getSaldo());            
        }

        @Test
        void TestarDebitoNaoPodeSaldoNegativoNaContaPoupanca(){
            String numero = "12345";
            double saldo = 500.0;
            double debito = 501.0;
            ContaPoupanca conta = new ContaPoupanca();
            conta.setNumero(numero);
            conta.setSaldo(saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.debito(numero, debito).getSaldo());            
        }
    }
    
    @Nested
    class Transferencia{

        @Test
        void TestarTransferenciaNegativa() {
            String numero = "12345";
            double valor = -200.0;
            assertEquals(null, contaService.fazerTransferencia(numero, numero, valor));    
        }

        @Test
        void TestarTransferenciaSaldoNegativoMenosMilContaSimples() {
            String numero = "12345";
            double saldo = 500.0;
            double valor = 12000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.fazerTransferencia(numero, numero, valor).getContaOrigem().getSaldo());    
        }

        @Test
        void TestarTransferenciaNaoPodeSaldoNegativoNaContaPoupanca(){
            String numero = "12345";
            double saldo = 200.0;
            double valor = 501.0;
            ContaPoupanca conta = new ContaPoupanca();
            conta.setNumero(numero);
            conta.setSaldo(saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.fazerTransferencia(numero, numero, valor).getContaOrigem().getSaldo());
        }
    }

    @Nested
    class RenderJuros{
        @Test
        void TestarRenderJurosTodos() {
            ContaPoupanca conta = new ContaPoupanca();
            double saldo = 10.0;
            conta.setSaldo(saldo);                   
            when(contaRepository.findAll()).thenReturn(List.of(conta));            
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            double taxa = 50.0;
            List<ContaPoupanca> contaPoupancas = contaService.renderJurosTodos(taxa);
            assertEquals(saldo+saldo*taxa/100, contaPoupancas.get(0).getSaldo());
        }
    }
}
