package es.xis.sqx.custom.project.generator.domain.model;

public record DataRegistry(
    Integer id, String symbol, Long dateFrom, Long dateTo, Instrument instrument) {}
