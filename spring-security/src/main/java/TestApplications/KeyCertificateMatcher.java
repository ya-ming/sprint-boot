package TestApplications;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class KeyCertificateMatcher {

    static {
        // Add Bouncy Castle as a security provider
        Security.addProvider(new BouncyCastleProvider());
    }

    public static boolean doKeysMatch(byte[] privateKeyBytes, byte[] certificateBytes) {
        try {
            // Parse the private key
            PEMParser privateKeyParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(privateKeyBytes)));
            Object privateKeyObject = privateKeyParser.readObject();
            if (!(privateKeyObject instanceof PEMKeyPair)) {
                return false; // Not a valid private key
            }
            PEMKeyPair pemKeyPair = (PEMKeyPair) privateKeyObject;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            KeyPair keyPair = converter.getKeyPair(pemKeyPair);

            // Parse the certificate
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory
                    .generateCertificate(new ByteArrayInputStream(certificateBytes));

            // Compare public keys
            PublicKey certificatePublicKey = certificate.getPublicKey();
            PublicKey privateKeyPublicKey = keyPair.getPublic();

            return certificatePublicKey.equals(privateKeyPublicKey);
        } catch (IOException | java.security.cert.CertificateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            // Load private key from file
            Path privateKeyPath = Paths.get("path/to/private-key.pem");
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);

            // Load certificate from file
            Path certificatePath = Paths.get("path/to/certificate.pem");
            byte[] certificateBytes = Files.readAllBytes(certificatePath);

            boolean keysMatch = doKeysMatch(privateKeyBytes, certificateBytes);
            System.out.println("Do Keys Match: " + keysMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

