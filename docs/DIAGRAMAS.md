<h2 id="diagramas">📊 Diagramas</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Modelo de Domínio

```mermaid
classDiagram
    class Order {
        -Long id
        -User user
        -String orderNumber
        -OrderStatus status
        -BigDecimal totalAmount
        -List~OrderItem~ items
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +addItem(OrderItem)
        +removeItem(OrderItem)
        +calculateTotalAmount()
        +updateStatus(OrderStatus)
    }


    class OrderItem {
        -Long id
        -Product product
        -Integer quantity
        -BigDecimal unitPrice
        -BigDecimal subtotal
        -String observations
        +calculateSubtotal()
    }

    class Catalog {
        -Long id
        -String name
    }

    class Category {
        -Long id
        -Catalog catalog
        -String name
        -String description
        -String imageUrl
        -Integer displayOrder
        -Boolean active
    }

    class Product {
        -Long id
        -Category category
        -String name
        -String description
        -BigDecimal price
        -String imageUrl
        -Integer displayOrder
        -Boolean active
    }

    class User {
        -Long id
        -String name
        -String username
        -String email
        -String password
        -String document
        -Boolean active
        -Boolean guest
        -Role role
        -LocalDateTime lastLogin
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +login(user)
    }

    class Role{
        <<enumeration>>
        ADMIN
        USER
        GUEST
    }

    class OrderStatus {
        <<enumeration>>
        RECEIVED
        PREPARING
        READY
        COMPLETED
        CANCELLED
    }

    Order "1" *-- "many" OrderItem
    Order "many" -- "1" User
    OrderItem "many" -- "1" Product
    Catalog "many" -- "1" Category
    Category "many" -- "1" Product
    Product -- Category
    Order -- OrderStatus
    User -- Role
```

### DER (Diagrama Entidade-Relacionamento)

```mermaid
erDiagram
    USERS ||--o{ ORDERS : places
    USERS ||--o{ PAYMENTS : makes
    ROLES ||--o{ USERS : places
    ORDERS ||--o{ ORDER_ITEMS : contains
    PRODUCTS ||--o{ ORDER_ITEMS : includes
    PRODUCTS ||--|| STOCK : stored_in
    CATALOG ||--o{ CATEGORIES : has
    CATEGORIES ||--o{ PRODUCTS : categorizes
    ORDERS ||--o{ PAYMENTS : has
    USERS {
        int id PK "ID único do usuário"
        string name "Nome do usuário"
        string username "Nome de usuário"
        string email "e-mail do usuário"
        string password "Hash da senha do usuário"
        string document "Documento do usuário"
        boolean active "Indica se o usuário está ativo"
        boolean guest "Indica se o usuário é convidado"
        int role_id "ID da role do usuário"
        timestamp last_login "Data do último login"
        timestamp created_at "Data de criação do registro"
        timestamp updated_at "Data da última atualização do registro"
    }

    ROLES {
        int id PK "ID único da Role"
        string name "Nome único da role"
        string description "Descrição das permissões do role"
        timestamp created_at "Data de criação do registro"
        timestamp updated_at "Data da última atualização do registro"
    }

    ORDERS {
        int id PK "ID único da order"
        int user_id FK "ID do usuário que criou o pedido"
        varchar order_number "hash aleatoria para identificar o pedido"
        varchar status "status do pedido"
        decimal amount "preço do pedido"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    ORDER_ITEMS {
        int id PK "ID único da order_item"
        int order_id FK "ID do pedido"
        int product_id FK "ID do produto"
        string name "nome do item"
        int quantity "quantidade do item"
        decimal unit_price "preço unitário"
        text observations "oberservações do usuário"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    CATALOG{
        int id PK "ID único da catálogo"
        string name "Nome do catálogo"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    CATEGORIES{
        int id PK "ID único da categoria"
        int catalog_id FK
        string name "Nome da categoria"
        string description "Descrição da categoria"
        string image_url "URL da imagem da categoria"
        int display_order "Ordem de exibição da categoria"
        boolean active "Indica se a categoria está ativa ou não"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    PRODUCTS {
        bigint id PK "ID único do produto"
        bigint category_id FK "ID da categoria do produto"
        varchar name "nome do produto"
        varchar description "descrição do produto"
        decimal price "preço do produto"
        varchar image_url "URL da foto do produto"
        int display_order "ordem de exibição do produto"
        boolean active "status do produto 'ativo ou inativo'"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    STOCK {
        bigint id PK "ID único do stock"
        bigint product_id FK "ID do produto"
        int quantity "quantidade disponivel"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    PAYMENTS {
        int id PK "ID único do pagamento"
        int user_id FK "ID do usuário que criou o pagamento"
        int order_id FK "ID do pedido pago"
        varchar payment_type "tipo de pagamento"
        timestamp expires_in "data de expiração do pagamento"
        varchar status "status do pagamento 'pago, cancelado, pendente'"
        timestamp paid_at "data do pagamento"
        varchar tid "id do pagamento na adquirente"
        decimal amount "valor do pagamento"
        varchar qr_code "código do qr_code do pagamento"
        text observations "Observações do usuário para o pagamento"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }
```

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[CustomerIdentified] --> E2[OrderCreated]
    E2 --> E3[ItemAdded]
    E3 --> E4[OrderConfirmed]
    E4 --> E5[PaymentRequested]
    E5 --> E6[QRCodeGenerated]
    E6 --> E7[PaymentReceived]
    E7 --> E8[OrderReceived]

    %% Comandos
    C1[IdentifyCustomer] --> E1
    C2[CreateOrder] --> E2
    C3[AddItem] --> E3
    C4[ConfirmOrder] --> E4
    C5[RequestPayment] --> E5
    C6[GenerateQRCode] --> E6
    C7[ConfirmPayment] --> E7
    C8[ReceiveOrder] --> E8

    %% Atores
    A1[Customer] --> C1
    A1 --> C2
    A1 --> C3
    A1 --> C4
    A1 --> C5
    A2[PaymentSystem] --> C7
    A3[Attendant] --> C8
