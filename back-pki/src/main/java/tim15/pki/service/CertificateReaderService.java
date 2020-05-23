package tim15.pki.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;
import tim15.pki.model.IssuerData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



@Service
public class CertificateReaderService {

    private Collection<Certificate> readFromBase64EncFile(String fileAdress) {
        try {
            FileInputStream fis = new FileInputStream(fileAdress);
            BufferedInputStream bis = new BufferedInputStream(fis);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //Cita sertifikat po sertifikat
            //Svaki certifikat je izmedju
            //-----BEGIN CERTIFICATE-----,
            //i
            //-----END CERTIFICATE-----.
            Collection<Certificate> certificatesFromFile = new ArrayList<Certificate>();

            while (bis.available() > 0) {
                Certificate cert = cf.generateCertificate(bis);
                certificatesFromFile.add(cert);
            }
            return certificatesFromFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    private Collection<Certificate> readFromBinEncFile(String fileAdress) {
        try {
            FileInputStream fis = new FileInputStream(fileAdress);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            //Ovde se vade svi sertifkati
            Collection c = cf.generateCertificates(fis);
            Iterator i = c.iterator();

            Collection<Certificate> certificatesFromFile = new ArrayList<Certificate>();

            while (i.hasNext()) {
                Certificate cert = (Certificate)i.next();
                certificatesFromFile.add(cert);
            }
            return certificatesFromFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }

    }

    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keypass) {
        try {
            //Datoteka se ucitava
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            //Iscitava se sertifikat koji ima dati alias
            Certificate cert = keyStore.getCertificate(alias);
            //Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keypass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getIssuer();
            return new IssuerData(issuerName, privKey);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("PKCS12");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                Certificate cert = ks.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Certificate[] readChain(String keyStoreFile, String keyStorePass, String alias) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("PKCS12");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                Certificate[] cert = ks.getCertificateChain(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("PKCS12");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

}
