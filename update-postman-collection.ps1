param(
    [string]$BaseUrl = "http://localhost:8080",
    [string]$CollectionFileName = "FIAP-Tech-Challenge.postman_collection.json",
    [switch]$DryRun,
    [switch]$KeepGeneratedFile
)

$ErrorActionPreference = "Stop"

function Write-Step {
    param([string]$Message)
    Write-Host "`n==> $Message" -ForegroundColor Cyan
}

function Ensure-Command {
    param([string]$Name)
    if (-not (Get-Command $Name -ErrorAction SilentlyContinue)) {
        throw "Comando '$Name' nao encontrado no PATH."
    }
}

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$targetDir = Join-Path $projectRoot "target"
$collectionsDir = Join-Path $projectRoot "collections"
$openApiPath = Join-Path $targetDir "openapi.json"
$generatedDir = Join-Path $targetDir "postman-generated"
$destinationCollectionPath = Join-Path $collectionsDir $CollectionFileName

Write-Step "Validando dependencias"
if (-not $DryRun) {
    Ensure-Command "mvn"
} else {
    Write-Host "[DryRun] Validacao do comando 'mvn' pulada." -ForegroundColor Yellow
}

if (-not (Test-Path $collectionsDir)) {
    throw "Diretorio de collections nao encontrado: $collectionsDir"
}

if (-not (Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir | Out-Null
}

Write-Step "Baixando contrato OpenAPI de $BaseUrl/v3/api-docs"
if (-not $DryRun) {
    $ProgressPreference = "SilentlyContinue"
    Invoke-WebRequest -Uri "$BaseUrl/v3/api-docs" -OutFile $openApiPath -Headers @{ Accept = "application/json" }
    Write-Host "Contrato salvo em $openApiPath" -ForegroundColor Green
} else {
    Write-Host "[DryRun] Download pulado." -ForegroundColor Yellow
}

Write-Step "Gerando Postman collection com openapi-generator"
$generatorCommand = "org.openapitools:openapi-generator-maven-plugin:7.7.0:generate"
$generatorArgs = @(
    "-q",
    $generatorCommand,
    "-DgeneratorName=postman-collection",
    "-DinputSpec=$openApiPath",
    "-Doutput=$generatedDir"
)

if (-not $DryRun) {
    & mvn @generatorArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Falha na execucao do openapi-generator."
    }
} else {
    Write-Host "[DryRun] mvn $($generatorArgs -join ' ')" -ForegroundColor Yellow
}

$generatedCandidates = @(
    (Join-Path $generatedDir "postman_collection.json"),
    (Join-Path $generatedDir "postman-collection.json")
)

$generatedCollectionPath = $generatedCandidates | Where-Object { Test-Path $_ } | Select-Object -First 1

if (-not $DryRun -and -not $generatedCollectionPath) {
    throw "Arquivo de collection gerado nao encontrado em $generatedDir"
}

Write-Step "Aplicando backup e atualizando collection final"
if (-not $DryRun) {
    if (Test-Path $destinationCollectionPath) {
        $timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
        $backupPath = "$destinationCollectionPath.$timestamp.bak"
        Copy-Item -Path $destinationCollectionPath -Destination $backupPath
        Write-Host "Backup salvo em $backupPath" -ForegroundColor Green
    }

    Copy-Item -Path $generatedCollectionPath -Destination $destinationCollectionPath -Force
    Write-Host "Collection atualizada em $destinationCollectionPath" -ForegroundColor Green

    if (-not $KeepGeneratedFile) {
        Remove-Item -Path $generatedDir -Recurse -Force
        Write-Host "Arquivos temporarios removidos." -ForegroundColor Green
    }
} else {
    Write-Host "[DryRun] Atualizacao de arquivo pulada." -ForegroundColor Yellow
}

Write-Step "Concluido"
Write-Host "Use esse arquivo no Postman: $destinationCollectionPath" -ForegroundColor Cyan

