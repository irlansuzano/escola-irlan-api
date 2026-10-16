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

## Como Executar

1. Certifique-se de ter o JDK 17 instalado.
2. Clone o repositório.
3. Execute o comando `./mvnw spring-boot:run` na raiz do projeto.
4. Acesse a documentação Swagger em: `http://localhost:8081/swagger-ui.html`

## Documentação da API

A documentação detalhada dos endpoints, incluindo modelos de requisição e resposta, pode ser acessada via Swagger UI após iniciar a aplicação.


## Perguntas da entrega
1. Qual funcionalidade foi separada da aplicação principal?<br>
Todas as funções relacionadas ao serviço de biblioteca, que não sejam cadastro de funcionários.<br>
2. Por que ela foi escolhida?<br>
Pois uma biblioteca tem que funcionar como um serviço independente. Geralmente, as bibliotecas escolares/universitárias tem seu próprio fluxo de trabalho, funcionários/responsabilidades separados, o que tira o sentido de funcionamento em conjunto. A escola é ligada à biblioteca, mas os serviços são independentes.<br>
3. O que ficou mais complexo depois da separação?
A comunicação entre os serviços e a gestão de envios de notificações, pois agora é necessário lidar com filas de mensagens e integração entre serviços, o que adiciona complexidade ao sistema.<br>
4. O que aconteceria com a funcionalidade principal caso o novo serviço ficasse indisponível?<br>
A funcionalidade principal continuaria a funcionar, mas com limitações na parte relacionada aos empréstimos e devoluções de livros.<br>
5. A funcionalidade realmente precisa permanecer como um serviço independente ou poderia continuar dentro da aplicação?<BR>
Na minha opinião, sim! A biblioteca deve permanecer como um serviço independente, pois ela possui um fluxo de trabalho próprio e responsabilidades distintas da escola. Manter a biblioteca como um serviço separado permite maior flexibilidade, escalabilidade e manutenção do sistema, além de facilitar futuras integrações com outros sistemas ou serviços externos.
