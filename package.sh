#!/bin/bash
# Builds a self-contained fat jar (symnote.jar) for distribution.

set -e

ANTLR_JAR="lib/antlr-4.13.2-complete.jar"
OUTPUT_JAR="symnote.jar"
BUILD_DIR="out/package"
EXTRACTED_ANTLR_DIR="out/antlr-extracted"
MANIFEST_FILE="out/MANIFEST.MF"

if [ ! -f "$ANTLR_JAR" ]; then
    echo "Error: ANTLR jar not found at $ANTLR_JAR"
    exit 1
fi

if [ ! -d "src/gen" ] || [ -z "$(ls -A src/gen 2>/dev/null)" ]; then
    echo "=== Generating ANTLR parser ==="
    chmod +x generate.sh
    ./generate.sh
else
    echo "=== ANTLR parser already generated, skipping ==="
fi

echo "=== Compiling Java sources ==="
mkdir -p "$BUILD_DIR"
javac --release 21 -cp "$ANTLR_JAR:src:src/gen" \
    src/gen/*.java \
    src/audio/*.java \
    src/environment/*.java \
    src/midi/*.java \
    src/*.java \
    -d "$BUILD_DIR"

echo "=== Extracting ANTLR runtime into fat jar ==="
mkdir -p "$EXTRACTED_ANTLR_DIR"
cd "$EXTRACTED_ANTLR_DIR"
jar -xf "../../$ANTLR_JAR"
cd ../..
cp -r "$EXTRACTED_ANTLR_DIR"/* "$BUILD_DIR/" 2>/dev/null || true
rm -rf "$EXTRACTED_ANTLR_DIR"

echo "=== Writing manifest ==="
mkdir -p out
cat > "$MANIFEST_FILE" <<EOF
Main-Class: Main
EOF

echo "=== Packaging $OUTPUT_JAR ==="
jar --create \
    --file "$OUTPUT_JAR" \
    --manifest "$MANIFEST_FILE" \
    -C "$BUILD_DIR" .

echo "=== Sanity check ==="
OUTPUT=$(java -jar "$OUTPUT_JAR" 2>&1 || true)
if echo "$OUTPUT" | grep -q "Please provide a file path"; then
    echo "OK: $OUTPUT_JAR responds correctly with no arguments."
else
    echo "WARNING: Unexpected output from sanity check: $OUTPUT"
    echo "         The jar may still work correctly with a .symnote file."
fi

echo ""
echo "Done. Distribution jar: $OUTPUT_JAR"
echo "  Run with: java -jar $OUTPUT_JAR <path/to/script.symnote>"
