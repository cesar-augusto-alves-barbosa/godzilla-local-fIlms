# API Godzilla
API para uma empresa de locadora
<br>

# Descrição
<p align="justify"> 
  Esse projeto apresenta o desenvolvimento de uma API para uma empresa de locadora de filmes chamada Godzilla
  utilizando Spring Boot
</p>

## Como utilizar
<p align="justify"> 
  Para ter acesso aos Filmes é preciso criar um usuário. 
  atravez do EndPoint(POST) http://localhost:8080/usuarios/usuario vocÊ pode se cadastrar passando o seguinte conteudo no corpo da requisição
</p>
  
```sh
 {
   "name": "Cesar Augusto",
   "email": "cliente@cliente.com",
   "senha": "1234567@"
 }
```
<p align="justify"> 
  e como resultado a seguinte resposta será recebida
</p> 

```sh
 {
  "id": 1,
  "name": "Cesar Augusto",
  "email": "cliente@cliente.com",
  "filmeAlugado": null,
  "token": "04901d6e-33f1-4e07-ae38-aff4e30c24e6"
}
```
<p align="justify" style="color: yellow" > 
    Nota: O token recebido na resposta deve ser utilizado em todos os EndPoints da API. Ele é unico para cada usuário.
</p> 
<br>

## Visualizar Catalogo

<p align="justify"> 
  É possivel visualizar o catálogo de filmes atravez do EndPoint(GET)  http://localhost:8080/filmes/{token}, onde {token} é o seu token pessoal.
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
  Agora você já possui o que é preciso para alugar um filme. Para aluga-lo basta acessar o EndPoint(POST) http://localhost:8080/godzilla/{idFilme}/{token}, 
  onde {idFilme} é o id do filme que deseja ser alugado e {token} é o seu token pessoal.
</p> 

<p align="justify" style="color: yellow" > 
  Nota: só é permitido alugar um filme por usúario.
</p> 
<br>

## Devolver um filme

<p align="justify"> 
  Para devolver um filme você deve acessar o EndPoint(DELETE) http://localhost:8080/usuarios/devolver/{token}, onde {token} é o seu token pessoal.
</p>

## Deletar toke

<p align="justify"> 
  É possivel deletar um token atravez do EndPoint(DELETE) http://localhost:8080/usuarios/delete/, onde {token} é o seu token pessoal.
  Dai o usuário não podera ter acesso a aplicação sem um token, precisando fazer um login novamente para acessar
</p>

## Login

<p align="justify"> 
  Para fazer o login é preciso acessar o EndPoint(POST) http://localhost:8080/usuarios/login, que retornara um novo token para a autenticação:
</p>


```sh
{
  "id": 3,
  "nome": "Almofadinhas",
  "email": "cliente@cliente.com",
  "filmeAlugado": null,
  "token": "403da746-056a-474d-b2a2-106b79e7acc4"
}

```

