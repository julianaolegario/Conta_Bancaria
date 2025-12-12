package com.senai.Conta_Bancaria.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "dispositivo_iot")
public class  DispositivoIOT {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // identificador único do dispositivo

    @Column(nullable = false, unique = true, length = 100)
    private String codigoSerial; // número de série único do dispositivo

    @Column(nullable = false, columnDefinition = "TEXT")
    private String chavePublica; // chave pública usada na comunicação segura

    @Column(nullable = false)
    private Boolean ativo; // indica se o dispositivo está ativo

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // relacionamento com o cliente proprietário do dispositivo
}

