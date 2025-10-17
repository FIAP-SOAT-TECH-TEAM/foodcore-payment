{{- define "ingressInt.name" -}}
{{- .Values.ingressInt.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingressInt.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.ingressInt.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingressInt.services" -}}
- name: {{ printf "%s-service" (include "api.fullname" .) }}
  namespace: {{ .Values.namespace.api.name }}
  port: {{ .Values.api.ports.port }}
  path: {{ .Values.api.ingress.path }}
  pathType: {{ .Values.api.ingress.pathType }}
{{- end }}

