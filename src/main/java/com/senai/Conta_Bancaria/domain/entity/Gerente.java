package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;

import java.util.List;

public class Gerente extends Conta{
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gerente_contas", joinColumns=@JoinColumn(name="gerente_id"))
    @Column(name="conta")
    private List<String > listaDeTurmas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="professor_ucs", joinColumns=@JoinColumn(name="gerente_id"))
    @Column(name="uc")
    private List<String> listaDeUC;
}
