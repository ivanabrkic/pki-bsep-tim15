import { Component, OnInit, Inject } from '@angular/core';
import { CertificateDetailsDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateDetailsDTO';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import {MatTabsModule} from '@angular/material/tabs';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.scss']
})
export class CertificateDetailsComponent implements OnInit {

  constructor(
    @Inject(MAT_DIALOG_DATA) public cert: CertificateDetailsDTO
  ) { }

  ngOnInit(): void {
  }

}
