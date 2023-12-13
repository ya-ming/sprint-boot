package io.javabrains.SpringBootSecurity.UploadService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

//@Controller
public class UploadController {

    @GetMapping("/upload")
    public String fileUploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("privateKeyFile") MultipartFile privateKeyFile,
                         @RequestParam("certificateFile") MultipartFile certificateFile) throws IOException {
        // Validate file formats
        if (!isPrivateKeyFile(privateKeyFile)) {
            return "Invalid private key format";
        }
        if (!isCertificateFile(certificateFile)) {
            return "Invalid certificate format";
        }

        // Store the files securely
        String privateKeyPath = storeFile(privateKeyFile);
        String certificatePath = storeFile(certificateFile);

        // Save file paths to database or configuration
        saveFilePaths(privateKeyPath, certificatePath);

        return "Upload successful";
    }

    private boolean isPrivateKeyFile(MultipartFile file) {
        // Check file extension or MIME type
        return file.getOriginalFilename().endsWith(".pem") || file.getContentType().startsWith("application/x-pem-file");
    }

    private boolean isCertificateFile(MultipartFile file) {
        // Check file extension or MIME type
        return file.getOriginalFilename().endsWith(".crt") || file.getContentType().startsWith("application/pkix-cert");
    }

    public boolean validateCertificateMatchesPrivateKey(String certificatePath, String privateKeyPath) throws Exception {
        // Register BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());

        // Load the certificate
        X509Certificate certificate = loadCertificate(certificatePath);

        // Extract the public key from the certificate
        PublicKey publicKey = certificate.getPublicKey();

        // Load the private key
        PrivateKey privateKey = loadPrivateKey(privateKeyPath);

        // Convert the certificate signature to a byte array
        byte[] signatureBytes = certificate.getSignature();

        // Verify the certificate signature using the private key
//        try {
//            privateKey.verify(signatureBytes, "SHA256withRSA");
//            return true;
//        } catch (SignatureException e) {
//            return false;
//        }
        return true;
    }


    private X509Certificate loadCertificate(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) certificateFactory.generateCertificate(fis);
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
//        FileInputStream fis = new FileInputStream(path);
//        PEMParser pemParser = new PEMParser(fis);
//        PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
//        KeyFactory factory = KeyFactory.getInstance("RSA");
//        return factory.generatePrivate(keySpec);
        return null;
    }

    private String storeFile(MultipartFile file) throws IOException {
        // Implement secure file storage
        String fileName = UUID.randomUUID().toString();
        String filePath = "/path/to/secure/location/" + fileName;
        file.transferTo(new File(filePath));
        return filePath;
    }

    private void saveFilePaths(String privateKeyPath, String certificatePath) {
        // Implement storage in database or configuration file
    }
}
