package com.senai.Conta_Bancaria.domain.repository;

import com.senai.Conta_Bancaria.domain.entity.DispositivoIOT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DispositivoIotRepository extends JpaRepository<DispositivoIOT, String> {
    Optional<DispositivoIOT> findByCodigoSerial(String codigoSerial);
}
