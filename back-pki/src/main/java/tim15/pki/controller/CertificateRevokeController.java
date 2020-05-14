package tim15.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tim15.pki.dto.TextMessage;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.service.CertificateRevokeService;
import tim15.pki.service.LoggerService;

@RestController
@RequestMapping("certificate_revoke")
public class CertificateRevokeController {
    @Autowired
    private CertificateRevokeService certificateRevokeService;

    @Autowired
    private LoggerService loggerService;

    /**
     * POST /server/certificate_revoke/revoke
     *
     * @return string which indicates status of operation revoking certificate
     */
    @GetMapping(value = "/revoke", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextMessage> revokeCertificate(@RequestParam(value = "serialNumber", required = true) String serialNumber) throws Exception {
        System.out.println("REVOKE REQUEST FOR SERIAL " + serialNumber);
        loggerService.print("Request: \"Revoke certificate\" received");
        TextMessage textMessage = certificateRevokeService.revokeCertificate(serialNumber, RevokeReason.CA_COMPROMISE);
        return new ResponseEntity<>(textMessage, HttpStatus.OK);
    }
}
