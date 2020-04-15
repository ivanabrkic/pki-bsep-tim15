package tim15.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tim15.pki.service.CertificateDownloadService;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.File;

@RestController
@RequestMapping("download")
public class CertificateDownloadController {
    @Autowired
    private CertificateDownloadService certificateDownloadService;

    /*@GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadCertificate(@QueryParam("serialNumber") String serialNumber) {
        File fileDownload = new File()
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=" + file);
        return response.build();
    }*/
}
