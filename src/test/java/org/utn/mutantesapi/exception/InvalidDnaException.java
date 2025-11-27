package org.utn.mutantesapi.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidDnaExceptionTest {

    @Test
    void testInvalidDnaExceptionWithMessage() {
        String expectedMessage = "La secuencia de ADN es inválida.";

        // Ejecuta el constructor de la excepción para cubrir el código (super(message))
        InvalidDnaException exception = new InvalidDnaException(expectedMessage);

        // Verifica que la excepción contenga el mensaje
        assertEquals(expectedMessage, exception.getMessage());
    }
}