/**
 * @author Rico Fauhcard
 */

import {Component, Input, OnInit} from '@angular/core';
import {Client} from "../client";
import {Router} from "@angular/router";
import {ClientService} from "../client.service";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  errorMessage: string;
  clients: Client[];

  constructor(private router: Router, private clientService: ClientService) {
  }

  ngOnInit() {
    this.clientService.getClients().subscribe(
      clients => this.clients = clients,
      error => this.errorMessage = error as any
    );
  }

  onSelect(client: Client) {
    this.router.navigate(['/clients', client.id])
  }

  addClient() {
    this.router.navigate(['/clients/add']);
  }

}
