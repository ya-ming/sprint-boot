package TestApplications;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
            if (!(privateKeyObject instanceof PrivateKeyInfo)) {
                return false; // Not a valid private key
            }
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) privateKeyObject;

            privateKeyParser.close();

            // Convert PrivateKeyInfo to PrivateKey
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // Use the appropriate algorithm (RSA, EC, etc.)
//            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
//            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Extract RSA-specific components
//            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            // Convert PrivateKeyInfo to PrivateKey

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);


            // Parse the certificate
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory
                    .generateCertificate(new ByteArrayInputStream(certificateBytes));

            // Compare public keys
            PublicKey publicKey = certificate.getPublicKey();

            // Verify if the public key matches the private key
            boolean keyPairMatch = verifyKeyPair(privateKey, publicKey);

            System.out.println("Key pair match: " + keyPairMatch);

            return keyPairMatch;
        } catch (IOException | java.security.cert.CertificateException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean verifyKeyPair(PrivateKey privateKey, PublicKey publicKey) {
        // Perform key matching logic here
        // For example, for RSA keys, you can compare the modulus
        String privateKeyAlgorithm = privateKey.getAlgorithm();
        String publicKeyAlgorithm = publicKey.getAlgorithm();
        System.out.println(privateKeyAlgorithm);
        System.out.println(publicKeyAlgorithm);

        System.out.println(privateKey.getFormat());
        System.out.println(publicKey.getFormat());

        byte[] data = {42};
        try {
            Signature sig = Signature.getInstance(privateKeyAlgorithm);
            sig.initSign(privateKey);
            sig.update(data);
            byte[] signature = sig.sign();
            Signature ver = Signature.getInstance(publicKeyAlgorithm);
            ver.initVerify(publicKey);
            ver.update(data);
            return ver.verify(signature);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

//        return privateKey.getAlgorithm().equals(publicKey.getAlgorithm())
//                && privateKey.getFormat().equals(publicKey.getFormat())
//                && java.util.Arrays.equals(privateKey.getEncoded(), publicKey.getEncoded());
    }

    public static void main(String[] args) {
        try {
            // Load private key from file
            Path privateKeyPath = Paths.get("uploads/keypair/uploaded/private_key.key");
            byte[] privateKeyBytes = Files.readAllBytes(privateKeyPath);

            // Load certificate from file
            Path certificatePath = Paths.get("uploads/keypair/uploaded/certificate.pem");
            byte[] certificateBytes = Files.readAllBytes(certificatePath);

            boolean keysMatch = doKeysMatch(privateKeyBytes, certificateBytes);
            System.out.println("Do Keys Match: " + keysMatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}