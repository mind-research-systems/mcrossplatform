#!/bin/bash
set -e
curl -L -o j2objc.zip https://github.com/google/j2objc/releases/download/2.0.1/j2objc-2.0.1.zip
unzip -q j2objc.zip
rm -rf j2objc
mv j2objc-2.0.1 j2objc
rm j2objc.zip
