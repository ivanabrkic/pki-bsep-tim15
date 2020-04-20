import { Component, OnInit, ViewChild } from '@angular/core';
import { CertFormService } from 'src/app/pki/pki-services/cert-form-services/cert-form.service';
import { Extension } from 'src/app/pki/pki-model-dto/backend-model/extension';
import { FormControl, FormBuilder, FormGroup, Validators, NgForm, FormGroupDirective } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MatStepper } from '@angular/material/stepper';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Certificate } from 'src/app/pki/pki-model-dto/backend-model/certificate';
import { CertificateGenDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificate-gen-DTO';
import { X500NameCustom } from 'src/app/pki/pki-model-dto/backend-dtos/x500name-custom';
import { SystemEntity } from 'src/app/pki/pki-model-dto/backend-model/system-entity';
import { ViewCertificateService } from 'src/app/pki/pki-services/view-certificate.service';
import { CertificateDetailsDTO } from 'src/app/pki/pki-model-dto/backend-dtos/certificateDetailsDTO';
import { CertificateDetailsComponent } from '../certificate-details/certificate-details/certificate-details.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-cert-form',
  templateUrl: './cert-form.component.html',
  styleUrls: ['./cert-form.component.scss']
})
export class CertFormComponent implements OnInit {

  public validCAs: Array<Certificate> = [];

  public selectedCA: Certificate;

  public countryCodes = [];
  public countryCodesCitizenship = [];
  public countryCodesResidence = [];

  public countryCtrl = new FormControl();
  public countryCtrlCitizenship = new FormControl();
  public countryCtrlResidence = new FormControl();

  public filteredCountries: Observable<{ "Code", "Name" }[]>;
  public filteredCountriesCitizenship: Observable<{ "Code", "Name" }[]>;
  public filteredCountriesResidence: Observable<{ "Code", "Name" }[]>;

  subjectForm: FormGroup;
  submitted: boolean = false;
  loading: boolean = false;

  public certificate: CertificateGenDTO = new CertificateGenDTO();

  public certificateType = "Self-signed";
  @ViewChild('stepper') private myStepper: MatStepper;

  public countryInvalid = false;
  public countryEmpty = false;

  public userType = "Service";

  public maxBirthDate: Date = new Date();
  public minValidFromDate: Date = new Date();
  public minValidToDate: Date = new Date();


  public selectedValidFromDate: Date = new Date();
  public selectedValidToDate: Date = new Date();

  public systemUsers: SystemEntity[];
  public selectedUser: SystemEntity;

  public uidInfo = "noUID";

  public newCertificate = new CertificateGenDTO();

  certificateDetails : CertificateDetailsDTO;

  constructor(public dialog: MatDialog, private viewCertificateService : ViewCertificateService, private certFormService: CertFormService, private _snackBar: MatSnackBar, private formBuilder: FormBuilder) {
    this.generateData();
  }

