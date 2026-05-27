@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET "__MVNW_ARG0_NAME__=%~nx0")
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PWSH_SESSION_FILE__=%TEMP%\%__MVNW_ARG0_NAME__%-%RANDOM%-%RANDOM%.txt

@IF NOT "%MVNW_USERNAME%" == "" (
  @SET __MVNW_CMD__=pwsh -NonInteractive -NoLogo -NoProfile
)
@IF "%__MVNW_CMD__%"=="" (
  @IF NOT "%MVNW_REPOURL%" == "" (
    @SET __MVNW_CMD__=pwsh -NonInteractive -NoLogo -NoProfile
  )
)
@IF "%__MVNW_CMD__%"=="" (
  @SET __MVNW_CMD__=powershell -NonInteractive -NoLogo -NoProfile
)
%__MVNW_CMD__% -ExecutionPolicy ByPass -command "& {try {if (!(Test-Path '%USERPROFILE%\.m2\wrapper\dists')) {New-Item -ItemType Directory -Force '%USERPROFILE%\.m2\wrapper\dists' | Out-Null}} catch {}; $wrapperProperties = Get-Content -Raw -Encoding UTF8 '%~dp0.mvn\wrapper\maven-wrapper.properties'; $distributionUrl = ($wrapperProperties -split '\n' | Where-Object {$_ -match '^distributionUrl='} | Select-Object -First 1) -replace '^distributionUrl=',''; $wrapperUrl = ($wrapperProperties -split '\n' | Where-Object {$_ -match '^wrapperUrl='} | Select-Object -First 1) -replace '^wrapperUrl=',''; $filename = $distributionUrl.Split('/')[-1]; $distDir = Join-Path $env:USERPROFILE '.m2\wrapper\dists' ($filename -replace '\.zip$',''); if (!(Test-Path $distDir)) {Write-Host 'Downloading Maven...'; New-Item -ItemType Directory -Force $distDir | Out-Null; $zipPath = Join-Path $distDir $filename; Invoke-WebRequest -Uri $distributionUrl -OutFile $zipPath; Expand-Archive -Path $zipPath -DestinationPath $distDir; Remove-Item $zipPath}; $mvnDir = (Get-ChildItem $distDir -Directory | Select-Object -First 1).FullName; $mvnExe = Join-Path $mvnDir 'bin\mvn.cmd'; & $mvnExe $args}" %*
