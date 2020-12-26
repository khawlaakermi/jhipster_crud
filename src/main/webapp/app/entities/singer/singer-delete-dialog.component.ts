import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISinger } from 'app/shared/model/singer.model';
import { SingerService } from './singer.service';

@Component({
  templateUrl: './singer-delete-dialog.component.html',
})
export class SingerDeleteDialogComponent {
  singer?: ISinger;

  constructor(protected singerService: SingerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.singerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('singerListModification');
      this.activeModal.close();
    });
  }
}
