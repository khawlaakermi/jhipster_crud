import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlbums } from 'app/shared/model/albums.model';
import { AlbumsService } from './albums.service';
import { AlbumsDeleteDialogComponent } from './albums-delete-dialog.component';

@Component({
  selector: 'jhi-albums',
  templateUrl: './albums.component.html',
})
export class AlbumsComponent implements OnInit, OnDestroy {
  albums?: IAlbums[];
  eventSubscriber?: Subscription;

  constructor(protected albumsService: AlbumsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.albumsService.query().subscribe((res: HttpResponse<IAlbums[]>) => (this.albums = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlbums();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlbums): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlbums(): void {
    this.eventSubscriber = this.eventManager.subscribe('albumsListModification', () => this.loadAll());
  }

  delete(albums: IAlbums): void {
    const modalRef = this.modalService.open(AlbumsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.albums = albums;
  }
}
