package es.xis.sqx.custom.project.generator.application.usecase.find_data_by_symbol_name;

import es.xis.sqx.custom.project.generator.application.model.InstrumentResponse;

public record FindDataBySymbolNameResponse(
    Integer id, String symbol, Long dateFrom, Long dateTo, InstrumentResponse instrument) {}
