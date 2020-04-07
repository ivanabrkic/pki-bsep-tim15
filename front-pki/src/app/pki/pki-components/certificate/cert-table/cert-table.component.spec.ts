import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertTableComponent } from './cert-table.component';

describe('CertTableComponent', () => {
  let component: CertTableComponent;
  let fixture: ComponentFixture<CertTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
