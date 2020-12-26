import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TpKhTestModule } from '../../../test.module';
import { SingerComponent } from 'app/entities/singer/singer.component';
import { SingerService } from 'app/entities/singer/singer.service';
import { Singer } from 'app/shared/model/singer.model';

describe('Component Tests', () => {
  describe('Singer Management Component', () => {
    let comp: SingerComponent;
    let fixture: ComponentFixture<SingerComponent>;
    let service: SingerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpKhTestModule],
        declarations: [SingerComponent],
      })
        .overrideTemplate(SingerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SingerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SingerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Singer(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.singers && comp.singers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