  ngOnInit(): void {
    this.generateData();
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

  get f() { return this.subjectForm.controls; }

  private _filterCountries(value: string): { "Code", "Name" }[] {
    const filterValue = value.toString().toLowerCase();
    return this.countryCodes.filter(country => country.Name.toString().toLowerCase().indexOf(filterValue) === 0);
  }

  private _filterCountriesCitizenship(value: string): { "Code", "Name" }[] {
    const filterValue = value.toString().toLowerCase();
    return this.countryCodesCitizenship.filter(country => country.Name.toString().toLowerCase().indexOf(filterValue) === 0);
  }

  private _filterCountriesResidence(value: string): { "Code", "Name" }[] {
    const filterValue = value.toString().toLowerCase();
    return this.countryCodesResidence.filter(country => country.Name.toString().toLowerCase().indexOf(filterValue) === 0);
  }

  backIssuerInfo() {
    if (this.certificateType == "Self-signed") {
      this.myStepper.previous();
    }
    this.myStepper.previous();
  }

  onChange(newValue) {
    this.selectedCA = newValue;
  }

  onChangeUID(newValue) {
    this.selectedUser = newValue;
  }

  openIssuerInfo() {
    if (this.validCAs.length == 0 && this.certificateType != "Self-signed") {
      this._snackBar.open("Valid root certificate does not exist. You must first create self-signed certificate!", "", {
        duration: 2000,
      });
    }
    else {
      if (this.certificateType == "Self-signed") {
        this.myStepper.next();
      }
      this.myStepper.next();
    }
  }

  openValidityDate() {
    // Proveravam da li je validan kod za drzavu
    if (this.countryCtrl.value == undefined || this.countryCtrl.value == "") {
      this.countryEmpty = true;
    }
    else {
      this.countryEmpty = false;
    }

    var isValid = false;

    this.countryCodes.forEach(element => {
      if (element.Code == this.countryCtrl.value) {
        isValid = true;
      }
    });

    this.countryInvalid = !isValid;

    this.submitted = true;

    // Ako nije validan kod za drzavu i ostala polja return
    if (this.subjectForm.invalid || this.countryInvalid || this.countryEmpty) {
      return;
    }

    this.loading = true;

    this.newCertificate.x500NameCustom = this.subjectForm.value;
    this.newCertificate.x500NameCustom.countryCode = this.countryCtrl.value;
    this.newCertificate.x500NameCustom.countryOfResidence = this.countryCtrlResidence.value;
    this.newCertificate.x500NameCustom.countryOfCitizenship = this.countryCtrlCitizenship.value;

    if (this.uidInfo == "newUID") {
      this.newCertificate.x500NameCustom.uid = "newUID";
    }
    else if (this.uidInfo == "noUID") {
      this.newCertificate.x500NameCustom.uid = "";
    }

    if (this.certificateType == 'End-entity') {
      this.newCertificate.isCA = false;
      this.newCertificate.parentSerialNumber = this.selectedCA.serialNumber;
    }
    else {
      this.newCertificate.isCA = true;

      if (this.certificateType == 'Self-signed') {
        this.newCertificate.parentSerialNumber = "";
      }
      else {
        this.newCertificate.parentSerialNumber = this.selectedCA.serialNumber;
      }
    }

    this.newCertificate.serialNumber = "";

    this.newCertificate.entityType = this.userType;

    this.myStepper.next();  }

  openDone() {
    if (this.selectedValidFromDate.getTime() > this.selectedValidToDate.getTime()) {
      this._snackBar.open("Starting date must be after ending date!", "", {
        duration: 2000,
      });
      return;
    }
    this.newCertificate.startDate = this.selectedValidFromDate;
    this.newCertificate.endDate = this.selectedValidToDate;

    this.myStepper.next();
  }

  openCreateCertificate() {
    this.certFormService.createCertificate(this.newCertificate)
      .subscribe(message => {
        this._snackBar.open(message.text.toString(), "", {
          duration: 2000,
        });
        this.myStepper.reset();
        this.subjectForm.reset();
        this.certFormService.getAllCAs()
        .subscribe(cas => {
          this.certFormService.getAllUIDs()
            .subscribe(uids => {
              this.validCAs = cas;
              this.systemUsers = uids;
              this.selectedCA = this.validCAs[0];
              this.selectedUser = this.systemUsers[0];
          });
        });
      });
  }

  resetStepper() {
    this.subjectForm.reset();
  }

  generateData() {
    this.certFormService.getAllCAs()
      .subscribe(cas => {
        this.validCAs = cas;
        this.certFormService.getAllUIDs()
          .subscribe(uids => {
            this.systemUsers = uids;
            this.selectedCA = this.validCAs[0];
            this.selectedUser = this.systemUsers[0];
          });
      });

    this.filteredCountries = this.countryCtrl.valueChanges
      .pipe(
        startWith(''),
        map(country => country ? this._filterCountries(country) : this.countryCodes.slice())
      );

    this.filteredCountriesCitizenship = this.countryCtrlCitizenship.valueChanges
      .pipe(
        startWith(''),
        map(country => country ? this._filterCountriesCitizenship(country) : this.countryCodesCitizenship.slice())
      );

    this.filteredCountriesResidence = this.countryCtrlResidence.valueChanges
      .pipe(
        startWith(''),
        map(country => country ? this._filterCountriesResidence(country) : this.countryCodesResidence.slice())
      );


    this.subjectForm = this.formBuilder.group({
      commonName: ["", Validators.required],
      organization: ["", Validators.required],
      organizationalUnit: ["", Validators.required],
      localityCity: ["", Validators.required],
      stateProvince: ["", Validators.required],
      gender: [""],
      telephoneNumber: [""],
      generation: [""],
      placeOfBirth: [""],
      dateOfBirth: [""],
      email: [""],
      initials: [""],
      givenName: [""],
      surname: [""],
      name: [""],
      title: [""],
      serialNumber: [""],
      businessCategory: [""],
      postalCode: [""],
      streetAddress: [""]
    }, {
    });

    this.certificateType = "Self-signed";
    this.userType = "SERVICE";

    this.certFormService.getCountryCodes()
      .subscribe(codes => {
        this.countryCodes = codes;
        this.countryCodesCitizenship = codes;
        this.countryCodesResidence = codes;
      });

    this.countryCtrl.setValue("");
    this.countryCtrlCitizenship.setValue("");
    this.countryCtrlResidence.setValue("");

    this.selectedValidFromDate = new Date();
    this.selectedValidToDate = new Date();
    this.minValidFromDate = new Date();
    this.minValidToDate = new Date();
    this.maxBirthDate = new Date();

    this.uidInfo = "noUID";
  }
}
