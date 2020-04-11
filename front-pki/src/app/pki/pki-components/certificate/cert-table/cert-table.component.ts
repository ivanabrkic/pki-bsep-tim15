import { Component, OnInit } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cert-table',
  templateUrl: './cert-table.component.html',
  styleUrls: ['./cert-table.component.scss']
})
export class CertTableComponent implements OnInit {

  keyStoreForm: FormGroup;
  displayedColumns: string[] = ['serialNumber', 'subjectCN', 'issuerCN', 'validFrom', 'validTo', 'buttons'];
  certificatesDataSource: MatTableDataSource<null>;
  
  constructor(public dialog: MatDialog,
    private formBuilder: FormBuilder,) {
   }

  ngOnInit(): void {
    this.keyStoreForm = this.formBuilder.group({
      certRole: new FormControl(null, Validators.required),
      keyStorePassword: new FormControl(null, Validators.required)
    });
  }

}
