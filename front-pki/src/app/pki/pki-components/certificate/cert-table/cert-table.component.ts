import { Component, OnInit } from '@angular/core';
import {MatSelectModule} from '@angular/material/select';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ViewCertificateService } from 'src/app/pki/pki-services/view-certificate.service';
import { CertificateViewDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateViewDTO';
import { CertificateDetailsDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateDetailsDTO';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details/certificate-details.component';
import { TextMessage } from 'src/app/pki/pki-model-dto/backend-dtos/text-message';
import { MatSnackBar } from '@angular/material/snack-bar';


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
  tm: TextMessage;
  arrayBuffer: ArrayBuffer;
  
  constructor(private formBuilder: FormBuilder,
    private viewCertificateService: ViewCertificateService, 
    public dialog: MatDialog,
    private _snackBar: MatSnackBar) {
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
          this._snackBar.open("Error, no certificates found!", "", {
            duration: 2000,
          });
        }
      }
    );
  }

  viewDetails(serialNumber: string) {
    this.viewCertificateService.getDetails(serialNumber).subscribe(
      (data: CertificateDetailsDTO) => {
        this.certificateDetails = data;
        this.dialog.open(CertificateDetailsComponent, {data : this.certificateDetails,
          maxHeight: '90vh'})
      }
    )
  }

 download(SerialNumber: string) {
   this.viewCertificateService.download(SerialNumber).subscribe(
    (data: TextMessage) => {
      this._snackBar.open("Download will start soon!", "", {
        duration: 2000,
      });
      this.tm = data;
        //let link = document.createElement('a');
        //link.setAttribute('type', 'hidden');
        //link.href = this.tm.text.toString();

        //console.log(this.tm.text.toString());
        //let array = this.tm.text.toString().split("/");
        
        //link.download = (array[array.length-1]).toString();
        //console.log(array[array.length - 1]);
        //document.body.appendChild(link);
        //link.click();
        //link.remove();
        const myBlob = new Blob([this.function_base64ToArrayBuffer(this.tm.arrayBuffer)], { type: 'application/octet-stream' });
        const blobUrl = window.URL.createObjectURL(myBlob);
        const link = document.createElement('a');
        link.href = blobUrl;
        link.download = 'certificate.cer';
        link.click();

     }
   )
 }

 revoke(SerialNumber: string) {
   this.viewCertificateService.revoke(SerialNumber).subscribe(
     (data: TextMessage) => {
      this._snackBar.open(data.text.toString(), "", {
        duration: 2000,
      });
     }
   )
 }

 function_base64ToArrayBuffer(base64:string) {
  var binary_string = window.atob(base64);
  var len = binary_string.length;
  var bytes = new Uint8Array(len);
  for (var i = 0; i < len; i++) {
      bytes[i] = binary_string.charCodeAt(i);
  }
  return bytes.buffer;
}

}
