import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpKhSharedModule } from 'app/shared/shared.module';
import { AlbumsComponent } from './albums.component';
import { AlbumsDetailComponent } from './albums-detail.component';
import { AlbumsUpdateComponent } from './albums-update.component';
import { AlbumsDeleteDialogComponent } from './albums-delete-dialog.component';
import { albumsRoute } from './albums.route';

@NgModule({
  imports: [TpKhSharedModule, RouterModule.forChild(albumsRoute)],
  declarations: [AlbumsComponent, AlbumsDetailComponent, AlbumsUpdateComponent, AlbumsDeleteDialogComponent],
  entryComponents: [AlbumsDeleteDialogComponent],
})
export class TpKhAlbumsModule {}
