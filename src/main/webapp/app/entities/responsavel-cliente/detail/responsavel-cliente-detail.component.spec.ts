import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResponsavelClienteDetailComponent } from './responsavel-cliente-detail.component';

describe('ResponsavelCliente Management Detail Component', () => {
  let comp: ResponsavelClienteDetailComponent;
  let fixture: ComponentFixture<ResponsavelClienteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResponsavelClienteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ responsavelCliente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResponsavelClienteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResponsavelClienteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load responsavelCliente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.responsavelCliente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
