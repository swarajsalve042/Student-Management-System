# Student Management System

[![ci](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml/badge.svg)](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml)

[![CI](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml/badge.svg)](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml)

Java 17 JDBC data-access project for student records.

## Features
- Initializes a students table.
- Adds students with prepared statements and generated IDs.
- Lists student records.
- Deletes students by ID.
- Uses try-with-resources for JDBC resource management.
- JUnit 5 tests with an H2 in-memory database.

## Requirements
Java 17 and Maven 3.9+.

## Test
`mvn test`

## Scope
This repository demonstrates the JDBC persistence layer only. It does not include a web/desktop UI, authentication, or production database deployment.

## Latest automated verification (25 November 2026)

- [Maven/JDBC tests — successful GitHub Actions run](https://github.com/swarajsalve042/Student-Management-System/actions/runs/45575564686)

These links document automated checks only; they do not imply production deployment or manual end-user acceptance testing.
