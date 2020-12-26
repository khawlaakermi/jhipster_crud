import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'singer',
        loadChildren: () => import('./singer/singer.module').then(m => m.TpKhSingerModule),
      },
      {
        path: 'albums',
        loadChildren: () => import('./albums/albums.module').then(m => m.TpKhAlbumsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TpKhEntityModule {}
