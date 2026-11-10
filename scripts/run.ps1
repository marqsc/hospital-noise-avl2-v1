$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$outputDirectory = Join-Path $projectRoot 'out/main'
$sourceDirectory = Join-Path $projectRoot 'src/main/java'

New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null
$sourceFiles = Get-ChildItem -Path $sourceDirectory -Recurse -Filter '*.java'

if ($sourceFiles.Count -eq 0) {
    throw 'Nenhum arquivo Java encontrado em src/main/java.'
}

javac --release 21 -d $outputDirectory $sourceFiles.FullName
java -cp $outputDirectory br.edu.ifba.complexidade.noise.Main
