package TestApplications;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class VerifyKeyPair {
    static {
        // Add Bouncy Castle as a security provider
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        try {
            // Read the private key in PEM format using FileInputStream
            FileInputStream privateKeyFileInputStream = new FileInputStream("uploads/keypair/uploaded/private_key.key");
            byte[] privateKeyBytes = privateKeyFileInputStream.readAllBytes();

            // Read the public key in PEM format using FileInputStream
            FileInputStream publicKeyFileInputStream = new FileInputStream("uploads/keypair/uploaded/certificate.pem");
            byte[] publicKeyBytes = publicKeyFileInputStream.readAllBytes();

            // Convert bytes to keys
            PEMParser privateKeyParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(privateKeyBytes)));
            Object privateKeyObject = privateKeyParser.readObject();
            if (!(privateKeyObject instanceof PrivateKeyInfo)) {
                return; // Not a valid private key
            }
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) privateKeyObject;
            privateKeyParser.close();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);

            // Parse the certificate
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory
                    .generateCertificate(new ByteArrayInputStream(publicKeyBytes));

            // Compare public keys
            PublicKey publicKey = certificate.getPublicKey();

            // Verify if the public key matches the private key
            boolean keyPairMatch = verifyKeyPair(privateKey, publicKey);

            System.out.println("Key pair match: " + keyPairMatch);
        } catch (Exception e) {
            e.printStackTrace();
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
}

