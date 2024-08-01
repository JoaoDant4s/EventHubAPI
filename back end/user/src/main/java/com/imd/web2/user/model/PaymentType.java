package com.imd.web2.user.model;

public enum PaymentType {
    Pix("Pix"),
    CartaoCredito("Cartão de Crédito"),
    Boleto("Boleto");

    private final String name;

    PaymentType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
