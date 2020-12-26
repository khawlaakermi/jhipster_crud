import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlbums } from 'app/shared/model/albums.model';

@Component({
  selector: 'jhi-albums-detail',
  templateUrl: './albums-detail.component.html',
})
export class AlbumsDetailComponent implements OnInit {
  albums: IAlbums | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ albums }) => (this.albums = albums));
  }

  previousState(): void {
    window.history.back();
  }
}
