import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RevokeReasonDialogComponent } from './revoke-reason-dialog.component';

describe('RevokeReasonDialogComponent', () => {
  let component: RevokeReasonDialogComponent;
  let fixture: ComponentFixture<RevokeReasonDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RevokeReasonDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RevokeReasonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