```

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[OrderReceived] --> E2[PreparationStarted]
    E2 --> E3[OrderReady]
    E3 --> E4[CustomerNotified]
    E4 --> E5[OrderDelivered]
    E5 --> E6[OrderFinished]

    %% Comandos
    C1[StartPreparation] --> E2
    C2[MarkAsReady] --> E3
    C3[NotifyCustomer] --> E4
    C4[DeliverOrder] --> E5
    C5[FinishOrder] --> E6

    %% Atores
    A1[Cook] --> C1
    A1 --> C2
    A2[System] --> C3
    A3[Attendant] --> C4
    A3 --> C5

```

### Fluxo de Realização do Pedido e Pagamento (Event Storming)

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[CustomerIdentified] --> E2[OrderCreated]
    E2 --> E3[ItemAdded]
    E3 --> E4[OrderConfirmed]
    E4 --> E5[PaymentRequested]
    E5 --> E6[QRCodeGenerated]
    E6 --> E7[PaymentReceived]
    E7 --> E8[OrderReceived]

    %% Comandos
    C1[IdentifyCustomer] --> E1
    C2[CreateOrder] --> E2
    C3[AddItem] --> E3
    C4[ConfirmOrder] --> E4
    C5[RequestPayment] --> E5
    C6[GenerateQRCode] --> E6
    C7[ConfirmPayment] --> E7
    C8[ReceiveOrder] --> E8

    %% Atores
    A1[Customer] --> C1
    A1 --> C2
    A1 --> C3
    A1 --> C4
    A1 --> C5
    A2[PaymentSystem] --> C7
    A3[Attendant] --> C8
```

### Fluxo de Preparação e Entrega do Pedido (Event Storming)

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[OrderReceived] --> E2[PreparationStarted]
    E2 --> E3[OrderReady]
    E3 --> E4[CustomerNotified]
    E4 --> E5[OrderDelivered]
    E5 --> E6[OrderFinished]

    %% Comandos
    C1[StartPreparation] --> E2
    C2[MarkAsReady] --> E3
    C3[NotifyCustomer] --> E4
    C4[DeliverOrder] --> E5
    C5[FinishOrder] --> E6

    %% Atores
    A1[Cook] --> C1
    A1 --> C2
    A2[System] --> C3
    A3[Attendant] --> C4
    A3 --> C5
```

</details>