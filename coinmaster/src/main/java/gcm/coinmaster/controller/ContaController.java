package gcm.coinmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaPoupanca;
import gcm.coinmaster.model.ContaBonus;
import gcm.coinmaster.model.DTO.ContaDTO;
import gcm.coinmaster.model.DTO.TransferenciaDTO;
import gcm.coinmaster.service.ContaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/banco/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestParam String numero, @RequestParam Double saldo) {
        Conta conta = contaService.cadastrarConta(numero, saldo);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/poupanca")
    public ResponseEntity<ContaPoupanca> cadastrarContaPoupanca(@RequestParam String numero, @RequestParam(required = false) Double saldoInicial) {
        ContaPoupanca contaPoupanca = contaService.cadastrarContaPoupanca(numero, saldoInicial);
        return ResponseEntity.ok(contaPoupanca);
    }

    @PutMapping("/{numero}/rendimento")
    public ResponseEntity<ContaPoupanca> renderJuros(@PathVariable String numero, @RequestParam double taxa) { //taxa = 10 quer dizer 10%
        ContaPoupanca contaPoupanca;
        contaPoupanca = contaService.renderJuros(numero, taxa);
        return ResponseEntity.ok(contaPoupanca);
    }

    @PutMapping("/rendimento")
    public ResponseEntity<List<ContaPoupanca>> renderJurosTodos(@RequestParam double taxa) {
        List<ContaPoupanca> contaPoupancas;
        contaPoupancas = contaService.renderJurosTodos(taxa);
        return ResponseEntity.ok(contaPoupancas);
    }
    @PostMapping("/bonus")
    public ResponseEntity<Conta> cadastrarContaBonus(@RequestParam String numero) {
        ContaBonus conta = contaService.cadastrarContaBonus(numero);
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/transferencia")
    public ResponseEntity<TransferenciaDTO> fazerTransferencia(@RequestParam String origem, @RequestParam String destino, @RequestParam double valor) {
        TransferenciaDTO transferenciaDTO = contaService.fazerTransferencia(origem, destino, valor);
        return ResponseEntity.ok(transferenciaDTO);
    }

    @PutMapping("/{numero}/credito")
    public ResponseEntity<Conta> porCredito(@PathVariable String numero, @RequestParam double credito) {
        Conta conta = contaService.porCredito(numero, credito);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/{numero}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable String numero) {
        double saldo = contaService.consultarSaldo(numero);
        return ResponseEntity.ok(saldo);
    } 

    @PutMapping("/{numero}/debito")
    public ResponseEntity<Conta> debito(@PathVariable String numero, @RequestParam double valor) {
        Conta conta = contaService.debito(numero, valor);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<ContaDTO> consultarConta(@PathVariable String numero) {
        ContaDTO conta = contaService.consultarConta(numero);
        return ResponseEntity.ok(conta);
    }

    @GetMapping
    public List<ContaDTO> consultarContas() {
        List<ContaDTO> contas = contaService.consultarContas();
        return contas;
    }
    
}