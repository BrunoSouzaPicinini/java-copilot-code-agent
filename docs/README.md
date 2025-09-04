# Sistema de Gestão Escolar - Mergington High School

Sistema de gestão de atividades extracurriculares desenvolvido com Spring Boot e arquitetura limpa (Clean Architecture).

## 📋 Visão Geral

O **School Management System** é uma aplicação web que permite o gerenciamento de atividades extracurriculares da Mergington High School. O sistema possibilita que professores administrem atividades e que estudantes se inscrevam nelas através de uma interface web intuitiva.

## 🏗️ Arquitetura

### Princípios Arquiteturais

- **Clean Architecture**: Separação clara entre camadas de domínio, aplicação, infraestrutura e apresentação
- **Domain-Driven Design (DDD)**: Modelagem focada no domínio escolar
- **SOLID Principles**: Código bem estruturado e extensível
- **Hexagonal Architecture**: Isolamento das regras de negócio

### Estrutura de Camadas

```text
src/main/java/com/mergingtonhigh/schoolmanagement/
├── domain/                    # 🎯 Camada de Domínio
│   ├── entities/             # Entidades principais
│   │   ├── Activity.java     # Atividade extracurricular
│   │   └── Teacher.java      # Professor/Administrador
│   ├── repositories/         # Interfaces de repositório
│   │   ├── ActivityRepository.java
│   │   └── TeacherRepository.java
│   └── valueobjects/         # Objetos de valor
│       ├── ActivityType.java       # Enum para tipos/categorias de atividades
│       ├── Email.java              # Validação de email
│       └── ScheduleDetails.java    # Detalhes de horário
├── application/              # 🔧 Camada de Aplicação
│   ├── dtos/                 # Data Transfer Objects
│   │   ├── ActivityDTO.java
│   │   ├── ActivityTypeDTO.java     # DTO para tipos de atividade
│   │   ├── LoginRequestDTO.java     # DTO para requisições de login
│   │   ├── StudentRegistrationDTO.java
│   │   └── TeacherDTO.java
│   └── usecases/             # Casos de uso
│       ├── ActivityUseCase.java
│       ├── AuthenticationUseCase.java    # Autenticação de professores
│       └── StudentRegistrationUseCase.java
├── infrastructure/           # 🏭 Camada de Infraestrutura
│   ├── config/               # Configurações
│   ├── migrations/           # Migrações do banco
│   │   └── V001_InitialDatabaseSetup.java
│   └── persistence/          # Implementações de repositório
│       ├── ActivityRepositoryImpl.java
│       ├── MongoActivityRepository.java
│       ├── MongoTeacherRepository.java
│       └── TeacherRepositoryImpl.java
└── presentation/             # 🎨 Camada de Apresentação
    ├── controllers/          # Controllers REST
    │   ├── ActivityController.java    # Gestão de atividades
    │   ├── AuthController.java        # Autenticação de professores
    │   └── StaticController.java      # Servir conteúdo estático
    └── mappers/              # Mapeadores DTO ↔ Entity
        ├── ActivityMapper.java
        └── TeacherMapper.java
```

## 🚀 Tecnologias Utilizadas

### Backend

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.4** - Framework principal
- **Spring Data MongoDB** - Integração com MongoDB
- **Spring Security** - Autenticação e autorização
- **Spring Web** - APIs REST
- **Spring Validation** - Validação de dados
- **Mongock 5.5.1** - Migrações do banco de dados
- **BouncyCastle** - Criptografia para senhas

### Frontend

- **HTML5/CSS3/JavaScript** - Interface web
- **Vanilla JavaScript** - Interatividade do frontend

### Banco de Dados

- **MongoDB** - Banco de dados NoSQL

### Ferramentas de Desenvolvimento

- **Maven** - Gerenciamento de dependências
- **JUnit 5** - Testes unitários
- **Mockito** - Mocks para testes
- **Testcontainers** - Testes de integração
- **Jacoco** - Cobertura de testes

## 📦 Funcionalidades Principais

### 🎓 Gestão de Atividades

