import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TpKhTestModule } from '../../../test.module';
import { SingerUpdateComponent } from 'app/entities/singer/singer-update.component';
import { SingerService } from 'app/entities/singer/singer.service';
import { Singer } from 'app/shared/model/singer.model';

describe('Component Tests', () => {
  describe('Singer Management Update Component', () => {
    let comp: SingerUpdateComponent;
    let fixture: ComponentFixture<SingerUpdateComponent>;
    let service: SingerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TpKhTestModule],
        declarations: [SingerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SingerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SingerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SingerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Singer(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Singer();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
