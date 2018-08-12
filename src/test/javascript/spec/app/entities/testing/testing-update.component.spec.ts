/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { TestingUpdateComponent } from 'app/entities/testing/testing-update.component';
import { TestingService } from 'app/entities/testing/testing.service';
import { Testing } from 'app/shared/model/testing.model';

describe('Component Tests', () => {
    describe('Testing Management Update Component', () => {
        let comp: TestingUpdateComponent;
        let fixture: ComponentFixture<TestingUpdateComponent>;
        let service: TestingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [TestingUpdateComponent]
            })
                .overrideTemplate(TestingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TestingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Testing(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.testing = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Testing();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.testing = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
