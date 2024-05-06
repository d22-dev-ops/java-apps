#!/bin/bash
set -e  # Stop on first error

echo "Creating SNS topic..."
awslocal --endpoint-url=http://localhost:4566 sns create-topic --name your-topic-name

echo "Creating SQS queue..."
awslocal --endpoint-url=http://localhost:4566 sqs create-queue --queue-name your-queue-name

awslocal --endpoint-url=http://localstack:4566 sqs list-queues
awslocal --endpoint-url=http://localstack:4566 sns list-topics

echo "Linking SQS queue to SNS topic..."
awslocal --endpoint-url=http://localhost:4566 sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:your-topic-name --protocol sqs --notification-endpoint http://localhost:4566/000000000000/your-queue-name

awslocal --endpoint-url=http://localstack:4566 sqs list-queues
awslocal --endpoint-url=http://localstack:4566 sns list-topics
