---
title: Build & Packaging
description: Developer guide for building, testing, and packaging SymNote.
sidebar:
  order: 5
---

This page covers the full build pipeline, developer scripts, CI/CD workflows, and how native installers are produced.

## Requirements

| Tool | Version | Purpose |
|---|---|---|
| JDK | 21+ | Compiling Java sources and running `jpackage` |
| Bash | any | Running developer scripts |
| ANTLR | 4.13.2 | Grammar-to-Java code generation (bundled in `lib/`) |
| `rpm` | any | Building `.rpm` packages — `sudo apt install rpm` |
| `fakeroot` | any | Building `.deb` packages — `sudo apt install fakeroot` |

> ANTLR does not need to be installed separately — `lib/antlr-4.13.2-complete.jar` is committed to the repository.

---

## Developer Scripts

All scripts are located in the project root and must be run from there.

### `generate.sh`

Compiles the ANTLR grammar (`grammar/SymNote.g4`) into Java source files, placing them in `src/gen/`.

```bash
./generate.sh
```

Only needs to be run once, or after any change to `SymNote.g4`.

### `run.sh`

Compiles all Java sources and immediately runs a `.symnote` file. Intended for quick local testing during development — does **not** produce a distributable artifact.

```bash
./run.sh examples/HelloWorld.symnote
```

Requires the ANTLR parser to have been generated first (`./generate.sh`).

### `test.sh`

Compiles all sources and the JUnit 5 test suite, then runs all tests. Results are printed to the console and saved to `logs/test_results.log`.

```bash
./test.sh
```

### `package.sh`

Builds a self-contained fat jar (`symnote.jar`) suitable for distribution. This is the artifact that `jpackage` wraps into native installers.

```bash
./package.sh
```

Steps performed:
1. Runs `./generate.sh` if `src/gen/` is missing or empty
2. Compiles all Java sources with `javac`
3. Extracts the ANTLR runtime jar into the build directory
4. Packages everything into `symnote.jar` with `Main-Class: Main` in the manifest
5. Runs a sanity check (`java -jar symnote.jar` with no args)

---

## CI/CD Workflows

### `test.yml` — Continuous Integration

**Trigger:** every push or pull request to `main`

Runs on `ubuntu-22.04`:
1. Checkout
2. Set up JDK 21 (Temurin)
3. `./generate.sh`
4. `./test.sh`

Purpose: fast signal on every commit — no packaging involved.

### `ci-docs.yml` — Documentation Build Check

**Trigger:** pull requests to `main` that touch `docs/**`

Runs on `ubuntu-22.04`:
1. Checkout
2. Set up Node 20
3. `npm ci` in `docs/`
4. `npm run build` in `docs/`

Purpose: ensures the documentation site builds without errors before merging.

### `deploy-docs.yml` — Documentation Deployment

**Trigger:** push to `main` (after merge)

Builds and deploys the Astro documentation site to GitHub Pages.

### `release.yml` — Release Pipeline

**Trigger:** push of a tag matching `v*.*.*`

```bash
git tag v1.0.0
git push origin v1.0.0
```

Three sequential jobs:

#### Job 1 — `build-jar`
- Runs `./generate.sh` and `./package.sh`
- Uploads `symnote.jar` as a workflow artifact

#### Job 2 — `package-linux` (needs: `build-jar`)
- Runs on `ubuntu-22.04` — ensures the `.deb` depends on `libasound2` rather than `libasound2t64`, making it installable on Ubuntu 22.04 and later
- Installs `rpm` and `fakeroot`
- Strips the `v` prefix and replaces hyphens with tildes to produce a version string valid for both deb and rpm (e.g. `v1.0.0-rc.1` → `1.0.0~rc.1`)
- Runs `jpackage --type deb` → produces `.deb`
- Runs `jpackage --type rpm` → produces `.rpm`
- Both use `--resource-dir packaging/linux/` for lifecycle scriptlets
- Uploads both packages as a workflow artifact

#### Job 3 — `publish-release` (needs: `package-linux`)
- Downloads all artifacts
- Creates a GitHub Release on the tag via `softprops/action-gh-release`
- Attaches `symnote.jar`, `.deb`, and `.rpm` to the release

---

## jpackage Configuration

`jpackage` bundles a full JRE 21 into each package — end users do not need Java installed.

### Java modules bundled

| Module | Reason |
|---|---|
| `java.base` | Core Java classes |
| `java.desktop` | `javax.sound.midi.*` — MIDI synthesizer |
| `java.logging` | `java.util.logging` — used internally by ANTLR |
| `java.xml` | XML parsing — used internally by ANTLR |

Full flag: `--add-modules java.base,java.desktop,java.logging,java.xml`

### Resource directory: `packaging/linux/`

Both `.deb` and `.rpm` packages are built with `--resource-dir packaging/linux/`. jpackage picks up different files per format based on exact filenames (no extensions):

| File | Format | Hook | What it does |
|---|---|---|---|
| `postinst` | deb | post-install | `ln -sf /opt/symnote/bin/symnote /usr/local/bin/symnote` |
| `prerm` | deb | pre-remove | `rm -f /usr/local/bin/symnote` |
| `post-install` | rpm | `%post` | `ln -sf /opt/symnote/bin/symnote /usr/local/bin/symnote` |
| `pre-uninstall` | rpm | `%preun` | `rm -f /usr/local/bin/symnote` (on full removal only) |

After installation on either format, `symnote` is available system-wide without specifying a full path.
