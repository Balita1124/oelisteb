/**
 * @author Rico Fauchard
 */

import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ClientEditComponent} from "./client-edit/client-edit.component";
import {ClientAddComponent} from "./client-add/client-add.component";
import {ClientListComponent} from "./client-list/client-list.component";


const clientRoutes: Routes = [
  {path: 'clients', component: ClientListComponent},
  {path: 'clients/add', component: ClientAddComponent},
  {
    path: 'clients/:id',
    children: [
      {
        path: 'edit',
        component: ClientEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(clientRoutes)],
  exports: [RouterModule]
})

export class ClientsRoutingModule {
}
