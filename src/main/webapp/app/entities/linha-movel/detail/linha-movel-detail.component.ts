import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILinhaMovel } from '../linha-movel.model';

@Component({
  selector: 'jhi-linha-movel-detail',
  templateUrl: './linha-movel-detail.component.html',
})
export class LinhaMovelDetailComponent implements OnInit {
  linhaMovel: ILinhaMovel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ linhaMovel }) => {
      this.linhaMovel = linhaMovel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
