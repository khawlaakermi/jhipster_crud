import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpKhTestModule } from '../../../test.module';
import { AlbumsComponent } from 'app/entities/albums/albums.component';
import { AlbumsService } from 'app/entities/albums/albums.service';
import { Albums } from 'app/shared/model/albums.model';

describe('Component Tests', () => {
  describe('Albums Management Component', () => {
    let comp: AlbumsComponent;
    let fixture: ComponentFixture<AlbumsComponent>;
    let service: AlbumsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpKhTestModule],
        declarations: [AlbumsComponent],
      })
        .overrideTemplate(AlbumsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlbumsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlbumsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Albums(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.albums && comp.albums[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
