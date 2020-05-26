import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { getCertificatesDTO } from '../pki-model-dto/backend-dtos/get-certificates-DTO';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class ViewCertificateService {
  private requestUrl: string;

  constructor(private httpClient: HttpClient) { }

  public getCertificates(getCertificatesDTO: getCertificatesDTO) {
    this.requestUrl = '/server/api/certificate';
    const optionsAndParams = {
      headers: { 'Content-Type': 'application/json' }
  };
    return this.httpClient.post(this.requestUrl + '/all', JSON.stringify(getCertificatesDTO), optionsAndParams);
}

public getDetails(serialNumber: string) {
  let params = new HttpParams();
  params = params.append('serialNumber', serialNumber);
  this.requestUrl = 'server/api/certificate';
  const optionsAndParams = {
    headers: { 'Content-Type': 'application/json' },
    params: params
};
return this.httpClient.get(this.requestUrl + '/certificateDetails', optionsAndParams);
}

public revoke(serialNumber: string, revokeReason: string) {
  let params = new HttpParams();
  params = params.append('serialNumber', serialNumber);
  params = params.append('revokeReason', revokeReason);
  this.requestUrl = 'server/certificate_revoke';
  const optionsAndParams = {
    headers: {  'Content-Type': 'application/json' },
    params: params
  };
return this.httpClient.get(this.requestUrl + '/revoke', optionsAndParams);
}

public download(serialNumber: string) {
  let params = new HttpParams();
  params = params.append('serialNumber', serialNumber);
  this.requestUrl = 'server/api/certificate';
  const optionsAndParams = {
    headers: { 'Content-Type': 'application/json' },
    params: params,
};
return this.httpClient.get(this.requestUrl + '/download', optionsAndParams);
}


}
