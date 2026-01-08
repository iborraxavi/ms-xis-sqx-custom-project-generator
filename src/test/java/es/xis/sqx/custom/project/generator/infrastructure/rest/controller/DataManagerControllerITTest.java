package es.xis.sqx.custom.project.generator.infrastructure.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(
    scripts = {"/data/drop-table.sql", "/data/create-table.sql", "/data/insert-data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DataManagerControllerITTest {

  private static final String DATA_MANAGER_LIST_PATH = "/api/data-manager/v1/data";
  private static final String DATA_MANAGER_FIND_BY_SYMBOL_NAME_PATH =
      DATA_MANAGER_LIST_PATH.concat("/%s");

  @Autowired private MockMvc mockMvc;

  @Autowired private DataSource dataSource;

  @Test
  void listData_whenExistData_shouldReturnAllRecords() throws Exception {
    mockMvc
        .perform(get(DATA_MANAGER_LIST_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].symbol").value("Symbol 01"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].symbol").value("Symbol 02"))
        .andExpect(jsonPath("$[2].id").value(3))
        .andExpect(jsonPath("$[2].symbol").value("Symbol 03"));
  }

  @Test
  void listData_whenNoData_shouldHandleEmptyDatabase() throws Exception {
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
      statement.execute("DELETE FROM DATA");
    }

    mockMvc
        .perform(get(DATA_MANAGER_LIST_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void getDataBySymbolName_whenExistData_shouldReturnExpectedRecord() throws Exception {
    final String symbolName = "Symbol 02";

    mockMvc
        .perform(get(DATA_MANAGER_FIND_BY_SYMBOL_NAME_PATH.formatted(symbolName)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.symbol").value(symbolName))
        .andExpect(jsonPath("$.dateFrom").value(1141948800000L))
        .andExpect(jsonPath("$.dateTo").value(1758671940000L))
        .andExpect(jsonPath("$.instrument.instrument").value("Instrument 02"));
  }

  @Test
  void getDataBySymbolName_whenNonExistData_shouldReturnExpectedError() throws Exception {
    final String symbolName = "NonExistingSymbolName";

    mockMvc
        .perform(get(DATA_MANAGER_FIND_BY_SYMBOL_NAME_PATH.formatted(symbolName)))
        .andExpect(status().isNotFound());
  }
}
