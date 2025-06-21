package gcm.coinmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gcm.coinmaster.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, String> {
}

