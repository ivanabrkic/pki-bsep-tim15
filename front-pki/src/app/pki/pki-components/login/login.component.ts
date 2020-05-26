import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from '../../pki-security/AuthService';
import {Router} from "@angular/router";
import {Creds} from "../../pki-security/Creds";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  user = new Creds;
  error = '';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['certificate']);
    }
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit() {
    if(this.form.invalid) {
      return;
    }
    this.user.username = this.form.controls.username.value;
    this.user.password = this.form.controls.password.value;
    this.authService.login(this.user)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate(['certificates']);
        },
        error => {
          this.error = error;
        }
      );
  }
}
