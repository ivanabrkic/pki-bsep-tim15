import { TestBed } from '@angular/core/testing';

import { ViewCertificateService } from './view-certificate.service';

describe('ViewCertificateService', () => {
  let service: ViewCertificateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewCertificateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
