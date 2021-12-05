#!/bin/bash

gradle clean && gradle assemble && gradle check && gradle publishToMavenLocal