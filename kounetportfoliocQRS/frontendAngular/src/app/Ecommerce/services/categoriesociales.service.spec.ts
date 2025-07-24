import { TestBed } from '@angular/core/testing';

import { CategoriesocialesService } from './categoriesociales.service';

describe('CategoriesocialesService', () => {
  let service: CategoriesocialesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriesocialesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
