import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Enumeration;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class JksTest {
    public static void main(String[] args) {
        // Adjust these variables as needed
        String keystorePath = "mykeystore.jks";
        String keystorePassword = "changeit";

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
            System.setProperty("javax.net.debug", "ssl");

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

        System.out.println("All tests done!");
    }
}
