#!/usr/bin/env bash
aws --endpoint-url=http://localhost:8000 --region us-east-1 dynamodb create-table --table-name Account \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws --endpoint-url=http://localhost:8000 --region us-east-1 dynamodb update-table \
    --table-name Account \
    --attribute-definitions AttributeName=userId,AttributeType=S \
    --global-secondary-index-updates \
    "[{\"Create\":{\"IndexName\": \"account-user-index\",\"KeySchema\":[{\"AttributeName\":\"userId\",\"KeyType\":\"HASH\"}], \
    \"ProvisionedThroughput\": {\"ReadCapacityUnits\": 1, \"WriteCapacityUnits\": 1      },\"Projection\":{\"ProjectionType\":\"ALL\"}}}]"

aws --endpoint-url=http://localhost:8000 --region us-east-1 dynamodb create-table --table-name Product \
    --attribute-definitions AttributeName=skuOrId,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1;

aws --endpoint-url=http://localhost:8000 --region us-east-1 dynamodb create-table --table-name Transaction \
    --attribute-definitions AttributeName=accountId,AttributeType=S AttributeName=transactionTypeAndId,AttributeType=S \
    --key-schema AttributeName=accountId,KeyType=HASH AttributeName=transactionTypeAndId,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1;