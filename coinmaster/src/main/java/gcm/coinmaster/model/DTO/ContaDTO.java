package gcm.coinmaster.model.DTO;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaBonus;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ContaDTO {
    private String numero;
    private double saldo;
    private String tipo;
    private Integer pontuacao;

    public ContaDTO(Conta conta) {
        this.numero = conta.getNumero();
        this.saldo = conta.getSaldo();
        this.tipo = conta.getClass().getSimpleName();
        if (conta instanceof ContaBonus) {
            this.pontuacao = ((ContaBonus) conta).getPontuacao();
        }
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getPontuacao() {
        return pontuacao;
    }
    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}
