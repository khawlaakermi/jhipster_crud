import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISinger, Singer } from 'app/shared/model/singer.model';
import { SingerService } from './singer.service';
import { SingerComponent } from './singer.component';
import { SingerDetailComponent } from './singer-detail.component';
import { SingerUpdateComponent } from './singer-update.component';

@Injectable({ providedIn: 'root' })
export class SingerResolve implements Resolve<ISinger> {
  constructor(private service: SingerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISinger> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((singer: HttpResponse<Singer>) => {
          if (singer.body) {
            return of(singer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Singer());
  }
}

export const singerRoute: Routes = [
  {
    path: '',
    component: SingerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Singers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SingerDetailComponent,
    resolve: {
      singer: SingerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Singers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SingerUpdateComponent,
    resolve: {
      singer: SingerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Singers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SingerUpdateComponent,
    resolve: {
      singer: SingerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Singers',
    },
    canActivate: [UserRouteAccessService],
  },
];
