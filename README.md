# Escola Irlan API

Uma API REST desenvolvida em Spring Boot para o gerenciamento de uma instituição de ensino e sua biblioteca.

## Apresentação do Projeto

Este projeto visa automatizar processos administrativos escolares e de biblioteca, permitindo o controle eficiente de alunos, professores, cursos, turmas, além do acervo e empréstimo de livros. A API foi construída seguindo os princípios REST e utiliza tecnologias modernas do ecossistema Spring.

## Módulos e Utilizações

O sistema está dividido nos seguintes módulos principais:

- **Alunos**: Cadastro e gerenciamento de estudantes, incluindo informações pessoais e controle de status.
- **Professores**: Registro de docentes e suas respectivas especialidades.
- **Cursos**: Definição dos cursos oferecidos pela instituição.
- **Turmas**: Gestão de grupos de alunos, vinculando-os a cursos e professores, além de controle de matrículas e ativação/desativação de turmas.
- **Bibliotecários**: Gestão dos funcionários responsáveis pela biblioteca, com controle de cargo e turno.
- **Livros**: Gerenciamento do acervo literário da instituição.
- **Aluguéis**: Controle de empréstimos de livros aos alunos, com registro de data de retirada e devolução.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.4
- Spring Data JPA
- PostgreSQL Driver (Substituído H2)
- MapStruct (Mapeamento de DTOs)
- SpringDoc OpenAPI (Documentação Swagger)
- Jakarta Validation
- Spring Batch (Processamento em Lote)

## Documentação da Arquitetura

Para uma visão detalhada da estrutura dos microsserviços, diagramas UML e fluxos de comunicação, consulte o arquivo:
- [DOCUMENTACAO_ARQUITETURA.md](../DOCUMENTACAO_ARQUITETURA.md) (ou na raiz do projeto).

## Como Executar

### Via Docker (Recomendado)

1. Certifique-se de ter o Docker e Docker Compose instalados.
2. Gere o jar de cada aplicação (ex: `./mvnw clean package -DskipTests` em cada pasta).
3. Na raiz do projeto (`projeto-ms-infnet`), execute:
   ```bash
   docker-compose up --build
   ```
4. Acessos:
   - Escola API: `http://localhost:8081/swagger-ui.html`
   - Biblioteca API: `http://localhost:8082/swagger-ui.html`
   - Notificações API: `http://localhost:8083` (mapeada da 8080 interna)
   - RabbitMQ Management: `http://localhost:15672` (guest/guest)

### Via Maven Local

1. Certifique-se de ter o JDK 17 e um banco PostgreSQL rodando localmente.
2. Configure o banco em `application-dev.properties`.
3. Execute o comando `./mvnw spring-boot:run` na raiz do projeto.

## Documentação da API

A documentação detalhada dos endpoints, incluindo modelos de requisição e resposta, pode ser acessada via Swagger UI após iniciar a aplicação.

### Postman Collections

Para facilitar os testes, foram geradas coleções atualizadas do Postman (v2) e arquivos CSV de exemplo para importação em lote:

1. **Escola API v2**: Arquivo `escola-irlan-api/Escola_Irlan_API_v2.postman_collection.json`
   - **Exemplo CSV**: `escola-irlan-api/alunos.csv` (10 registros de alunos).
   - Inclui: Alunos, Professores, Cursos, Turmas, Bibliotecários e **Batch Import de Alunos/Funcionários**.
   - Variável: `{{baseUrl}}` (padrão: `http://localhost:8081`).

2. **Biblioteca API v2**: Arquivo `biblioteca-irlan-api/Biblioteca_Irlan_API_v2.postman_collection.json`
   - **Exemplo CSV**: `biblioteca-irlan-api/livros.csv` (15 registros de livros).
   - Inclui: Livros, Aluguéis, Devoluções e **Batch Import de Livros**.
   - Variável: `{{base_url}}` (padrão: `http://localhost:8082`).

Para usar o Batch Import:
1. No Postman, selecione a requisição `POST .../batch-import`.
2. Vá na aba `Body` -> `form-data`.
3. Na chave `file`, altere o tipo para `File` e selecione o arquivo CSV correspondente.

Para usar, basta importar os arquivos JSON diretamente no seu Postman.


## Padronização de Respostas de Erro

A API utiliza um tratador global de exceções para garantir que todos os erros sejam retornados em um formato JSON estruturado, facilitando a integração e o entendimento pelo cliente:

```json
{
  "status": 409,
  "titulo": "Conflito de Dados",
  "detalhe": "Não foi possível completar a operação devido a uma violação de integridade (ex: registro duplicado ou dependência ativa).",
  "timestamp": "2026-10-17T16:20:00"
}
```

### Principais Status Tratados:
- **400 (Bad Request)**: Erros de validação de campos ou argumentos inválidos.
- **404 (Not Found)**: Recurso não encontrado.
- **409 (Conflict)**: Violação de integridade de dados (ex: CPF duplicado).
- **500 (Internal Server Error)**: Erros inesperados no servidor (detalhes técnicos são mascarados por segurança).

