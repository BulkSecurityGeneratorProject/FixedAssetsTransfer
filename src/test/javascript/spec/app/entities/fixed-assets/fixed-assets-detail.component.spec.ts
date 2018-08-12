/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { FixedAssetsDetailComponent } from 'app/entities/fixed-assets/fixed-assets-detail.component';
import { FixedAssets } from 'app/shared/model/fixed-assets.model';

describe('Component Tests', () => {
    describe('FixedAssets Management Detail Component', () => {
        let comp: FixedAssetsDetailComponent;
        let fixture: ComponentFixture<FixedAssetsDetailComponent>;
        const route = ({ data: of({ fixedAssets: new FixedAssets(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [FixedAssetsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FixedAssetsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FixedAssetsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fixedAssets).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
