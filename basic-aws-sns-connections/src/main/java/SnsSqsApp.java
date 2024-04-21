package basic.aws.sns.connections.src.main.java;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnsSqsApp {
    private static final Logger logger = LogManager.getLogger(SnsSqsApp.class);

    public static void main(String[] args) {
        logger.debug("Starting SNS-SQS app");
        AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
        AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
        
        String sqsUrl = System.getenv("SQS_QUEUE_URL");
        String snsTopicArn = System.getenv("SNS_TOPIC_ARN");

        while (true) {
            logger.debug("Polling SQS for messages");
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsUrl).withMaxNumberOfMessages(10);
            var messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
            logger.debug("Received {} messages from SQS", messages.size());
            for (Message message : messages) {
                logger.debug("Publishing message to SNS: {}", message.getBody());
                snsClient.publish(snsTopicArn, message.getBody());
                sqsClient.deleteMessage(sqsUrl, message.getReceiptHandle());
                logger.debug("Deleted message from SQS: {}", message.getBody());
            }

            try {
                Thread.sleep(1000); // Pause to reduce API call frequency
            } catch (InterruptedException e) {
                logger.error("Thread sleep interrupted", e);
                e.printStackTrace();
            }
        }
    }
}
