import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISinger } from 'app/shared/model/singer.model';
import { SingerService } from './singer.service';
import { SingerDeleteDialogComponent } from './singer-delete-dialog.component';

@Component({
  selector: 'jhi-singer',
  templateUrl: './singer.component.html',
})
export class SingerComponent implements OnInit, OnDestroy {
  singers?: ISinger[];
  eventSubscriber?: Subscription;

  constructor(protected singerService: SingerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.singerService.query().subscribe((res: HttpResponse<ISinger[]>) => (this.singers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSingers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISinger): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSingers(): void {
    this.eventSubscriber = this.eventManager.subscribe('singerListModification', () => this.loadAll());
  }

  delete(singer: ISinger): void {
    const modalRef = this.modalService.open(SingerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.singer = singer;
  }
}
