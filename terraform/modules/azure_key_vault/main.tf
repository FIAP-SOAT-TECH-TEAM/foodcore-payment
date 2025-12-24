resource "azurerm_key_vault_secret" "mp_token" {
  name         = "mp-token"
  value        = var.mercado_pago_token
  key_vault_id = var.akv_id

  tags = {
    microservice = "payment"
    resource  = "mercadopago"
  }

}

resource "azurerm_key_vault_secret" "mp_user_id" {
  name         = "mp-user-id"
  value        = var.mercado_pago_user_id
  key_vault_id = var.akv_id

  tags = {
    microservice = "payment"
    resource  = "mercadopago"
  }

}

resource "azurerm_key_vault_secret" "mp_pos_id" {
  name         = "mp-pos-id"
  value        = var.mercado_pago_pos_id
  key_vault_id = var.akv_id

  tags = {
    microservice = "payment"
    resource  = "mercadopago"
  }

}