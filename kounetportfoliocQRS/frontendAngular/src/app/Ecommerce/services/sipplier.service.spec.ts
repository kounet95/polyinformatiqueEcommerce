import { TestBed } from '@angular/core/testing';

import { SipplierService } from './sipplier.service';

describe('SipplierService', () => {
  let service: SipplierService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SipplierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
