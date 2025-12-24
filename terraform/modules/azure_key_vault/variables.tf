# Commn
  variable "subscription_id" {
    type        = string
    description = "Azure Subscription ID"
  }
  
# remote states
  variable "foodcore-backend-resource-group" {
    type        = string
    description = "Nome do resource group onde o backend está armazenado"
  }

  variable "foodcore-backend-storage-account" {
    type        = string
    description = "Nome da conta de armazenamento onde o backend está armazenado"
  }

  variable "foodcore-backend-container" {
    type        = string
    description = "Nome do contêiner onde o backend está armazenado"
  }

  variable "foodcore-backend-infra-key" {
    type        = string
    description = "Chave do arquivo tfstate do foodcore-infra"
  }

# AKV
  variable "akv_id" {
    type        = string
    description = "ID do Azure Key Vault"
  }

variable "mercado_pago_token" {
  type = string
  description = "Token de autenticação da API do MercadoPago"
  sensitive = true
}

variable "mercado_pago_user_id" {
  type = string
  description =  "ID do usuário do MercadoPago"
  sensitive = true
}

variable "mercado_pago_pos_id" {
  type = string
  description = "ID do ponto de venda (POS) do MercadoPago"
  sensitive = true
}