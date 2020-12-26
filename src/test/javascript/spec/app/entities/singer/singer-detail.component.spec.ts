import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TpKhTestModule } from '../../../test.module';
import { SingerDetailComponent } from 'app/entities/singer/singer-detail.component';
import { Singer } from 'app/shared/model/singer.model';

describe('Component Tests', () => {
  describe('Singer Management Detail Component', () => {
    let comp: SingerDetailComponent;
    let fixture: ComponentFixture<SingerDetailComponent>;
    const route = ({ data: of({ singer: new Singer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpKhTestModule],
        declarations: [SingerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SingerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SingerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load singer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.singer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
