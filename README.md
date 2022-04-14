# API Godzilla
<br>

# Descrição
<p align="justify"> 
  Esse projeto apresenta o desenvolvimento de uma API para uma empresa de locadora de filmes chamada Godzilla
</p>

# Tecnologias utilizadas
![](https://img.shields.io/static/v1?label=&message=Spring%20Boot&color=6DB33F)
![](https://img.shields.io/static/v1?label=&message=Spring%20Security&color=ffc300)
![](https://img.shields.io/static/v1?label=&message=H2%20Database&color=1021FF)
<br>
<br>

## Como utilizar
<p align="justify"> 
  Para ter acesso aos Filmes é preciso se cadastrar. 
  atravez do EndPoint(POST) http://localhost:8080/usuarios/cadastrar você pode se cadastrar passando por exemplo o seguinte conteudo no corpo da requisição:
</p>
  
```sh
{
	"nome":"Nome Sobrenome",
	"email":"nome.sobrenome@email.com",
	"senha": "minhaSenha123"
}
```
<p align="justify"> 
  e como resultado a seguinte resposta será recebida
</p> 

```sh
{
	"id": 4,
	"nome": "Nome Sobrenome",
	"email": "nome.sobrenome@email.com"
}
```
<br>

## Login
<p align="justify"> 
  Agora para acessar qualquer conteudo da aplicação é preciso estar logado. E para logar acesse o EndPoint(POST) http://localhost:8080/login.
  Atravez dele você receberá um token de autenticação para ser usado no header de todas as outras requisições da API,
  com o titulo do header sendo "Authorization" e o valor do header sendo "Bearer {seuToken}"
</p>

<br>
### Exemplo de token:

```sh
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnYWJyaWVsLmNhcmRvc29AZ21haWwuY29tIiwiZXhwIjoxNjQ5ODc2MzA4fQ.El_feXEPsS_ngnmEHSWB8l86XyKp_68ISmK5Lx5gQKCppCFMsEKCXXY7u2OEkzxkulAVImjSVY595RXZt9lemQ
```
<br>

<p align="justify"> 
  🟡 Nota: O token expira em 10 minútos, para realizar requisições é preciso logar novamente e utilizar o novo token recebido no login.
</p>

## Visualizar Catalogo

<p align="justify"> 
  É possivel visualizar o catálogo de filmes atravez do EndPoint(GET)  http://localhost:8080/filmes, ou buscando um filme pelo seu titulo
  no EndPoint(GET) http://localhost:8080/filmes/locadora/{nomeDoFilme}, onde {nomeDoFilme} é o nome do filme que se deseja pesquisar.
  e como resultado obtemos uma lista de filmes como esssa:
</p>

```sh
 [
  {
    "id": 1,
    "titulo": "Godzilla 200",
    "diretor": "Takao Okawara",
    "estoque": 1
  },
  {
    "id": 2,
    "titulo": "Godzilla Contra Ataca",
    "diretor": "Motoyoshi Oda",
    "estoque": 5
  }
]
```
<br>

## Alugar um filme

<p align="justify"> 
  Agora você já possui o que é preciso para alugar um filme. Para aluga-lo basta acessar o EndPoint(POST) http://localhost:8080/alugadores/alugar/{idFilme}, 
  onde {idFilme} é o id do filme que deseja ser alugado.
</p> 

<p align="justify" style="color: yellow" > 
  🟡 Nota: só é permitido alugar um filme por usúario.
</p> 
<br>

## Devolver um filme

<p align="justify"> 
  Para devolver um filme você deve acessar o EndPoint(DELETE) http://localhost:8080/filmes/devolver/, 
  Como resultado, o usuário devolve o último filme que ele alugou.
</p>

