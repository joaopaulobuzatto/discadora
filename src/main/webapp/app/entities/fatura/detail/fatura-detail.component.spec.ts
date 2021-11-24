import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FaturaDetailComponent } from './fatura-detail.component';

describe('Fatura Management Detail Component', () => {
  let comp: FaturaDetailComponent;
  let fixture: ComponentFixture<FaturaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FaturaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fatura: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FaturaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FaturaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fatura on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fatura).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