- **Listagem de atividades** com filtros por:
  - Dia da semana
  - Horário (manhã, tarde, fim de semana)
  - Categoria automática (esportes, artes, acadêmico, comunidade, tecnologia)
- **Categorização automática** baseada em palavras-chave do nome e descrição
- **Detalhes de atividades**:
  - Nome e descrição
  - Horários e dias da semana
  - Categoria com cores visuais
  - Capacidade máxima
  - Lista de participantes

### 👨‍🏫 Sistema de Autenticação

- **Endpoints dedicados** para autenticação (`/auth/login`, `/auth/check-session`)
- **Login de professores** com username/senha via formulário
- **Verificação de sessão** para validar usuários autenticados
- **Controle de acesso** baseado em roles (TEACHER/ADMIN)
- **Autenticação requerida** para inscrições e cancelamentos

### 📝 Gestão de Inscrições

- **Inscrição de estudantes** em atividades
- **Cancelamento de inscrições**
- **Validações**:
  - Capacidade máxima
  - Duplicatas
  - Autenticação do professor

### 🎨 Interface Web

- **Design responsivo** e intuitivo
- **Filtros dinâmicos** para busca de atividades
- **Modais** para login e inscrições
- **Feedback visual** para ações do usuário

## 🔧 Configuração e Execução

### Pré-requisitos

- Java 21
- Maven 3.8+
- MongoDB 4.4+

### Variáveis de Ambiente

Crie um arquivo `.env` baseado no `.env.example`

### Executando o Projeto

1. **Iniciar MongoDB**:

   ```bash
   # Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   
   # Ou MongoDB local
   mongod
   ```

2. **Compilar e executar**:

   ```bash
   # Compilar o projeto
   mvn clean compile
   
   # Executar os testes
   mvn test
   
   # Iniciar a aplicação
   mvn spring-boot:run
   ```

3. **Acessar a aplicação**:
   - Frontend: <http://localhost:8080>
   - API REST: <http://localhost:8080/activities>

### Tasks Maven Disponíveis

- `mvn clean install` - Build completo
- `mvn test` - Executar testes
- `mvn spring-boot:run` - Iniciar aplicação
- `mvn package -DskipTests` - Gerar JAR

## 🌐 API REST

### Endpoints Principais

#### 🏠 Página Principal

```http
GET /                           # Página principal (redirecionamento para static/index.html)
```

#### 🎓 Atividades

```http
GET /activities                 # Listar todas as atividades
GET /activities?day=Monday&start_time=15:00&end_time=17:00  # Filtros opcionais
GET /activities/days           # Listar dias disponíveis com atividades
```

#### 🔐 Autenticação

```http
POST /auth/login               # Login de professor
Content-Type: application/x-www-form-urlencoded
Body: username=professor&password=senha

GET /auth/check-session?username=professor  # Verificar sessão ativa
```

#### 📝 Inscrições

```http
POST /activities/{activityName}/signup      # Inscrever estudante
Content-Type: application/x-www-form-urlencoded
Body: email=student@mergington.edu&teacher_username=professor

POST /activities/{activityName}/unregister  # Cancelar inscrição
Content-Type: application/x-www-form-urlencoded
Body: email=student@mergington.edu&teacher_username=professor
```

### Respostas da API

#### Sucesso
- **200 OK**: Operação realizada com sucesso
- **Formato**: JSON com dados solicitados

#### Erros
- **400 Bad Request**: Dados inválidos ou regras de negócio violadas
- **401 Unauthorized**: Credenciais inválidas ou autenticação requerida
- **404 Not Found**: Recurso não encontrado
- **Formato de erro**: `{"detail": "Mensagem descritiva do erro"}`

## 🎨 Sistema de Categorização de Atividades

### Categorias Automáticas

O sistema categoriza automaticamente as atividades baseado em palavras-chave do nome e descrição:

#### 🏃‍♂️ Esportes (`SPORTS`)
- **Palavras-chave**: futebol, basquete, esporte, fitness, soccer, basketball, sport
- **Descrição**: equipe, time, jogo, atlético, team, game, athletic
- **Cores**: Fundo `#e8f5e9`, Texto `#2e7d32`

