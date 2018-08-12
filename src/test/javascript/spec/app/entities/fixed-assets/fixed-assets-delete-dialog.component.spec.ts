/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTransferTestModule } from '../../../test.module';
import { FixedAssetsDeleteDialogComponent } from 'app/entities/fixed-assets/fixed-assets-delete-dialog.component';
import { FixedAssetsService } from 'app/entities/fixed-assets/fixed-assets.service';

describe('Component Tests', () => {
    describe('FixedAssets Management Delete Component', () => {
        let comp: FixedAssetsDeleteDialogComponent;
        let fixture: ComponentFixture<FixedAssetsDeleteDialogComponent>;
        let service: FixedAssetsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FixedAssetsTransferTestModule],
                declarations: [FixedAssetsDeleteDialogComponent]
            })
                .overrideTemplate(FixedAssetsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FixedAssetsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FixedAssetsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
