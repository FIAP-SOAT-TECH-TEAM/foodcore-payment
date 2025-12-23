locals {
  mp_notification_url = "${data.terraform_remote_state.infra.outputs.apim_gateway_url}${var.api_ingress_path}/webhook?subscription-key=${data.terraform_remote_state.infra.outputs.apim_foodcore_start_subscription_key}"
}