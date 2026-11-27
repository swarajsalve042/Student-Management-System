# Student Management System

[![CI](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml/badge.svg)](https://github.com/swarajsalve042/Student-Management-System/actions/workflows/ci.yml)

A Java 17 JDBC data-access project for managing student records with parameterized SQL and an H2 in-memory database.

## Features

- Initialize the student table safely more than once.
- Add students and retrieve generated database IDs.
- List students in ID order and retrieve an individual student.
- Update student details and delete records by ID.
- Validate required fields, email format, field lengths and positive IDs.
- Trim names/courses and normalize email addresses to lowercase.
- Use PreparedStatement for inserts, lookups, updates and deletes.
- Close JDBC connections, statements and result sets with try-with-resources.

## Requirements

- Java 17
- Maven 3.9+

## Run tests

```bash
mvn --batch-mode test
```

The JUnit 5 test suite uses H2 and covers create, list, lookup, update, delete, duplicate email handling, invalid input, missing IDs, repeated schema initialization and SQL-injection-style input treated as data.

## Repository API

- initialize() — create the students table if it does not exist.
- add(name, email, course) — validate and insert a student; return generated ID.
- findAll() — list student rows in ID order.
- findById(id) — return an optional student row.
- update(id, name, email, course) — update a record and report whether it existed.
- delete(id) — delete a record and report whether it existed.

## Scope and limitations

This repository currently demonstrates a JDBC persistence layer, not a complete student management application. It does not include a web/desktop interface, REST API, authentication, authorization, pagination or production database deployment. The H2 database used by tests is in-memory.
