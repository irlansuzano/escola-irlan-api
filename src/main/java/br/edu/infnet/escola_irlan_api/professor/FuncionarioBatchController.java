package br.edu.infnet.escola_irlan_api.professor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/funcionarios/batch")
public class FuncionarioBatchController {

    private final JobLauncher jobLauncher;
    private final Job importFuncionarioJob;

    public FuncionarioBatchController(JobLauncher jobLauncher, @Qualifier("importFuncionarioJob") Job importFuncionarioJob) {
        this.jobLauncher = jobLauncher;
        this.importFuncionarioJob = importFuncionarioJob;
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("funcionarios_", ".csv");
            file.transferTo(tempFile.toFile());

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", tempFile.toString())
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(importFuncionarioJob, jobParameters);

            return ResponseEntity.ok("Processamento em lote de funcionários iniciado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao iniciar processamento: " + e.getMessage());
        }
    }
}
