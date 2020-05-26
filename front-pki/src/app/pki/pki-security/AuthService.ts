import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {catchError, mapTo, tap} from 'rxjs/operators';
import {Creds} from "./Creds";

const httpOptions = {headers: new HttpHeaders({'Content-Type' : 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly  JWT = 'JWT';
  private loggedUser: string;

  constructor(private http: HttpClient) {}


  isLoggedIn() {
    return this.getToken();
  }


  login(user: Creds): Observable<boolean> {
    return this.http.post<any>('/server/user/login', JSON.stringify(user), httpOptions)
      .pipe(
        tap(token => this.doLoginUser(user.username, token.jwt)),
        mapTo(true),
        catchError(error => {
          alert("Error!");
          return of(false);
        })
      );
  }

  logout() {
    this.doLogoutUser();
  }

  private doLoginUser(username: string, token: string) {
    this.loggedUser = username;
    this.storeToken(token);
  }

  private doLogoutUser() {
    this.loggedUser = null;
    this.removeToken();
  }

  getToken() {
    return localStorage.getItem(this.JWT);
  }

  private storeToken(token: string) {
    localStorage.setItem(this.JWT, token);
  }
  private removeToken() {
    localStorage.removeItem(this.JWT);
  }
}
