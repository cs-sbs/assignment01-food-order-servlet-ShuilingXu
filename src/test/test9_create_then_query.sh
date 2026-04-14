#!/bin/bash

set -euo pipefail
BASE_URL="http://localhost:8080"

curl -s -X POST $BASE_URL/order \
  -d "customer=Charlie" \
  -d "food=Noodles" \
  -d "quantity=3" > /dev/null

tmp="$(mktemp)"
status=$(curl -s -o "$tmp" -w "%{http_code}" $BASE_URL/order/1003)
body="$(cat "$tmp")"
rm -f "$tmp"

if [[ "$status" == "200" ]] && [[ "$body" == *"Charlie"* ]]; then
  echo "PASS: create then query works"
  exit 0
else
  echo "FAIL: create then query failed"
  exit 1
fi
