package br.edu.infnet.escola_irlan_api.professor;

import br.edu.infnet.escola_irlan_api.bibliotecario.Bibliotecario;
import br.edu.infnet.escola_irlan_api.bibliotecario.CargoBibliotecaEnum;
import br.edu.infnet.escola_irlan_api.bibliotecario.TurnoBibliotecaEnum;
import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class FuncionarioBatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<FuncionarioCSV> funcionarioReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<FuncionarioCSV>()
                .name("funcionarioReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("tipo", "nome", "cpf", "email", "dataNascimento", "registroProfissional", "cargo", "turno")
                .targetType(FuncionarioCSV.class)
                .build();
    }

    @Bean
    public ItemProcessor<FuncionarioCSV, Pessoa> funcionarioProcessor() {
        return item -> {
            Pessoa pessoa;
            if ("PROFESSOR".equalsIgnoreCase(item.tipo())) {
                Professor prof = new Professor();
                prof.setRegistroProfissional(item.registroProfissional());
                pessoa = prof;
            } else if ("BIBLIOTECARIO".equalsIgnoreCase(item.tipo())) {
                Bibliotecario bib = new Bibliotecario();
                bib.setCargo(CargoBibliotecaEnum.valueOf(item.cargo().toUpperCase()));
                bib.setTurno(TurnoBibliotecaEnum.valueOf(item.turno().toUpperCase()));
                pessoa = bib;
            } else {
                return null;
            }
            pessoa.setNome(item.nome());
            pessoa.setCpf(item.cpf());
            pessoa.setEmail(item.email());
            pessoa.setDataNascimento(LocalDate.parse(item.dataNascimento(), DateTimeFormatter.ISO_DATE));
            pessoa.setAtivo(true);
            pessoa.setDataCadastro(LocalDate.now());
            return pessoa;
        };
    }

    @Bean
    public JpaItemWriter<Pessoa> funcionarioWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Pessoa>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step funcionarioStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                FlatFileItemReader<FuncionarioCSV> funcionarioReader, JpaItemWriter<Pessoa> funcionarioWriter) {
        return new StepBuilder("funcionarioStep", jobRepository)
                .<FuncionarioCSV, Pessoa>chunk(10, transactionManager)
                .reader(funcionarioReader)
                .processor(funcionarioProcessor())
                .writer(funcionarioWriter)
                .build();
    }

    @Bean
    public Job importFuncionarioJob(JobRepository jobRepository, Step funcionarioStep) {
        return new JobBuilder("importFuncionarioJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(funcionarioStep)
                .build();
    }
}
