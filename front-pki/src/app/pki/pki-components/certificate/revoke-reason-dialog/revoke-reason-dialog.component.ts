import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CertificateViewDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateViewDTO';

@Component({
  selector: 'app-revoke-reason-dialog',
  templateUrl: './revoke-reason-dialog.component.html',
  styleUrls: ['./revoke-reason-dialog.component.scss']
})


export class RevokeReasonDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<RevokeReasonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CertificateViewDTO) { }


  selected  = "NOT REVOKED"

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOkClick(): void {
    this.dialogRef.close(this.selected);
  }
}
