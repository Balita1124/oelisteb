import {Component, OnInit} from '@angular/core';
import {Client} from "../client";
import {ClientService} from "../client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  client: Client;
  errorMessage: string;

  constructor(private clientService: ClientService, private router: Router) {
    this.client = {} as Client;
  }

  ngOnInit() {
  }

  onSubmit(client: Client) {
    client.id = null;
    this.clientService.addClient(client).subscribe(
      client => {
        this.client = client;
        this.goToClientList();
      },
      error => this.errorMessage = error as any
    );
  }

  goToClientList(){
    this.router.navigate(['/clients']);
  }

}
