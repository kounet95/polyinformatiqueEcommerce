import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDomainComponent } from './create-domain.component';

describe('CreateDomainComponent', () => {
  let component: CreateDomainComponent;
  let fixture: ComponentFixture<CreateDomainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateDomainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateDomainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
