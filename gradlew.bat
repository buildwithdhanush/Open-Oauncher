#!/usr/bin/env bat

@echo off

:: Copyright 2015-2026 Gradle, Inc. All rights reserved.

set PROGRAM_DIR=%~dp0

:: Use the maximum available heap size.
set DEFAULT_JMX_OPTS=""
set DEFAULT_JVM_OPTS="--add-opts=java.lang.System:setProperty:gradle.defaultJvmOpts=true"

:: Allow setting the Gradle JVM options in the environment.
set GRADLE_OPTS=%GRADLE_OPTS%
set JAVA_OPTS=%JAVA_OPTS%

:: Default settings
set DEFAULT_JVM_OPTS=""
set DEFAULT_JMX_OPTS=""

:: OS specific support (requirements)
cygwin=false
msys=false
call unix:uname 2>nul
if %errorlevel% == 0 (
  for /f "tokens=%*" %%i in ('unix:uname') do (
    set uname=%%i
  )
)
if "%uname%" == "CYGWIN*" (
  set cygwin=true
)
if "%uname%" == "MINGW*" (
  set msys=true
)

:: Determine the Java command to use to start Gradle.
if defined GRADLE_OPTS (
  set JVM_OPTS=%GRADLE_OPTS%
) else (
  set JVM_OPTS=%DEFAULT_JVM_OPTS% %DEFAULT_JMX_OPTS%
)

:: For Cygwin, switch paths to Windows format before running java
if %cygwin%==true (
  for /f "tokens=*" %%a in ("%PROGRAM_DIR%") do set PROGRAM_DIR=%%~fa
)

:: Coverage for JDK 9+ which smells the classpath
if defined DEFAULT_JVM_OPTS (
  set JVM_OPTS=%DEFAULT_JVM_OPTS%%JVM_OPTS%
)

:: For Msys, switch paths to Windows format before running java
if %msys%==true (
  call set PROGRAM_DIR=%cd%
)

:: Setup the classpath
set CLASSPATH="%PROGRAM_DIR%/gradle/wrapper/gradle-wrapper.jar"

:: The bootstrap classname for running the Gradle wrapper
set BOOTSTRAP_CLASSPATH="org.gradle.wrapper.GradleWrapperMain"

:: Launch Gradle
"%JAVA%" -cp "%CLASSPATH%" -Dorg.gradle.wrapper.properties="%PROGRAM_DIR%/gradle/wrapper/gradle-wrapper.properties" %BOOTSTRAP_CLASSPATH% %*