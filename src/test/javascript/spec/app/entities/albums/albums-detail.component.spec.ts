import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TpKhTestModule } from '../../../test.module';
import { AlbumsDetailComponent } from 'app/entities/albums/albums-detail.component';
import { Albums } from 'app/shared/model/albums.model';

describe('Component Tests', () => {
  describe('Albums Management Detail Component', () => {
    let comp: AlbumsDetailComponent;
    let fixture: ComponentFixture<AlbumsDetailComponent>;
    const route = ({ data: of({ albums: new Albums(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpKhTestModule],
        declarations: [AlbumsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AlbumsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlbumsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load albums on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.albums).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
