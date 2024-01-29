# language: pt
Funcionalidade: API - Cliente - Identificar Cliente

  Cenário: Identificar Cliente
    Dado que eu tenha um cliente cadastrado
    Quando eu consultar o cliente pelo CPF
    Então o cliente é retornado com sucesso