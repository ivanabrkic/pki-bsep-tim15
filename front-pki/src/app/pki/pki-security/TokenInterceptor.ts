import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AuthService} from './AuthService';
import {Observable, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";
import {any} from "codelyzer/util/function";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if(this.authService.getToken()) {
        request = this.addToken(request, this.authService.getToken());
      }
    // @ts-ignore
    return next.handle(request);
  }

  private addToken(request: HttpRequest<any>, token: string) {
    const header = 'Bearer ' + token;
    return request.clone({
      setHeaders: {
        Authorization: header
      }
    });
  }
}
