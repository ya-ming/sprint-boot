package io.javabrains.SpringBootSecurity.UploadService;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Controller
public class FileUploadController {

    @GetMapping("/upload")
    public String fileUploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(
            @RequestPart("certificate") MultipartFile certificateFile,
            @RequestPart("privateKey") MultipartFile privateKeyFile,
            Model model) {
        try {
            // Validate and process the uploaded files
            if (isValidCertificate(certificateFile) && isValidPrivateKey(privateKeyFile)) {
                saveFile("keypair/uploaded", "certificate.pem", certificateFile);
                saveFile("keypair/uploaded", "private_key.key", privateKeyFile);

                model.addAttribute("message", "Upload successful!");
            } else {
                model.addAttribute("message", "Invalid certificate or private key files.");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Error handling file upload: " + e.getMessage());
        }

        return "upload";
    }

    private boolean isValidCertificate(MultipartFile certificateFile) throws IOException {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(new String(certificateFile.getBytes())));
            X509CertificateHolder certificateHolder = (X509CertificateHolder) pemParser.readObject();
            Certificate certificate = new JcaX509CertificateConverter().getCertificate(certificateHolder);
            // Add additional validation logic if needed
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidPrivateKey(MultipartFile privateKeyFile) throws IOException {
        // Add validation logic for private key if needed
        return isValidPrivateKey(privateKeyFile.getBytes());
    }

    private boolean isValidPrivateKey(byte[] privateKeyBytes) {
        try {
            PEMParser pemParser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(privateKeyBytes)));
            Object object = pemParser.readObject();

            if (object instanceof PrivateKeyInfo) {
                PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) object;
//                JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//                KeyPair keyPair = converter.getKeyPair(pemKeyPair);

                // Optionally, you can perform additional checks on the private key here
                // For example, check if the key is of sufficient length, algorithm, etc.

                // Assuming any private key is considered valid for this example
                return true;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean doKeysMatch(byte[] privateKeyBytes, byte[] certificateBytes) {
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

    private void saveFile(String directory, String fileName, MultipartFile file) throws IOException {
        File uploadDir = new File("uploads/" + directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destFile = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        file.transferTo(destFile);
    }
}
