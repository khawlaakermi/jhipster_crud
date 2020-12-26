import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISinger } from 'app/shared/model/singer.model';

type EntityResponseType = HttpResponse<ISinger>;
type EntityArrayResponseType = HttpResponse<ISinger[]>;

@Injectable({ providedIn: 'root' })
export class SingerService {
  public resourceUrl = SERVER_API_URL + 'api/singers';

  constructor(protected http: HttpClient) {}

  create(singer: ISinger): Observable<EntityResponseType> {
    return this.http.post<ISinger>(this.resourceUrl, singer, { observe: 'response' });
  }

  update(singer: ISinger): Observable<EntityResponseType> {
    return this.http.put<ISinger>(this.resourceUrl, singer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISinger>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISinger[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
