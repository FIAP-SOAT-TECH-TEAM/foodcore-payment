{{- define "adminer.name" -}}
{{- .Values.adminer.name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "adminer.fullname" -}}
{{- printf "%s-%s" .Chart.Name .Values.adminer.name | trunc 63 | trimSuffix "-" }}
{{- end }}