import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TpKhSharedModule } from 'app/shared/shared.module';
import { SingerComponent } from './singer.component';
import { SingerDetailComponent } from './singer-detail.component';
import { SingerUpdateComponent } from './singer-update.component';
import { SingerDeleteDialogComponent } from './singer-delete-dialog.component';
import { singerRoute } from './singer.route';

@NgModule({
  imports: [TpKhSharedModule, RouterModule.forChild(singerRoute)],
  declarations: [SingerComponent, SingerDetailComponent, SingerUpdateComponent, SingerDeleteDialogComponent],
  entryComponents: [SingerDeleteDialogComponent],
})
export class TpKhSingerModule {}
