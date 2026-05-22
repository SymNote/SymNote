#!/bin/bash

echo "Building Astro Starlight site..."
npm run build

echo "Starting preview server..."
npm run preview > /dev/null &
PREVIEW_PID=$!

sleep 5

echo "Generating Unified SymNote Documentation PDF..."
PUPPETEER_SKIP_DOWNLOAD=true npx starlight-to-pdf http://localhost:4321 \
  -p ../ \
  --filename SymNote_Documentation \
  --browser-executable /usr/bin/chromium \
  --contents-links 'internal' \
  --pdf-outline

kill $PREVIEW_PID
echo "Done! SymNote_Documentation.pdf generated in the parent directory."
