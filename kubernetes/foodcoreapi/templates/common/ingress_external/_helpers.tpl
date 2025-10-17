{{- define "ingressExt.name" -}}
{{- .Values.ingressExt.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingressExt.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.ingressExt.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "ingressExt.services" -}}
- name: {{ printf "%s-service" (include "adminer.fullname" .) }}
  namespace: {{ .Values.namespace.api.name }}
  port: {{ .Values.adminer.ports.port }}
  path: {{ .Values.adminer.ingress.path }}
  pathType: {{ .Values.adminer.ingress.pathType }}

- name: {{ printf "%s-service" (include "kibana.fullname" .) }}
  namespace: {{ .Values.namespace.efk.name }}
  port: {{ .Values.kibana.ports.port }}
  path: {{ .Values.kibana.ingress.path }}
  pathType: {{ .Values.kibana.ingress.pathType }}

- name: {{ printf "%s-service" (include "grafana.fullname" .) }}
  namespace: {{ .Values.namespace.monitor.name }}
  port: {{ .Values.grafana.ports.port }}
  path: {{ .Values.grafana.ingress.path }}
  pathType: {{ .Values.grafana.ingress.pathType }}

- name: {{ printf "%s-service" (include "prometheus.fullname" .) }}
  namespace: {{ .Values.namespace.monitor.name }}
  port: {{ .Values.prometheus.ports.port }}
  path: {{ .Values.prometheus.ingress.path }}
  pathType: {{ .Values.prometheus.ingress.pathType }}

- name: {{ printf "%s-service" (include "zipkin.fullname" .) }}
  namespace: {{ .Values.namespace.monitor.name }}
  port: {{ .Values.zipkin.ports.port }}
  path: {{ .Values.zipkin.ingress.path }}
  pathType: {{ .Values.zipkin.ingress.pathType }}
{{- end }}

