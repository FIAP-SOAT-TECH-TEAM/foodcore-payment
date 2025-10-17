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

  variable "foodcore-backend-db-key" {
    type        = string
    description = "Chave do arquivo tfstate do foodcore-db"
  }

variable "release_name" {
  type        = string
  description = "Nome do release do Helm."
}

variable "repository_url" {
  type        = string
  description = "URL do repositório Helm onde o chart está hospedado."
}

variable "chart_name" {
  type        = string
  description = "Nome do chart Helm a ser instalado."
}

variable "chart_version" {
  type        = string
  description = "Versão do chart Helm."
}

variable "release_namespace" {
  type        = string
  description = "Namespace Kubernetes onde o release será instalado."
}

variable "docker_image_uri" {
  type        = string
  description = "URI da imagem Docker a ser utilizada."
}

variable "docker_image_tag" {
  type        = string
  description = "Tag da imagem Docker a ser utilizada."
}

variable "jwt_secret" {
  type        = string
  description = "Segredo para assinatura de tokens JWT."
}

variable "jwt_expires_time" {
  type        = number
  description = "Tempo de expiração do token JWT em minutos."
}

variable "mercadopago_base_url" {
  type        = string
  description = "URL base da API do MercadoPago."
}

variable "mercadopago_token" {
  type        = string
  description = "Token da API do MercadoPago."
}

variable "mercadopago_user_id" {
  type        = string
  description = "User ID da API do MercadoPago."
}

variable "mercadopago_pos_id" {
  type        = string
  description = "POS ID da API do MercadoPago."
}

variable "api_ingress_path" {
  type        = string
  description = "Caminho de ingress da API."
}

variable "ingress_repository_url" {
  type        = string
  description = "URL do repositório Helm onde o chart do ingress-nginx está hospedado."
  default     = "https://kubernetes.github.io/ingress-nginx"
}

variable "ingress_chart_name" {
  type        = string
  description = "Nome do chart Helm do ingress-nginx a ser instalado."
  default     = "ingress-nginx"
}

variable "ingress_chart_version" {
  type        = string
  description = "Versão do chart Helm do ingress-nginx."
  default     = "4.13.2"
}

variable "ingress_int_release_name" {
  type        = string
  description = "Nome do release do Helm do ingress-nginx interno."
  default     = "ingress-nginx-int"
}

variable "ingress_ext_release_name" {
  type        = string
  description = "Nome do release do Helm do ingress-nginx externo."
  default     = "ingress-nginx-ext"
}

variable "ingress_release_namespace" {
  type        = string
  description = "Namespace Kubernetes onde o release do ingress-nginx será instalado."
  default     = "default"
}

variable "ingress_ext_class_name" {
  type        = string
  description = "Nome da classe do ingress-nginx externo."
  default     = "nginx-ext"
}

variable "ingress_int_class_name" {
  type        = string
  description = "Nome da classe do ingress-nginx interno."
  default     = "nginx-int"
}