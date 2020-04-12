package tim15.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim15.pki.dto.CertificateGenDTO;
import tim15.pki.dto.TextMessage;
import tim15.pki.model.Extension;
import tim15.pki.service.CertificateGenService;
import tim15.pki.service.LoggerService;

import java.util.List;

@RestController
@RequestMapping("certificate_gen")
public class CertificateGenController {

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private CertificateGenService certificateGenService;

    /**
     * POST /server/create
     *
     * @return string which indicates status of operation creating certificate
     */
    @PostMapping(value = "/create", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextMessage> create(@RequestBody CertificateGenDTO certificateGenDTO) throws Exception {
        loggerService.print("action = create certificate, status = success");
        TextMessage tm = certificateGenService.generateCertificate(certificateGenDTO);
        return new ResponseEntity<>(tm, HttpStatus.OK);
    }

    /**
     * GET /server/getCAs
     *
     * @return all CA certificates
     */
    @GetMapping(value = "/getCAs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCAs() throws Exception{
        loggerService.print("action = getCAs, status = success");
        List<String> cas = certificateGenService.getAllCAs();
        return new ResponseEntity<>(cas, HttpStatus.OK);
    }

    /**
     * GET /server/getExtensions
     *
     * @return all extensions
     */
    @GetMapping(value = "/getExtensions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Extension>> getExtensions() throws Exception{
        loggerService.print("action = getExtensions, status = success");
        List<Extension> extensions = certificateGenService.getAllExtensions();
        return new ResponseEntity<>(extensions, HttpStatus.OK);
    }

}
