param([string]$Host = '127.0.0.1', [int]$Port = 5000)
$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$out = Join-Path $root 'out/main'
New-Item -ItemType Directory -Force -Path $out | Out-Null
$files = Get-ChildItem (Join-Path $root 'src/main/java') -Recurse -Filter '*.java'
javac --release 21 -d $out $files.FullName
java -cp $out br.edu.ifba.complexidade.noise.cliente.ClienteMain --host $Host --port $Port --csv (Join-Path $root 'dados\leituras-ruido.csv')
