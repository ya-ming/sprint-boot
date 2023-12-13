package TestApplications;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;
import java.util.TreeSet;

public class PrintProviders {
    static {
        // Add Bouncy Castle as a security provider
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        TreeSet<String> algorithms = new TreeSet<>();

        Provider[] providers = Security.getProviders();
        System.out.println("-----Provider 列表如下：-----");
        for (Provider provider : providers) {
            System.out.println(provider.getName());
        }

        System.out.println("-----支持的签名算法如下：-----");

        for (Provider provider : providers) {
            for (Provider.Service service : provider.getServices())
                if (service.getType().equals("Signature")) {
                    algorithms.add(service.getAlgorithm());
                }
        }

        for (String algorithm : algorithms) {
            System.out.println(algorithm);
        }
    }
}
