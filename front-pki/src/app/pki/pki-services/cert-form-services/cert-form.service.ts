import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { NewCertificate } from '../pki-model/new-cert';
import { Extension } from '../pki-model/extension';

const httpOptions = {headers: new HttpHeaders({'Content-Type' : 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class CertFormService {

  constructor(private http: HttpClient) { }

  public createCertificate(certificate: NewCertificate) {
    return this.http.post<NewCertificate>('/server/certificate_gen/create' , JSON.stringify(certificate), httpOptions);
  }

  public getAllCAs() {
    return this.http.get<String[]>('/server/certificate_gen/getCAs' , httpOptions);
  }

  public getAllExtensions() {
    return this.http.get<Extension[]>('/server/certificate_gen/getExtensions' , httpOptions);
  }
}
