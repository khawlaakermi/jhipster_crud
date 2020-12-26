import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlbums } from 'app/shared/model/albums.model';
import { AlbumsService } from './albums.service';

@Component({
  templateUrl: './albums-delete-dialog.component.html',
})
export class AlbumsDeleteDialogComponent {
  albums?: IAlbums;

  constructor(protected albumsService: AlbumsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.albumsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('albumsListModification');
      this.activeModal.close();
    });
  }
}
