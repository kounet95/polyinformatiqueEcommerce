import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrincingComponent } from './princing.component';

describe('PrincingComponent', () => {
  let component: PrincingComponent;
  let fixture: ComponentFixture<PrincingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PrincingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrincingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
