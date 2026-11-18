Feature: Inscripción a cursos
  El estudiante I2024001 ya está inscrito en el curso 1

  Scenario: Inscripción correcta
    Given un estudiante nuevo con código "I202502247"
    When se inscribe en el curso 2
    Then el sistema responde 200

  Scenario: Inscripción duplicada
    Given el estudiante "I2024001" ya inscrito en el curso 1
    When intenta inscribirse de nuevo en el curso 1
    Then el sistema rechaza con error