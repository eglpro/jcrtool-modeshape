#!/bin/sh

TREE=$PWD

BRANCH=master

git submodule init &&
        git submodule update --recursive &&
        git submodule foreach "git checkout $BRANCH"