## Perguntas da entrega de cada etapa
# Etapa 1
1. qual funcionalidade foi escolhida;<br>
Escolhi separar a funcionalidade de biblioteca da aplicação principal (escola).<br>
2. qual é sua responsabilidade;<br>
Ela será responsável pelo cadastro de alugueis, devoluções e cadastro de livros.<br>
3. por que ela poderia ser executada separadamente;<br>
Porque a biblioteca geralmente possui um fluxo de trabalho próprio e responsabilidades independentes da aplicação principal.<br>
4. quais partes da aplicação atualmente dependem dela.<br>
A parte da aplicação que lida com a gestão de empréstimos e devoluções de livros.<br>
# Etapa 2
1. Qual funcionalidade foi separada da aplicação principal?<br>
Todas as funções relacionadas ao serviço de biblioteca, que não sejam cadastro de funcionários.<br>
2. Por que ela foi escolhida?<br>
Pois uma biblioteca tem que funcionar como um serviço independente. Geralmente, as bibliotecas escolares/universitárias tem seu próprio fluxo de trabalho, funcionários/responsabilidades separados, o que tira o sentido de funcionamento em conjunto. A escola é ligada à biblioteca, mas os serviços são independentes.<br>
3. O que ficou mais complexo depois da separação?
A comunicação entre os serviços e a gestão de envios de notificações, pois agora é necessário lidar com filas de mensagens e integração entre serviços, o que adiciona complexidade ao sistema.<br>
4. O que aconteceria com a funcionalidade principal caso o novo serviço ficasse indisponível?<br>
A funcionalidade principal continuaria a funcionar, mas com limitações na parte relacionada aos empréstimos e devoluções de livros.<br>
5. A funcionalidade realmente precisa permanecer como um serviço independente ou poderia continuar dentro da aplicação?<BR>
Na minha opinião, sim! A biblioteca deve permanecer como um serviço independente, pois ela possui um fluxo de trabalho próprio e responsabilidades distintas da escola. Manter a biblioteca como um serviço separado permite maior flexibilidade, escalabilidade e manutenção do sistema, além de facilitar futuras integrações com outros sistemas ou serviços externos.<br>
# Etapa 3
1. Quais configurações da aplicação podem variar entre ambientes?<br>
As credenciais de comunicações entre os serviços, as credenciais de acesso ao banco de dados, as configurações do RabbitMQ.<br>
2. Quais dessas configurações foram externalizadas?<br>
As credenciais de acesso ao banco de dados, as configurações do RabbitMQ e as variáveis de ambiente para a URL do serviço de biblioteca.<br>
3. Por que um serviço não deve acessar diretamente o banco de outro serviço?<br>
Porque isso viola o princípio de encapsulamento e pode levar a problemas de integridade dos dados e dificuldades na manutenção do sistema.<br>
4. Qual problema o Docker resolve no projeto?<br>
O Docker resolve o problema de consistência e portabilidade do ambiente de desenvolvimento e produção, permitindo que a aplicação seja executada de forma idêntica em diferentes ambientes.<br>
5. Qual é a função do Docker Compose?<br>
O Docker Compose é utilizado para definir e executar aplicativos compostos por múltiplos contêineres, facilitando a configuração e orquestração dos serviços.<br>
6. Qual problema uma configuração centralizada procura resolver?<br>
Uma configuração centralizada procura resolver o problema de gerenciamento e manutenção das configurações em ambientes distribuídos, permitindo que as configurações sejam gerenciadas de forma unificada e simplificada.<br>
# Etapa 4
1. Qual operação foi escolhida para comunicação assíncrona?<br>
As notificações de aluguel e devolução de livros da biblioteca para o serviço de notificações.<br>
2. Por que essa operação não precisa necessariamente ser concluída durante a requisição original?<br>
Porque o envio de uma notificação (como e-mail ou log) é uma tarefa secundária e não deve atrasar a confirmação do aluguel/devolução para o usuário.<br>
3. O que acontece com a mensagem caso o consumidor esteja temporariamente indisponível?<br>
A mensagem permanece na fila do RabbitMQ e será processada assim que o serviço de notificações voltar a ficar disponível, garantindo a entrega (eventual consistency).<br>
4. Qual funcionalidade foi escolhida para processamento em lote?<br>
A importação de Alunos, Funcionários (Professores/Bibliotecários) e Livros via arquivos CSV.<br>
5. Por que essa funcionalidade é adequada para Batch?<br>
Porque envolve o processamento de grandes volumes de dados que não precisam de resposta imediata em tempo real, permitindo otimizar o uso de recursos e transações de banco de dados.<br>
6. Em quais situações da aplicação seria mais adequado utilizar REST, mensageria ou Batch?<br>
   - **REST**: Para operações síncronas que exigem resposta imediata (ex: consultar um cadastro, validar login).
   - **Mensageria**: Para tarefas assíncronas e desacoplamento (ex: disparar alertas, integrações entre serviços onde um não precisa esperar o outro).
   - **Batch**: Para carga massiva de dados ou processamentos periódicos de grandes volumes (ex: relatórios mensais, importação de cadastros).<br>