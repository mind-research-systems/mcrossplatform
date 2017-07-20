#!/bin/bash
if [ ! -f "$HOME/j2objc/j2objc" ]; then
	echo $HOME/j2objc not found -> download j2objc.zip to $HOME
	rm -rf $HOME/j2objc
	set -e
	curl -L -o $HOME/j2objc.zip https://github.com/google/j2objc/releases/download/2.0.1/j2objc-2.0.1.zip
	unzip -qd $HOME $HOME/j2objc.zip
	rm -rf $HOME/j2objc
	mv $HOME/j2objc-2.0.1 $HOME/j2objc
	rm $HOME/j2objc.zip
else
	echo found $HOME/j2objc -> no download	
fi