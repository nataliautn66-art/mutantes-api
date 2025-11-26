package org.utn.mutantesapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.utn.mutantesapi.dto.DnaRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para MutantController.
 * Prueban los endpoints completos con Spring Boot Test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ========== TESTS DE POST /mutant ==========

    @Test
    @DisplayName("POST /mutant debe retornar 200 OK para mutante")
    void testCheckMutant_ReturnOk_WhenIsMutant() throws Exception {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 403 FORBIDDEN para humano")
    void testCheckMutant_ReturnForbidden_WhenIsHuman() throws Exception {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 para DNA null")
    void testCheckMutant_ReturnBadRequest_WhenDnaIsNull() throws Exception {
        DnaRequest request = new DnaRequest(null);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 para DNA con caracteres inválidos")
    void testCheckMutant_ReturnBadRequest_WhenInvalidCharacters() throws Exception {
        String[] dna = {
                "ATXC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString("Invalid character")
                ));
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 para matriz no cuadrada")
    void testCheckMutant_ReturnBadRequest_WhenNotSquare() throws Exception {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT"  // 3x4 (no cuadrada)
        };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant debe retornar 400 para matriz muy pequeña")
    void testCheckMutant_ReturnBadRequest_WhenTooSmall() throws Exception {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"  // 3x3 (menor a 4x4)
        };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString("at least 4x4")
                ));
    }

    // ========== TESTS DE GET /stats ==========

    @Test
    @DisplayName("GET /stats debe retornar 200 OK con estadísticas")
    void testGetStats_ReturnOk() throws Exception {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.ratio").exists());
    }

    @Test
    @DisplayName("GET /stats debe retornar ratio correcto")
    void testGetStats_CorrectRatio() throws Exception {
        // Primero agregar datos conocidos
        String[] mutant = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        String[] human = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };

        // Enviar mutante
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DnaRequest(mutant))));

        // Enviar humano
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DnaRequest(human))));

        // Verificar stats
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(org.hamcrest.Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.count_human_dna").value(org.hamcrest.Matchers.greaterThan(0)));
    }
}