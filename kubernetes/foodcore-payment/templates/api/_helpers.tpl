{{- define "api.name" -}}
{{- .Values.api.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "api.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.api.name | trunc 63 | trimSuffix "-" }}
{{- end }}
