import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HandleError, HttpErrorHandler} from "../error.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import {Client} from "./client";

@Injectable()
export class ClientService {

  private entityUrl = environment.REST_API_URL + 'clients';

  private readonly handlerError: HandleError;

  constructor(private http: HttpClient, private httpErrorHandler: HttpErrorHandler) {
    this.handlerError = httpErrorHandler.createHandleError('ClientService');
  }

  getClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.entityUrl)
      .pipe(
        catchError(this.handlerError('getClients', []))
      );
  }

  getClientById(clientId: string): Observable<Client> {
    return this.http.get<Client>(this.entityUrl + '/' + clientId)
      .pipe(
        catchError(this.handlerError('getClientById', {} as Client))
      );
  }

  addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(this.entityUrl, client)
      .pipe(
        catchError(this.handlerError('addClient', client))
      );
  }

  updateClient(clientId: string, client: Client): Observable<Client> {
    return this.http.put<Client>(this.entityUrl + '/' + clientId, client)
      .pipe(
        catchError(this.handlerError('updateClient', client))
      );
  }

  deleteClient(clientId: string): Observable<number> {
    return this.http.delete<number>(this.entityUrl + '/' + clientId)
      .pipe(
        catchError(this.handlerError('deleteClient', 0))
      );
  }
}
