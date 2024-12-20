package med.voll.api.domain.consulta;

import static org.junit.jupiter.api.Assertions.*;


import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import med.voll.api.domain.endereco.dto.DadosEndereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.paciente.Paciente;

import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.dto.DadosCadastroPaciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest  // Configuração do contexto JPA para testes
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @BeforeEach
    void setUp() {
        // Criar um objeto DadosEndereco fictício
        DadosEndereco enderecoFicticio = new DadosEndereco(
                "Rua Teste", "123", "123456789", "Cidade Teste", "SP", "12345678","12"
        );

        // Criar médicos e pacientes para os testes usando os registros de dados
        Medico medico1 = new Medico(new DadosCadastroMedico(
                "Dr. João", "joao@example.com", "123456789", "12345", Especialidade.CARDIOLOGIA, enderecoFicticio
        ));
        Medico medico2 = new Medico(new DadosCadastroMedico(
                "Dr. Maria", "maria@example.com", "987654321", "67890", Especialidade.DERMATOLOGIA, enderecoFicticio
        ));
        medicoRepository.save(medico1);
        medicoRepository.save(medico2);

        Paciente paciente1 = new Paciente(new DadosCadastroPaciente(
                "Paciente A", "pacienteA@example.com", "11987654321", "12345678909", enderecoFicticio
        ));
        Paciente paciente2 = new Paciente(new DadosCadastroPaciente(
                "Paciente B", "pacienteB@example.com", "11987654322", "98765432100", enderecoFicticio
        ));
        Paciente paciente3 = new Paciente(new DadosCadastroPaciente(
                "Paciente C", "pacienteC@example.com", "11987654323", "11223344556", enderecoFicticio
        ));
        pacienteRepository.save(paciente1);
        pacienteRepository.save(paciente2);
        pacienteRepository.save(paciente3);

        // Criar consultas para os testes com datas específicas no mês de dezembro
        Consulta consulta1 = new Consulta(medico1, paciente1, LocalDateTime.of(2024, 12, 1, 10, 0));  // Dezembro
        Consulta consulta2 = new Consulta(medico1, paciente2, LocalDateTime.of(2024, 12, 2, 15, 0));  // Dezembro
        Consulta consulta3 = new Consulta(medico2, paciente3, LocalDateTime.of(2024, 12, 3, 9, 0));   // Dezembro
        consultaRepository.save(consulta1);
        consultaRepository.save(consulta2);
        consultaRepository.save(consulta3);
    }

    @Test
    void testFindConsultasMensais() {
        // Definir o mês e o ano para o relatório
        int mes = 12;  // Dezembro
        int ano = 2024;  // Ano de 2024

        // Chama o método da repository para gerar o relatório de consultas mensais
        List<DadosRelatorioConsultaMensal> relatorio = consultaRepository.findConsultasMensais(mes, ano);

        // Verificar que o relatório tem 2 médicos
        assertEquals(2, relatorio.size(), "Deve retornar dois médicos com consultas no período.");

        // Verificar as informações do primeiro médico
        DadosRelatorioConsultaMensal dadosMedico1 = relatorio.get(0);
        assertEquals("Dr. João", dadosMedico1.nome());
        assertEquals("12345", dadosMedico1.crm());
        assertEquals(2, dadosMedico1.quantidadeConsultasNoMes());

        // Verificar as informações do segundo médico
        DadosRelatorioConsultaMensal dadosMedico2 = relatorio.get(1);
        assertEquals("Dr. Maria", dadosMedico2.nome());
        assertEquals("67890", dadosMedico2.crm());
        assertEquals(1, dadosMedico2.quantidadeConsultasNoMes());
    }
}
