$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$outputDirectory = Join-Path $projectRoot 'out/test'
$mainSourceDirectory = Join-Path $projectRoot 'src/main/java'
$testSourceDirectory = Join-Path $projectRoot 'src/test/java'

if (-not (Test-Path -Path $testSourceDirectory)) {
    Write-Output 'Ainda não há testes automatizados. A estrutura está pronta para recebê-los.'
    exit 0
}

New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null
$mainSources = Get-ChildItem -Path $mainSourceDirectory -Recurse -Filter '*.java'
$testSources = Get-ChildItem -Path $testSourceDirectory -Recurse -Filter '*.java'

if ($testSources.Count -eq 0) {
    Write-Output 'Ainda não há testes automatizados. A estrutura está pronta para recebê-los.'
    exit 0
}

javac --release 21 -d $outputDirectory $mainSources.FullName $testSources.FullName
java -cp $outputDirectory br.edu.ifba.complexidade.noise.service.MonitoramentoRuidoServiceTest
