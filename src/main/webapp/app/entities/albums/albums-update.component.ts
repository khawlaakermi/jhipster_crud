import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlbums, Albums } from 'app/shared/model/albums.model';
import { AlbumsService } from './albums.service';
import { ISinger } from 'app/shared/model/singer.model';
import { SingerService } from 'app/entities/singer/singer.service';

@Component({
  selector: 'jhi-albums-update',
  templateUrl: './albums-update.component.html',
})
export class AlbumsUpdateComponent implements OnInit {
  isSaving = false;
  singers: ISinger[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    type: [],
    singerrId: [],
  });

  constructor(
    protected albumsService: AlbumsService,
    protected singerService: SingerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ albums }) => {
      this.updateForm(albums);

      this.singerService.query().subscribe((res: HttpResponse<ISinger[]>) => (this.singers = res.body || []));
    });
  }

  updateForm(albums: IAlbums): void {
    this.editForm.patchValue({
      id: albums.id,
      title: albums.title,
      type: albums.type,
      singerrId: albums.singerrId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const albums = this.createFromForm();
    if (albums.id !== undefined) {
      this.subscribeToSaveResponse(this.albumsService.update(albums));
    } else {
      this.subscribeToSaveResponse(this.albumsService.create(albums));
    }
  }

  private createFromForm(): IAlbums {
    return {
      ...new Albums(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      type: this.editForm.get(['type'])!.value,
      singerrId: this.editForm.get(['singerrId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlbums>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISinger): any {
    return item.id;
  }
}
