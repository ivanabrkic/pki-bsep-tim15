package tim15.pki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tim15.pki.service.CertificateGenService;

@SpringBootApplication
public class PkiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PkiApplication.class, args);
		context.getBean(CertificateGenService.class).generateTestDataCertificates();
	}

}
