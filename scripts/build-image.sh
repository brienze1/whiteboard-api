#!/bin/bash

docker build . -t lfbrienze/whiteboard-api

docker tag lfbrienze/whiteboard-api lfbrienze/whiteboard-api:1

docker push lfbrienze/whiteboard-api:1