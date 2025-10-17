resource "helm_release" "ingress_nginx_private" {
  name       = var.ingress_int_release_name
  namespace  = var.ingress_release_namespace
  repository = var.ingress_repository_url
  chart      = var.ingress_chart_name
  version    = var.ingress_chart_version

  # Permitir upgrade e reinstalação do release automaticamente (apenas para fins da atividade)
  upgrade_install = true
  force_update    = true

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-health-probe-request-path"
    value = "/healthz"
  }

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-internal"
    value = "true"
  }

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-ipv4"
    value = data.terraform_remote_state.infra.outputs.aks_api_private_ip
  }

  set {
    name  = "controller.ingressClass"
    value = var.ingress_int_class_name
  }

  set {
    name  = "controller.ingressClassResource.name"
    value = var.ingress_int_class_name
  }
}

resource "helm_release" "ingress_nginx_public" {
  name       = var.ingress_ext_release_name
  namespace  = var.ingress_release_namespace
  repository = var.ingress_repository_url
  chart      = var.ingress_chart_name
  version    = var.ingress_chart_version

  # Permitir upgrade e reinstalação do release automaticamente (apenas para fins da atividade)
  upgrade_install = true
  force_update    = true

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-health-probe-request-path"
    value = "/healthz"
  }

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-resource-group"
    value = data.terraform_remote_state.infra.outputs.resource_group_name
  }

  set {
    name  = "controller.service.loadBalancerIP"
    value = data.terraform_remote_state.infra.outputs.ext_ingress_public_ip
  }

  set {
    name  = "controller.ingressClass"
    value = var.ingress_ext_class_name
  }

  set {
    name  = "controller.ingressClassResource.name"
    value = var.ingress_ext_class_name
  }
}

resource "helm_release" "foodcoreapi" {
  name            = var.release_name
  repository      = var.repository_url
  chart           = var.chart_name
  version         = var.chart_version
  namespace       = var.release_namespace

  # Permitir upgrade e reinstalação do release automaticamente (apenas para fins da atividade)
  upgrade_install = true
  force_update    = true

  set {
    name  = "ingressInt.name"
    value = var.ingress_int_release_name
  }

  set {
    name  = "ingressInt.hosts[0].host"
    value = data.terraform_remote_state.infra.outputs.api_private_dns_fqdn
  }

  set {
    name  = "ingressExt.name"
    value = var.ingress_ext_release_name
  }

  set {
    name  = "ingressExt.hosts[0].host"
    value = data.terraform_remote_state.infra.outputs.ext_ingress_public_ip_fqdn
  }

  set {
    name  = "ingressInt.className"
    value = var.ingress_int_class_name
  }

  set {
    name  = "ingressExt.className"
    value = var.ingress_ext_class_name
  }

  set {
    name  = "api.image.repository"
    value = var.docker_image_uri
  }

  set {
    name  = "api.image.tag"
    value = var.docker_image_tag
  }

  set {
    name  = "api.ingress.path"
    value = var.api_ingress_path
  }

  set {
    name  = "api.azure.blob.connectionString"
    value = data.terraform_remote_state.infra.outputs.storage_account_connection_string
  }

  set {
    name  = "api.azure.blob.containerName"
    value = data.terraform_remote_state.infra.outputs.storage_container_name
  }

  set {
    name  = "api.auth.jwt.secret"
    value = var.jwt_secret
  }

  set {
    name  = "api.auth.jwt.expirationTime"
    value = var.jwt_expires_time
  }

  set {
    name  = "api.mercadoPago.baseUrl"
    value = var.mercadopago_base_url
  }

  set {
    name  = "api.mercadoPago.token"
    value = var.mercadopago_token
  }

  set {
    name  = "api.mercadoPago.userId"
    value = var.mercadopago_user_id
  }

  set {
    name  = "api.mercadoPago.posId"
    value = var.mercadopago_pos_id
  }

  set {
    name  = "api.mercadoPago.notificationUrl"
    value = "${data.terraform_remote_state.infra.outputs.apim_gateway_url}${var.api_ingress_path}/payments/webhook?subscription-key=${data.terraform_remote_state.infra.outputs.apim_foodcore_start_subscription_key}"
  }

  set {
    name  = "postgresql.auth.username"
    value = data.terraform_remote_state.db.outputs.pgsql_admin_username_foodcoreapi
  }

  set {
    name  = "postgresql.auth.password"
    value = data.terraform_remote_state.db.outputs.pgsql_admin_password_foodcoreapi
  }

  set {
    name  = "postgresql.auth.url"
    value = data.terraform_remote_state.db.outputs.jdbc_pgsql_connection_string_foodcoreapi
  }

  set {
    name  = "postgresql.fqdn"
    value = data.terraform_remote_state.db.outputs.pgsql_fqdn
  }

    depends_on = [ helm_release.ingress_nginx_private, helm_release.ingress_nginx_public ]

}