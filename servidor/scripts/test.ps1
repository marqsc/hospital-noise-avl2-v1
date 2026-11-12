$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$out = Join-Path $root 'out/test'
New-Item -ItemType Directory -Force -Path $out | Out-Null
$main = Get-ChildItem (Join-Path $root 'src/main/java') -Recurse -Filter '*.java'
$tests = Get-ChildItem (Join-Path $root 'src/test/java') -Recurse -Filter '*.java'
javac --release 21 --add-modules jdk.httpserver -d $out $main.FullName $tests.FullName
java --add-modules jdk.httpserver -cp $out br.edu.ifba.complexidade.noise.servidor.ServidorHttpTest
