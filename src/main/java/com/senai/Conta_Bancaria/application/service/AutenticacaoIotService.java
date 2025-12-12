package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.domain.entity.DispositivoIOT;
import com.senai.Conta_Bancaria.domain.repository.DispositivoIotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class  AutenticacaoIotService {
    private final DispositivoIotRepository dispositivoRepository;

    @Transactional(readOnly = true)
    public boolean validarOperacao(String codigoSerial, String chavePublicaEnviada, String id) {

        // busca o dispositivo pelo número de série
        DispositivoIOT dispositivo = dispositivoRepository
                .findByCodigoSerial(codigoSerial)
                .orElse(null);

        if (dispositivo == null) {
            System.out.println(" Dispositivo não encontrado");
            return false;
        }

        // valida chave pública
        if (!dispositivo.getChavePublica().equals(chavePublicaEnviada)) {
            System.out.println("Chave pública inválida");
            return false;
        }

        // verifica se está ativo
        if (!dispositivo.getAtivo()) {
            System.out.println("Dispositivo inativo");
            return false;
        }

        // verifica se pertence ao cliente informado
        if (!dispositivo.getCliente().getId().equals(id)) {
            System.out.println(" Dispositivo não pertence ao cliente");
            return false;
        }

        System.out.println("Dispositivo IoT validado e autorizado!");
        return true;
    }
}