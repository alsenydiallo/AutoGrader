#!/bin/bash


# driver.sh - The simplest autograder using JUnitTests.
#   Usage: ./driver.sh

# Compile the code

echo "Cleaning..."
make --no-print-directory clean
echo "Compiling..."
rm -rf ${BUILD}
make --no-print-directory bin
status=$?
if [ ${status} -ne 0 ]; then
    echo "Failure: Unable to compile (return status = ${status})"
    echo "{\"scores\": {\"Correctness\": 0}}"
    exit
fi

# Run the code
echo "Running..."
make --no-print-directory run
status=$?
if [ ${status} -ne 0 ]; then
    echo "Failure: testsuite failed with nonzero exit status of ${status}"
    echo "{\"scores\": {\"Correctness\": 0}}"
fi

exit
