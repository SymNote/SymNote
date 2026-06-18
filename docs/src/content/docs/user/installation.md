---
title: Installation & Usage
description: How to install and run SymNote programs.
sidebar:
  order: 2
---

SymNote is a music scripting language that compiles and plays audio using a built-in MIDI synthesizer.

## Option A — Native Installer (Recommended)

The easiest way to install SymNote.

### Download

Go to the [GitHub Releases](https://github.com/trojancoding/SymNote/releases) page and download the package for your Linux distribution:

| Distribution | File |
|---|---|
| Debian / Ubuntu | `symnote_<version>_amd64.deb` |
| Fedora / RHEL / openSUSE | `symnote-<version>-1.x86_64.rpm` |

### Install

```bash
# Debian / Ubuntu
sudo apt install ./symnote_<version>_amd64.deb

# Fedora / RHEL
sudo rpm -i symnote-<version>-1.x86_64.rpm
```

### Run

After installation, `symnote` is available system-wide:

```bash
symnote my_song.symnote
```

### Uninstall

```bash
# Debian / Ubuntu
sudo apt remove symnote

# Fedora / RHEL
sudo rpm -e symnote
```

The `symnote` command is removed from PATH automatically on uninstall.

---

## Option B — Running from JAR

If you prefer not to use a system package, you can run SymNote directly from the JAR file.

### Requirements

- **Java Runtime Environment (JRE) 17** or higher  

### Download

Go to the [GitHub Releases](https://github.com/trojancoding/SymNote/releases) page and download `symnote.jar`.

### Run

```bash
java -jar symnote.jar my_song.symnote
```

---

## Writing Your First Program

Create a file with the `.symnote` extension. A minimal program that plays a C major arpeggio:

```ts
set_bpm(120);

synth Piano = load_synth("piano");

track Arpeggio(int bars) {
    use_synth(Piano);
    loop (int i from 1 to bars) {
        grid(1/8) {
            C4 E4 G4 C5 G4 E4 C4 -
        }
    }
}

Arpeggio(2);
```

Save it as `my_song.symnote` and run:

```bash
# If installed via package:
symnote my_song.symnote

# If running from JAR:
java -jar symnote.jar my_song.symnote
```
