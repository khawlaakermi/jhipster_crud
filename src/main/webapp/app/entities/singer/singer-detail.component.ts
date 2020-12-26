import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISinger } from 'app/shared/model/singer.model';

@Component({
  selector: 'jhi-singer-detail',
  templateUrl: './singer-detail.component.html',
})
export class SingerDetailComponent implements OnInit {
  singer: ISinger | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ singer }) => (this.singer = singer));
  }

  previousState(): void {
    window.history.back();
  }
}