#### 🎭 Artes (`ARTS`)
- **Palavras-chave**: arte, música, teatro, drama, art, music, theater
- **Descrição**: criativo, pintura, creative, paint
- **Cores**: Fundo `#f3e5f5`, Texto `#7b1fa2`

#### 📚 Acadêmico (`ACADEMIC`)
- **Palavras-chave**: ciência, matemática, acadêmico, estudo, olimpíada, science, math, academic, study, olympiad
- **Descrição**: aprendizado, educação, competição, learning, education, competition
- **Cores**: Fundo `#e3f2fd`, Texto `#1565c0`

#### 🤝 Comunidade (`COMMUNITY`)
- **Palavras-chave**: voluntário, comunidade, volunteer, community
- **Descrição**: serviço, voluntário, service, volunteer
- **Cores**: Fundo `#fff3e0`, Texto `#e65100`

#### 💻 Tecnologia (`TECHNOLOGY`)
- **Palavras-chave**: computador, programação, tecnologia, robótica, computer, coding, tech, robotics
- **Descrição**: programação, tecnologia, digital, robô, programming, technology, robot
- **Cores**: Fundo `#e8eaf6`, Texto `#3949ab`

### Implementação

A categorização é feita automaticamente pelo método `ActivityType.determineFromContent()` que:
1. Analisa o nome e descrição da atividade
2. Busca por palavras-chave específicas
3. Retorna a categoria mais apropriada
4. Usa "Acadêmico" como categoria padrão

## 🧪 Testes

### Estrutura de Testes

```text
src/test/java/
├── application/usecases/     # Testes de casos de uso
├── domain/entities/          # Testes de entidades
└── integration/             # Testes de integração
```

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=ActivityTest
mvn test -Dtest=StudentRegistrationUseCaseTest

# Com cobertura
mvn jacoco:report
```

## 📊 Dados Iniciais

O sistema utiliza **Mongock** para realizar migrações automáticas do banco de dados, incluindo:

### Professores Padrão

- **admin** - Administrador principal
- **teacher.rodriguez** - Professor de artes
- **teacher.chen** - Professor de xadrez

### Atividades Exemplo (por categoria)

#### 🏃‍♂️ Esportes
- **Fitness Matinal** - Segundas, quartas e sextas, 06:30-07:45
- **Time de Futebol** - Terças e quintas, 15:30-17:30
- **Time de Basquete** - Quartas e sextas, 15:15-17:00

#### 🎭 Artes
- **Clube de Arte** - Quintas, 15:15-17:00
- **Clube de Teatro** - Segundas e quartas, 15:30-17:30
- **Manga Maniacs** - Terças, 19:00-20:00

#### 📚 Acadêmico
- **Clube de Xadrez** - Segundas e sextas, 15:15-16:45
- **Clube de Matemática** - Terças, 07:15-08:00
- **Equipe de Debates** - Sextas, 15:30-17:30
- **Olimpíada de Ciências** - Sábados, 13:00-16:00
- **Torneio de Xadrez** - Domingos, 14:00-17:00

#### 💻 Tecnologia
- **Aula de Programação** - Terças e quintas, 07:00-08:00
- **Oficina de Robótica** - Sábados, 10:00-14:00

#### 🤝 Comunidade
- **Serviço Comunitário** - Sábados, 09:00-12:00

Todas as atividades incluem estudantes pré-inscritos para demonstração.

## 🔒 Segurança

- **Autenticação HTTP Basic** para endpoints administrativos
- **Criptografia Argon2** para senhas
- **Validação de dados** em todas as camadas
- **CORS** configurado para desenvolvimento

## 📈 Monitoramento

- **Spring Actuator** - Métricas da aplicação
- **Logs estruturados** - Nível DEBUG para desenvolvimento
- **Health checks** - Status da aplicação e banco

## 🚀 Deploy

### Perfis de Ambiente

- **dev** - Ambiente de desenvolvimento
