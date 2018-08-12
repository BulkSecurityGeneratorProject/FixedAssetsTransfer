/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { FixedAssetsComponent } from 'app/entities/fixed-assets/fixed-assets.component';
import { FixedAssetsService } from 'app/entities/fixed-assets/fixed-assets.service';
import { FixedAssets } from 'app/shared/model/fixed-assets.model';

describe('Component Tests', () => {
    describe('FixedAssets Management Component', () => {
        let comp: FixedAssetsComponent;
        let fixture: ComponentFixture<FixedAssetsComponent>;
        let service: FixedAssetsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [FixedAssetsComponent],
                providers: []
            })
                .overrideTemplate(FixedAssetsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FixedAssetsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FixedAssetsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FixedAssets(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fixedAssets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
