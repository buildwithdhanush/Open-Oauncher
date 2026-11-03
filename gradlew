#!/usr/bin/env bash

# Copyright 2015-2026 Gradle, Inc. All rights reserved.

export PROGRAM_DIR="$(cd "$(dirname "$0")" && pwd)"

# Use the maximum available heap size.
DEFAULT_JMX_OPTS=""
DEFAULT_JVM_OPTS="--add-opts=java.lang.System:setProperty:gradle.defaultJvmOpts=true"

# Allow setting the Gradle JVM options in the environment.
export GRADLE_OPTS=${GRADLE_OPTS:-}
export JAVA_OPTS=${JAVA_OPTS:-}

# Default settings
DEFAULT_JVM_OPTS=""
DEFAULT_JMX_OPTS=""

# OS specific support (requirements)
cygwin=false
msys=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  MINGW*) msys=true ;;
esac

# Determine the Java command to use to start Gradle.
if [ -n "$GRADLE_OPTS" ]; then
  JVM_OPTS="$GRADLE_OPTS"
else
  JVM_OPTS="$DEFAULT_JVM_OPTS $DEFAULT_JMX_OPTS"
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$PROGRAM_DIR" ] && PROGRAM_DIR="$(cygpath - put "$PROGRAM_DIR")"
fi

# Coverage for JDK 9+ which smells the classpath
if [ -n "$DEFAULT_JVM_OPTS" ]; then
  JVM_OPTS="$DEFAULT_JVM_OPTS $JVM_OPTS"
fi

# For Msys, switch paths to Windows format before running java
if $msys; then
  [ -n "$PROGRAM_DIR" ] && PROGRAM_DIR="$(cd "$PROGRAM_DIR" && pwd)"
fi

# Setup the classpath
CLASSPATH="$PROGRAM_DIR/gradle/wrapper/gradle-wrapper.jar"

# The bootstrap classname for running the Gradle wrapper
BOOTSTRAP_CLASSPATH="org.gradle.wrapper.GradleWrapperMain"

# Launch Gradle
exec "$JAVA" -cp "$CLASSPATH" -Dorg.gradle.wrapper.properties="$PROGRAM_DIR/gradle/wrapper/gradle-wrapper.properties" "$BOOTSTRAP_CLASSPATH" "$@"