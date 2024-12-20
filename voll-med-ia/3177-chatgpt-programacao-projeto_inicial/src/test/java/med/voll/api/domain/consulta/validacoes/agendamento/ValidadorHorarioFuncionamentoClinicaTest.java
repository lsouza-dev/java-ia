package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidadorHorarioFuncionamentoClinicaTest {

    private ValidadorHorarioFuncionamentoClinica validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorHorarioFuncionamentoClinica();
    }

    @Test
    void naoDeveLancarExcecaoQuandoConsultaForExatamenteAs7Horas() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 7, 0)); // Quarta-feira, 7h00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    void naoDeveLancarExcecaoQuandoConsultaForExatamenteAs18Horas() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 18, 0)); // Quarta-feira, 18h00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    void deveLancarExcecaoQuandoConsultaForDeMeiaNoite() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 0, 1)); // Quarta-feira, 00h01

        // Act & Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dados),
                "Consulta fora do horário de funcionamento da clínica");
    }

    @Test
    void naoDeveLancarExcecaoQuandoConsultaForEmHorarioValidoEmDiaUtil() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 7, 0)); // Quarta-feira, 7h00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    void naoDeveLancarExcecaoQuandoConsultaForExatamenteAs18HorasEmDiaUtil() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 18, 0)); // Quarta-feira, 18h00

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }


    @Test
    void deveLancarExcecaoQuandoConsultaForNoDomingo() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 22, 10, 0)); // Domingo

        // Act & Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dados),
                "Consulta fora do horário de funcionamento da clínica");
    }

    @Test
    void deveLancarExcecaoQuandoConsultaForAntesDas7Horas() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 6, 59)); // Quarta-feira, antes das 7h

        // Act & Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dados),
                "Consulta fora do horário de funcionamento da clínica");
    }

    @Test
    void deveLancarExcecaoQuandoConsultaForDepoisDas18Horas() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 18, 1)); // Quarta-feira, após as 18h

        // Act & Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dados),
                "Consulta fora do horário de funcionamento da clínica");
    }

    @Test
    void naoDeveLancarExcecaoQuandoConsultaForEmHorarioValido() {
        // Arrange
        var dados = criarDadosAgendamento(LocalDateTime.of(2024, 12, 18, 10, 0)); // Quarta-feira, 10h

        // Act & Assert
        assertDoesNotThrow(() -> validador.validar(dados));
    }

    // Método auxiliar para criar instâncias de DadosAgendamentoConsulta
    private DadosAgendamentoConsulta criarDadosAgendamento(LocalDateTime dataConsulta) {
        return new DadosAgendamentoConsulta(
                1L,                  // idMedico (opcional)
                1L,                    // idPaciente (obrigatório)
                dataConsulta,          // Data da consulta
                Especialidade.CARDIOLOGIA                   // Especialidade (opcional para o teste atual)
        );
    }

}
