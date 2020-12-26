import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlbums, Albums } from 'app/shared/model/albums.model';
import { AlbumsService } from './albums.service';
import { AlbumsComponent } from './albums.component';
import { AlbumsDetailComponent } from './albums-detail.component';
import { AlbumsUpdateComponent } from './albums-update.component';

@Injectable({ providedIn: 'root' })
export class AlbumsResolve implements Resolve<IAlbums> {
  constructor(private service: AlbumsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlbums> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((albums: HttpResponse<Albums>) => {
          if (albums.body) {
            return of(albums.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Albums());
  }
}

export const albumsRoute: Routes = [
  {
    path: '',
    component: AlbumsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Albums',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlbumsDetailComponent,
    resolve: {
      albums: AlbumsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Albums',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlbumsUpdateComponent,
    resolve: {
      albums: AlbumsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Albums',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlbumsUpdateComponent,
    resolve: {
      albums: AlbumsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Albums',
    },
    canActivate: [UserRouteAccessService],
  },
];
