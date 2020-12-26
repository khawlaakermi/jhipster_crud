import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISinger, Singer } from 'app/shared/model/singer.model';
import { SingerService } from './singer.service';

@Component({
  selector: 'jhi-singer-update',
  templateUrl: './singer-update.component.html',
})
export class SingerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fname: [],
    lname: [],
    adress: [],
  });

  constructor(protected singerService: SingerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ singer }) => {
      this.updateForm(singer);
    });
  }

  updateForm(singer: ISinger): void {
    this.editForm.patchValue({
      id: singer.id,
      fname: singer.fname,
      lname: singer.lname,
      adress: singer.adress,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const singer = this.createFromForm();
    if (singer.id !== undefined) {
      this.subscribeToSaveResponse(this.singerService.update(singer));
    } else {
      this.subscribeToSaveResponse(this.singerService.create(singer));
    }
  }

  private createFromForm(): ISinger {
    return {
      ...new Singer(),
      id: this.editForm.get(['id'])!.value,
      fname: this.editForm.get(['fname'])!.value,
      lname: this.editForm.get(['lname'])!.value,
      adress: this.editForm.get(['adress'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISinger>>): void {
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
}
