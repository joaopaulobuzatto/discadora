import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LinhaMovelDetailComponent } from './linha-movel-detail.component';

describe('LinhaMovel Management Detail Component', () => {
  let comp: LinhaMovelDetailComponent;
  let fixture: ComponentFixture<LinhaMovelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LinhaMovelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ linhaMovel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LinhaMovelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LinhaMovelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load linhaMovel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.linhaMovel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
