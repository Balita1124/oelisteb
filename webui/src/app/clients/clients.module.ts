import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientListComponent } from './client-list/client-list.component';
import { ClientAddComponent } from './client-add/client-add.component';
import { ClientEditComponent } from './client-edit/client-edit.component';
import {FormsModule} from "@angular/forms";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatMomentDateModule, MomentDateAdapter} from "@angular/material-moment-adapter";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {MY_DATE_FORMATS} from "../pets/pets.module";
import {ClientsRoutingModule} from "./clients-routing.module";
import {ClientService} from "./client.service";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatDatepickerModule,
    MatMomentDateModule,
    ClientsRoutingModule
  ],
  declarations: [
    ClientListComponent,
    ClientEditComponent,
    ClientAddComponent
  ],
  exports: [
    ClientListComponent,
    ClientEditComponent,
    ClientAddComponent
  ],
  providers: [
    ClientService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS}
  ]
})
export class ClientsModule { }
