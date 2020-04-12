import { Component, OnInit } from '@angular/core';
import { CertFormService } from 'src/app/pki/pki-services/cert-form-services/cert-form.service';
import { Certificate } from 'src/app/pki/pki-model-dto/backend-model/certificate';
import { Extension } from 'src/app/pki/pki-model-dto/backend-model/extension';

@Component({
  selector: 'app-cert-form',
  templateUrl: './cert-form.component.html',
  styleUrls: ['./cert-form.component.scss']
})
export class CertFormComponent implements OnInit {

  public validCAs: Array<Certificate> = [];
  public extensions: Array<Extension> = [];

  public selectedCA : Certificate;
  public selectedExtension : Extension;

  public displayedColumns: string[] = ['oid', 'name', 'value', 'isCritical'];

  public dataSource = null;
  public data = null;

  constructor(private certFormService : CertFormService) {
    this.certFormService.getAllCAs()
    .subscribe(cas => {
      this.validCAs = cas;
      this.certFormService.getAllExtensions()
      .subscribe(extensions => {
        this.extensions = extensions;
      });
    });
    this.selectedCA = this.validCAs[0];
    this.selectedExtension = this.extensions[0];
    this.dataSource = this.data;
  }

  ngOnInit(): void {
  }


}
