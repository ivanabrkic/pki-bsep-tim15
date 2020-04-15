import { Component, OnInit } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ViewCertificateService } from 'src/app/pki/pki-services/view-certificate.service';
import { CertificateViewDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateViewDTO';
import { CertificateDetailsDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateDetailsDTO';

@Component({
  selector: 'app-cert-table',
  templateUrl: './cert-table.component.html',
  styleUrls: ['./cert-table.component.scss']
})
export class CertTableComponent implements OnInit {

  keyStoreForm: FormGroup;
  displayedColumns: string[] = ['serialNumber', 'subjectName', 'issuerName', 'validFrom', 'validTo', 'buttons'];
  certificatesDataSource: MatTableDataSource<CertificateViewDTO>;
  certificateDetails : CertificateDetailsDTO;
  
  constructor(public dialog: MatDialog,
    private formBuilder: FormBuilder,private viewCertificateService: ViewCertificateService) {
   }

  ngOnInit(): void {
    this.keyStoreForm = this.formBuilder.group({
      certRole: new FormControl(null, Validators.required),
      keyStorePassword: new FormControl(null, Validators.required)
    });
  }

  getCertificates() {
    this.viewCertificateService.getCertificates(this.keyStoreForm.value.certRole, this.keyStoreForm.value.keyStorePassword).subscribe(
      (data: CertificateViewDTO[]) => {
        this.certificatesDataSource = new MatTableDataSource(data)
        if (data.length == 0) {
          alert('Error, no certificates found!');
        }
      }
    );
  }

  viewDetails(serialNumber: string) {
    this.viewCertificateService.getDetails(serialNumber).subscribe(
      (data: CertificateDetailsDTO) => {
        this.certificateDetails = data;
      }
    )
  }

}
