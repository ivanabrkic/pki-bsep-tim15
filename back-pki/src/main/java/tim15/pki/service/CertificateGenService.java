package tim15.pki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateGenDTO;
import tim15.pki.dto.TextMessage;
import tim15.pki.model.Extension;
import tim15.pki.repository.CertificateRepository;
import tim15.pki.repository.ExtensionRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.List;

@Service
public class CertificateGenService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ExtensionRepository extensionRepository;

    @Autowired
    private LoggerService loggerService;

    /// OBAVEZNO SAMO SA STATUSOM AKTIVNI I VALIDNI ------------- DODAJ!!!!!!!!!!!!!!!!!!
    public List<String> getAllCAs(){
        return certificateRepository.findByIsCA(true);
    }

    public List<Extension> getAllExtensions(){
        return extensionRepository.findAll();
    }

    public TextMessage generateCertificate(CertificateGenDTO certificateGenDTO) {

//        try {
//
//            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
//            builder = builder.setProvider("BC");

///           RESI GET ISSUER DATA - POVUCI ISSUERA IZ KEYSTORA :)
///            ContentSigner contentSigner = builder.build(certificateGenDTO.getIssuerData().getPrivateKey());

///         RESI SUBJECT X500NAME + POTREBNE METODE ZA GENERISANJE OBJEKATA KLASE ISSUER DATA I SUBJECT DATA

//            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(certificateGenDTO.getIssuerData().getName(),
//                    new BigInteger(certificateGenDTO.getSerialNumber()),
//                    certificateGenDTO.getStartDate(),
//                    certificateGenDTO.getEndDate(),
//                    certificateGenDTO.getName(),
//                    certificateGenDTO.getSubjectData().getPublicKey());

//            X509CertificateHolder certHolder = certGen.build(contentSigner);
//            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
//            certConverter = certConverter.setProvider("BC");
//
//      //////// DODATI KOD ZA DODAVANJE EKSTENZIJA ---- https://github.com/DanijelRadakovic/BSEP/blob/master/src/main/java/megatravel/com/pki/service/CertificateService.java
//
//            X509Certificate certificate = certConverter.getCertificate(certHolder);

//            loggerService.print("Certificate successfully generated.");
//            return new TextMessage("Certificate successfully generated.");
//
//        } catch (CertificateEncodingException e) {
//            loggerService.print(e.getMessage());
//        } catch (IllegalArgumentException e) {
//            loggerService.print(e.getMessage());
//        } catch (IllegalStateException e) {
//            loggerService.print(e.getMessage());
//        } catch (OperatorCreationException e) {
//            loggerService.print(e.getMessage());
//        } catch (CertificateException e) {
//            loggerService.print(e.getMessage());
//        } catch (Exception e) {
//            loggerService.print(e.getMessage());
//        }

        loggerService.print("Certificate failed to generate.");
        return new TextMessage("Certificate failed to generate.");
    }

    //////// IMPLEMENTIRATI CUVANJE CHAIN-A U KEYSTORE
    //////// SETOVATI PASSWORD ZA KEY STORE
    //////// DVA ODVOJENA KEY STORE-a
    public TextMessage saveKeyStore(CertificateGenDTO certificateGenDTO, String fileName, char[] password) {

        java.security.cert.Certificate certificate = null;
        String alias = null;

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            if(fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                keyStore.load(null, password);
            }
            /// RESI GET ISSUER DATA - POVUCI ISSUERA IZ KEYSTORA :)
//            keyStore.setKeyEntry(alias, certificateGenDTO.getIssuerData().getPrivateKey(), password, new Certificate[] {certificate});
            keyStore.store(new FileOutputStream(fileName), password);
            loggerService.print("Certificate successfully saved.");
            return new TextMessage("Certificate successfully saved.");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            loggerService.print(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            loggerService.print(e.getMessage());
        } catch (CertificateException e) {
            loggerService.print(e.getMessage());
        } catch (FileNotFoundException e) {
            loggerService.print(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        loggerService.print("Certificate failed to save.");
        return new TextMessage("Certificate failed to save.");
    }

}
