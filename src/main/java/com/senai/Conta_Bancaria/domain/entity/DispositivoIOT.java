package com.senai.Conta_Bancaria.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "dispositivo_iot")
public class  DispositivoIOT {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // Identificador único do dispositivo

    @Column(nullable = false, unique = true, length = 100)
    private String codigoSerial; // Número de série único do dispositivo

    @Column(nullable = false, columnDefinition = "TEXT")
    private String chavePublica; // Chave pública usada na comunicação segura

    @Column(nullable = false)
    private Boolean ativo; // Indica se o dispositivo está ativo

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Relacionamento com o cliente proprietário do dispositivo
}

