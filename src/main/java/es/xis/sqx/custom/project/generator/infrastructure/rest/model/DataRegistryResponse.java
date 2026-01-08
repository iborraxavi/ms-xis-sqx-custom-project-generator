package es.xis.sqx.custom.project.generator.infrastructure.rest.model;

public record DataRegistryResponse(
    Integer id, String symbol, Long dateFrom, Long dateTo, InstrumentResponse instrument) {}
