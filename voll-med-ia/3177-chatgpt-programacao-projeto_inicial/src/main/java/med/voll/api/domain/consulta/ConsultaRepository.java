package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Page<Consulta> findAllByDataGreaterThan(LocalDateTime data, Pageable paginacao);

    @Query("""
            SELECT new med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal(c.medico.nome, c.medico.crm, COUNT(c))
            FROM Consulta c
            WHERE MONTH(c.data) = :mes AND YEAR(c.data) = :ano
            GROUP BY c.medico.nome, c.medico.crm
            """)
    List<DadosRelatorioConsultaMensal> findConsultasMensais(int mes, int ano);


}
