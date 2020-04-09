import { TestBed } from '@angular/core/testing';

import { CertFormService } from './cert-form.service';

describe('CertFormService', () => {
  let service: CertFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CertFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
