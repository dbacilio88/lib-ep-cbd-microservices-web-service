package pe.mil.microservices.components.helpers;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.ResourceUtils;
import pe.mil.microservices.components.exceptions.SoapBusinessProcessException;
import pe.mil.microservices.utils.components.enums.ResponseCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Log4j2
public class SoapKeyStoreHelper {

    public static KeyStore createKeyStore(final String keyStorePath, final String keyStorePassword) {

        try (InputStream inputStreamTrustStore = new FileInputStream(ResourceUtils.getFile(keyStorePath))) {
            final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(inputStreamTrustStore, keyStorePassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            log.error("error in process createKeyStore, KeyStoreException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in process createKeyStore, KeyStoreException", ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (NoSuchAlgorithmException e) {
            log.error("error in process createKeyStore, NoSuchAlgorithmException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in process createKeyStore, NoSuchAlgorithmException", ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (CertificateException e) {
            log.error("error in process createKeyStore, CertificateException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in process createKeyStore, CertificateException", ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("error in process createKeyStore, IOException, error: {}", e.getMessage());
            throw new SoapBusinessProcessException("error in process createKeyStore, IOException", ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("error in process createKeyStore, Exception error: {}", e.getMessage());
            throw new SoapBusinessProcessException(e.getMessage());
        }
    }
}
