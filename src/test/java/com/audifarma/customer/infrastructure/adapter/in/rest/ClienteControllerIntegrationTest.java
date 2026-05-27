package com.audifarma.customer.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/clientes";

    private String createClienteJson(String numero) {
        return """
                {
                  "nombre": "Juan",
                  "apellido": "Perez",
                  "numeroDocumento": "%s",
                  "tipoDocumento": "CC",
                  "edad": 30,
                  "activo": true,
                  "direcciones": []
                }
                """.formatted(numero);
    }

    @Test
    void shouldCreateClienteAndReturn201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClienteJson("DOC-001")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.numeroDocumento").value("DOC-001"))
                .andExpect(jsonPath("$.direcciones").isArray());
    }

    @Test
    void shouldReturn409WhenDuplicateDocumento() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createClienteJson("DOC-DUP")));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClienteJson("DOC-DUP")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void shouldReturn400WhenMissingRequiredFields() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetClienteById() throws Exception {
        MvcResult created = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClienteJson("DOC-002")))
                .andReturn();

        Long id = objectMapper.readTree(created.getResponse().getContentAsString())
                .get("id").asLong();

        mockMvc.perform(get(BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.direcciones").isArray());
    }

    @Test
    void shouldReturn404WhenClienteNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + "/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldUpdateCliente() throws Exception {
        MvcResult created = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClienteJson("DOC-003")))
                .andReturn();

        Long id = objectMapper.readTree(created.getResponse().getContentAsString())
                .get("id").asLong();

        String updateJson = """
                {
                  "nombre": "Maria",
                  "apellido": "Garcia",
                  "tipoDocumento": "TI",
                  "edad": 25,
                  "activo": false
                }
                """;

        mockMvc.perform(put(BASE_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Maria"))
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void shouldAddAndRemoveDireccion() throws Exception {
        MvcResult created = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createClienteJson("DOC-004")))
                .andReturn();

        Long clienteId = objectMapper.readTree(created.getResponse().getContentAsString())
                .get("id").asLong();

        String dirJson = """
                {
                  "departamento": "Antioquia",
                  "ciudad": "Medellín",
                  "direccionCompleta": "Calle 10 #20-30"
                }
                """;

        MvcResult addedDir = mockMvc.perform(post(BASE_URL + "/" + clienteId + "/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dirJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.ciudad").value("Medellín"))
                .andReturn();

        Long dirId = objectMapper.readTree(addedDir.getResponse().getContentAsString())
                .get("id").asLong();

        // Verify address is returned with customer
        mockMvc.perform(get(BASE_URL + "/" + clienteId))
                .andExpect(jsonPath("$.direcciones", hasSize(1)));

        // Remove the address
        mockMvc.perform(delete(BASE_URL + "/" + clienteId + "/direcciones/" + dirId))
                .andExpect(status().isNoContent());

        // Verify it's gone
        mockMvc.perform(get(BASE_URL + "/" + clienteId))
                .andExpect(jsonPath("$.direcciones", hasSize(0)));
    }

    @Test
    void shouldReturn404WhenRemovingDireccionOfUnknownCliente() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/9999/direcciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateClienteWithDireccionesInPayload() throws Exception {
        String json = """
                {
                  "nombre": "Ana",
                  "apellido": "Torres",
                  "numeroDocumento": "DOC-WITH-DIR",
                  "tipoDocumento": "CC",
                  "edad": 28,
                  "activo": true,
                  "direcciones": [
                    {
                      "departamento": "Valle",
                      "ciudad": "Cali",
                      "direccionCompleta": "Avenida 6 #12-34"
                    }
                  ]
                }
                """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.direcciones", hasSize(1)))
                .andExpect(jsonPath("$.direcciones[0].id").isNumber())
                .andExpect(jsonPath("$.direcciones[0].ciudad").value("Cali"));
    }

    @Test
    void shouldCreateClienteWithoutDireccionesWhenFieldOmitted() throws Exception {
        String json = """
                {
                  "nombre": "Carlos",
                  "apellido": "Ruiz",
                  "numeroDocumento": "DOC-NO-DIR",
                  "tipoDocumento": "TI",
                  "edad": 20,
                  "activo": true
                }
                """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.direcciones").isArray())
                .andExpect(jsonPath("$.direcciones", hasSize(0)));
    }
}
