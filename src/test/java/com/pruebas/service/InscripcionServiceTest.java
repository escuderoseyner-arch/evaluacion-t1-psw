package com.pruebas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;

/**
 Alumno: Escudero Campos Seyner Joaquin
 Código: I202502247
 */

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    // Caso 1
    @Test
    @DisplayName("Caso 1 - Inscribir estudiante correctamente")
    void inscribirEstudianteCorrectamente() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCodigoEstudiante("I2024050");
        inscripcion.setCursoId(2L);

        Inscripcion guardada = new Inscripcion();
        guardada.setId(1L);
        guardada.setCodigoEstudiante("I2024050");
        guardada.setCursoId(2L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024050", 2L))
                .thenReturn(false);
        when(repository.save(inscripcion)).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(inscripcion);

        verify(repository).save(inscripcion);
        assertNotNull(resultado);
        assertEquals("I2024050", resultado.getCodigoEstudiante());
        assertEquals(2L, resultado.getCursoId());
    }

    // Caso 2
    @Test
    @DisplayName("Caso 2 - Estudiante ya inscrito lanza excepción")
    void inscribirEstudianteYaInscritoLanzaExcepcion() {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCodigoEstudiante("I2024050");
        inscripcion.setCursoId(2L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024050", 2L))
                .thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.inscribir(inscripcion);
        });

        assertEquals("Estudiante ya inscrito", ex.getMessage());
        verify(repository, never()).save(any());
    }
    // Caso 3
    @Test
    @DisplayName("Caso 3 - Listar inscripciones por estudiante")
    void listarInscripcionesPorEstudiante() {
        Inscripcion i1 = new Inscripcion();
        i1.setId(1L);
        i1.setCodigoEstudiante("I2024001");
        i1.setCursoId(1L);

        Inscripcion i2 = new Inscripcion();
        i2.setId(2L);
        i2.setCodigoEstudiante("I2024001");
        i2.setCursoId(2L);

        when(repository.findByCodigoEstudiante("I2024001"))
                .thenReturn(Arrays.asList(i1, i2));

        List<Inscripcion> lista = service.listarPorEstudiante("I2024001");

        assertEquals(2, lista.size());
        assertEquals("I2024001", lista.get(0).getCodigoEstudiante());
        assertEquals("I2024001", lista.get(1).getCodigoEstudiante());
    }
}
