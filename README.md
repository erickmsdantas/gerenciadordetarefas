## Gerenciador de tarefas

O Gerenciador de tarefas consiste em uma aplicação Java Web utilizando JavaServer Faces (JSF) (item a), utiliza o banco de dados PostgreSQL (item b) e o framework Hibernate, implementação JPA (item c).
O projeto foi publicado na plataforma Amazon EC2 (item e).

### Funcionalidades

O Gerenciador de Tarefas permite que os usuários realizem as seguintes funções:

* Cadastrar Tarefa
* Alterar Tarefa
* Excluir Tarefa
* Listar Tarefas
    * Buscar tarefas por número
    * Buscar tarefas por Titulo ou descriçao
    * Buscar tarefas por responsável
    * Buscar tarefas por situação
* Cadastrar Responsável
* Alterar Responsável
* Excluir Responsável
**Exclui apenas responsáveis que não têm tarefas associadas
* Listar Responsável

### Acesso

Para acessar o Gerenciador de Tarefas, utilize o seguinte link:
```
http://ec2-44-204-179-123.compute-1.amazonaws.com:8080/gerenciadordetarefas-1.0-SNAPSHOT/
```

## Instruções para execução

1. Instale o JDK (Java Development Kit)
2. Instale o Eclipse
2. Instale PostgreSQL 14
3. Baixe o Wildfly-24.0.0.Final

4. Clone este repositório utilizando o comando abaixo:

```
git clone https://github.com/erickmsdantas/gerenciadordetarefas.git
```

5. Crie um banco de dados no PostgreSQL e execute o script SQL abaixo.
6. No Eclipse, vá em "File" > "Import" e selecione "Existing Maven Projects". Selecione o diretório do repositório clonado acima.
7. Em "src/main/resources/META-INF/persistence.xml", as propriedades "javax.persistence.jdbc.url", "javax.persistence.jdbc.user" e "javax.persistence.jdbc.password" correspondem às configurações do seu banco de dados.
8. Em "File" > "New" > "Other", crie um novo servidor WildFly 24+. É possível que seja necessário instalar o JBoss Tools no Eclipse Marketplace..
9. Para executar a aplicação, clique com o botão direito no projeto no "Project Explorer" e selecione "Run As" > "Run on Server"..
10. Acesse a aplicação no seguinte endereço: http://localhost:8080/gerenciadordetarefas-1.0-SNAPSHOT/

## Script Banco de Dados

Utilize o script abaixo para criar as tabelas necessárias no banco de dados:

```

CREATE TABLE responsavel (
	id serial4 NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT responsavel_pkey PRIMARY KEY (id)
);


CREATE TABLE tarefa (
	id serial4 NOT NULL,
	deadline date NULL,
	descricao varchar(255) NULL,
	prioridade varchar(255) NOT NULL,
	situacao varchar(255) NOT NULL,
	titulo varchar(255) NOT NULL,
	responsavel_id int4 NOT NULL,
	CONSTRAINT tarefa_pkey PRIMARY KEY (id)
);


ALTER TABLE tarefa ADD CONSTRAINT fk3pmini36lb1ana7agpihl3l5v FOREIGN KEY (responsavel_id) REFERENCES responsavel(id);
```