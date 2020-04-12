import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { TextMessage } from '../../pki-model-dto/backend-dtos/text-message';
import { CertificateGenDTO } from '../../pki-model-dto/backend-dtos/certificate-gen-DTO';
import { Extension } from '../../pki-model-dto/backend-model/extension';
import { Certificate } from '../../pki-model-dto/backend-model/certificate';

const httpOptions = {headers: new HttpHeaders({'Content-Type' : 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class CertFormService {

  constructor(private http: HttpClient) { }

  public createCertificate(certificate: CertificateGenDTO) {
    return this.http.post<TextMessage>('/server/certificate_gen/create' , JSON.stringify(certificate), httpOptions);
  }

  public getAllCAs() {
    return this.http.get<Certificate[]>('/server/certificate_gen/getCAs' , httpOptions);
  }

  public getAllExtensions() {
    return this.http.get<Extension[]>('/server/certificate_gen/getExtensions' , httpOptions);
  }
}
