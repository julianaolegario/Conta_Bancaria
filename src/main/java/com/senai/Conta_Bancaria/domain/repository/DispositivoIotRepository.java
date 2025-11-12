package com.senai.Conta_Bancaria.domain.repository;

import com.senai.Conta_Bancaria.domain.entity.DispositivoIOT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoIotRepository extends JpaRepository<DispositivoIOT, String> {
}
