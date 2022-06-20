# transactions-control

## 1. Introdução
### Esta aplicação tem por objetivo controlar uma rotina de transações.
## 2. Executando a aplicação
### Requisitos:
### * Docker - Para download, acessar: https://www.docker.com/get-started/
### * Maven - Para download e passos para configuração, acessar: https://maven.apache.org/
#### Seguir os passos abaixo:
#### 1) Faça a cópia dos arquivos para sua máquina.
#### 2) Abra o prompt de comando, e navegue até a pasta raiz da aplicação (transactions-control).
#### 3) Execute os comandos:
#### * mvn clean install
#### * docker-compose up --build --force-recreate
#### 4) A aplicação estará rodando na porta 8080 e o banco Postgres na porta 5432
#### 5) Acessar a documentação da api em: http://localhost:8080/swagger-ui.html#/
