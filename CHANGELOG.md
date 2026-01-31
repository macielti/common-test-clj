# Change Log

All notable changes to this project will be documented in this file. This change log follows the conventions
of [keepachangelog.com](http://keepachangelog.com/).

## 7.0.0 - 2026-01-31

### Changed

- Now you are able to provide custom leaf generators.

## 6.2.4 - 2026-01-03

### Added

- Added `pg2-migration` as a new dependency for migration support.

### Changed

- Updated PostgreSQL mock component to use migrations for database setup instead of raw schemas.
- Bumped versions of multiple dependencies to their latest releases.

## 5.2.4 - 2025-02-01

### Changed

- Bump dependencies.

## 5.2.3 - 2024-12-03

### Changed

- Remove dependencies to `pg.migration`.

## 4.2.3 - 2024-11-25

### Changed

- Use database connection instead of database spec for sqlite.

## 3.2.3 - 2024-11-23

### Fixed

- Fixed leaf generator for `LocalDateWire`.

## 3.2.2 - 2024-11-23

### Added

- Added generators for `LocalDateWire`, `LocalDateTimeWire`, and `UuidWire`.

## 3.1.2 - 2024-11-20

### Added

- Bump dependencies.

## 3.1.1 - 2024-11-20

### Changed

- Added `schemas` param to sqlite component and unit mock tooling.

## 2.1.1 - 2024-11-19

### Added

- Added integration with sqlite.

## 1.1.1 - 2024-11-15

### Fixed

- Better handling startup time for the PostgreSQL container while attempting to establish a connection.

## 1.1.0 - 2024-11-07

### Added

- Schema generators helpers.
- Bump dependencies.

## 1.0.0 - 2024-11-03

### Added

- Added PostgreSQL Integrant Mock Component.
