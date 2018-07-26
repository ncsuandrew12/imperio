#!/bin/bash

#
# Be user friendly and compensate for being in wrong directory
#
cd `dirname "${BASH_SOURCE[0]}"`
if [ `basename \`pwd\`` != "Imperio" ]; then
    echo "Could not auto-cd to Options directory. Ended up in "`pwd` >&2
    exit 1
fi

set -e

echo "Building Imperio"
gradle clean

gradle release

set +e
exit 0
