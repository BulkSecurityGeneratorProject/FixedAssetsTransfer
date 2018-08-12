/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { TestingComponent } from 'app/entities/testing/testing.component';
import { TestingService } from 'app/entities/testing/testing.service';
import { Testing } from 'app/shared/model/testing.model';

describe('Component Tests', () => {
    describe('Testing Management Component', () => {
        let comp: TestingComponent;
        let fixture: ComponentFixture<TestingComponent>;
        let service: TestingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [TestingComponent],
                providers: []
            })
                .overrideTemplate(TestingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TestingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Testing(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.testings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
