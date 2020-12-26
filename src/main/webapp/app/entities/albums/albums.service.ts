import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlbums } from 'app/shared/model/albums.model';

type EntityResponseType = HttpResponse<IAlbums>;
type EntityArrayResponseType = HttpResponse<IAlbums[]>;

@Injectable({ providedIn: 'root' })
export class AlbumsService {
  public resourceUrl = SERVER_API_URL + 'api/albums';

  constructor(protected http: HttpClient) {}

  create(albums: IAlbums): Observable<EntityResponseType> {
    return this.http.post<IAlbums>(this.resourceUrl, albums, { observe: 'response' });
  }

  update(albums: IAlbums): Observable<EntityResponseType> {
    return this.http.put<IAlbums>(this.resourceUrl, albums, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlbums>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlbums[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
