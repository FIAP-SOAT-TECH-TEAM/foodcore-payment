# language: pt

Funcionalidade: Buscar QrCode de pagamento por ID do pedido
  Como CLIENTE que realizou um pedido
  Quero buscar o QrCode de pagamento associado ao meu pedido
  Para que eu possa realizar o pagamento

  Contexto:
    Dado que existam pagamentos

  Cenario: Recuperar QrCode de pagamento com sucesso
    Dado que o ID do cliente é "d3689f8c-1970-4c9c-a4b2-9a823a37i5aa"
    Quando o cliente buscar o QrCode de pagamento pelo ID do pedido "1"
    Então o sistema deve retornar o status 200
    E o corpo da resposta deve conter o campo "orderId" igual a "1"
    E o corpo da resposta deve conter o campo "qrCode"
    E o campo "expiresIn" deve estar presente

  Cenario: Recuperar QrCode de pagamento sem sucesso
    Dado que o ID do cliente é "j36k9l8c-2s70-4c9c-a4b2-0as23a37i5aa"
    Quando o cliente buscar o QrCode de pagamento pelo ID do pedido "3"
    Então o sistema deve retornar o status 403
