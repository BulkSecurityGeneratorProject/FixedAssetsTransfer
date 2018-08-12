/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { TestingDetailComponent } from 'app/entities/testing/testing-detail.component';
import { Testing } from 'app/shared/model/testing.model';

describe('Component Tests', () => {
    describe('Testing Management Detail Component', () => {
        let comp: TestingDetailComponent;
        let fixture: ComponentFixture<TestingDetailComponent>;
        const route = ({ data: of({ testing: new Testing(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [TestingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TestingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TestingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.testing).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
