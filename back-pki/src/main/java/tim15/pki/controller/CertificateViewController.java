package tim15.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tim15.pki.dto.CertificateDetailsDTO;
import tim15.pki.dto.CertificateViewDTO;
import tim15.pki.dto.TextMessage;
import tim15.pki.model.Certificate;
import tim15.pki.service.CertificateReaderService;
import tim15.pki.service.CertificateViewService;

import java.util.List;

@RestController
@RequestMapping(value ="/api/certificate")
public class CertificateViewController {

    @Autowired
    private CertificateReaderService certificateReaderService;

    @Autowired
    private CertificateViewService certificateViewService;


    @GetMapping(value = "/all")
    public ResponseEntity<List<CertificateViewDTO>> getCertificates(@RequestParam(value = "role", required = true) String role,
                                                                    @RequestParam(value = "keyStorePassword", required = true) String keyStorePassword) throws Exception {

        return new ResponseEntity<>(certificateViewService.convertCertificatesToDTOList(certificateViewService.getCertificates(role, keyStorePassword)), HttpStatus.OK);
    }

    @GetMapping(value = "/certificateDetails")
    public ResponseEntity<CertificateDetailsDTO> getCertificateDetails(@RequestParam(value = "serialNumber", required = true) String serialNumber) throws Exception {

        return new ResponseEntity<>(certificateViewService.getDetails(serialNumber), HttpStatus.OK);
    }

    @GetMapping(value = "/download")
    public ResponseEntity<TextMessage> download(@RequestParam(value = "serialNumber", required = true) String serialNumber) throws Exception {
        TextMessage tm = new TextMessage();
        tm.setText(certificateViewService.download(serialNumber));
        return new ResponseEntity<>(tm, HttpStatus.OK);
    }

}
