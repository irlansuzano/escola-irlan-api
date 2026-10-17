package br.edu.infnet.escola_irlan_api.aluno;

import br.edu.infnet.escola_irlan_api.turma.Turma;
import br.edu.infnet.escola_irlan_api.turma.TurmaRepository;
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
public class AlunoBatchConfig {

    private final TurmaRepository turmaRepository;

    public AlunoBatchConfig(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<AlunoCSV> studentReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<AlunoCSV>()
                .name("studentReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("nome", "cpf", "email", "dataNascimento", "matricula", "turmaId")
                .targetType(AlunoCSV.class)
                .build();
    }

    @Bean
    public ItemProcessor<AlunoCSV, Aluno> studentProcessor() {
        return item -> {
            Aluno aluno = new Aluno();
            aluno.setNome(item.nome());
            aluno.setCpf(item.cpf());
            aluno.setEmail(item.email());
            aluno.setDataNascimento(LocalDate.parse(item.dataNascimento(), DateTimeFormatter.ISO_DATE));
            aluno.setMatricula(item.matricula());
            aluno.setAtivo(true);
            aluno.setDataCadastro(LocalDate.now());

            if (item.turmaId() != null) {
                Turma turma = turmaRepository.findById(item.turmaId()).orElse(null);
                aluno.setTurma(turma);
            }
            return aluno;
        };
    }

    @Bean
    public JpaItemWriter<Aluno> studentWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Aluno>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step studentStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           FlatFileItemReader<AlunoCSV> studentReader, JpaItemWriter<Aluno> studentWriter) {
        return new StepBuilder("studentStep", jobRepository)
                .<AlunoCSV, Aluno>chunk(10, transactionManager)
                .reader(studentReader)
                .processor(studentProcessor())
                .writer(studentWriter)
                .build();
    }

    @Bean
    public Job importStudentJob(JobRepository jobRepository, Step studentStep) {
        return new JobBuilder("importStudentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(studentStep)
                .build();
    }
}
