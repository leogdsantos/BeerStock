package com.dio.beerstock.beerstock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeerType {

    LAGER ("lager"),

    MALZBIER("Malzbier"),

    WITBIER ("Witbier"),

    WEISS ("Weiss"),

    ALE ("Ale"),

    IPA ("IPA"),

    STOUT ("Stout");

    private final String description;


}
