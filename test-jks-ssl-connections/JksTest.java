import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Enumeration;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class JksTest {
    public static void main(String[] args) {
        // Adjust these variables as needed
        String keystorePath = System.getenv("KEYSTORE_PATH");
        String keystorePassword = System.getenv("KEYSTORE_PASSWORD");
        //System.setProperty("javax.net.debug", "ssl");

        String host = "www.google.com";
        int port = 443; // Default HTTPS port

        // list all certificates in the keystore
        try {
            System.out.println("TEST 1 - Listing all certificates in the keystore: " + keystorePath);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream keystoreFis = new FileInputStream(keystorePath);
            keystore.load(keystoreFis, keystorePassword.toCharArray());
            Enumeration<String> aliases = keystore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Found certificate for alias: " + alias);
            }

            keystoreFis.close();
            System.out.println("Done!");
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test a connection with no given truststore / default java cacerts truststore
        try {
            System.out.println("TEST 2 - Testing connection with no given truststore / default java cacerts truststore");

            javax.net.ssl.HttpsURLConnection connection = (javax.net.ssl.HttpsURLConnection) new java.net.URL("https://www.google.com").openConnection();
            connection.connect();
            System.out.println("Response code: " + connection.getResponseCode());
            connection.disconnect();
            System.out.println("Connection successful!");
            System.out.println("Done!");
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test a connection with the given truststore
        try {
            System.out.println("TEST 3 - Testing connection with the given truststore: " + keystorePath);
            // Load the truststore
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream fis = new FileInputStream(keystorePath)) {
                trustStore.load(fis, keystorePassword.toCharArray());
            }

            // Create a custom trust manager that uses the truststore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            TrustManager[] trustManagers = tmf.getTrustManagers();

            // Create a custom SSL context that uses the trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);

            // Create a connection and set the custom SSL context
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://www.google.com").openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());

            // Now the connection will use the custom truststore
            connection.connect();
            System.out.println("Response code: " + connection.getResponseCode());
            connection.disconnect();
            System.out.println("Connection successful!");
            System.out.println("Done!");
            System.out.println("----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("TEST 4 - Validating TLS/crypto details of the connection");

            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (FileInputStream fis = new FileInputStream(keystorePath)) {
                keystore.load(fis, keystorePassword.toCharArray());
            }

            // Initialize TrustManagerFactory with the keystore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keystore);

            // Initialize an SSLContext with the TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Create an SSLSocketFactory from the SSLContext
            SSLSocketFactory factory = context.getSocketFactory();

            // Create an SSLSocket to the host
            try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
                // Start handshake manually to have access to SSLSession
                socket.startHandshake();

                // Retrieve SSLSession to get details
                System.out.println("Session Details:");
                System.out.println("Protocol: " + socket.getSession().getProtocol());
                System.out.println("Cipher suite: " + socket.getSession().getCipherSuite());

                // Add any additional session details you need here
            System.out.println("Done!");
            System.out.println("----------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All tests done!");
    }
}
