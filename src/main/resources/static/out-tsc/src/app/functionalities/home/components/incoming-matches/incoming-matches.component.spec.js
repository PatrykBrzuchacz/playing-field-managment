import { async, TestBed } from '@angular/core/testing';
import { IncomingMatchesComponent } from './incoming-matches.component';
describe('IncomingMatchesComponent', function () {
    var component;
    var fixture;
    beforeEach(async(function () {
        TestBed.configureTestingModule({
            declarations: [IncomingMatchesComponent]
        })
            .compileComponents();
    }));
    beforeEach(function () {
        fixture = TestBed.createComponent(IncomingMatchesComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    it('should create', function () {
        expect(component).toBeTruthy();
    });
});
//# sourceMappingURL=incoming-matches.component.spec.js.map