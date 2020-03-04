package es.uji.ei1027.elderlypeople.services;

import java.util.Map;

import es.uji.ei1027.elderlypeople.model.Nadador;

import java.util.List;

public interface ClassificacioService {
    public Map<String, List<Nadador>> getClassificationByCountry(String prova);
}
