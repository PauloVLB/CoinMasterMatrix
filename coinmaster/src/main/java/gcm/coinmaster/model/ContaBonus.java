package gcm.coinmaster.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ContaBonus extends Conta {
    private Integer pontuacao;
}
