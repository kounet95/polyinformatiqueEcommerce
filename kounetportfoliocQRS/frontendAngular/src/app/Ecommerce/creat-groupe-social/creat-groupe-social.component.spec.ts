import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatGroupeSocialComponent } from './creat-groupe-social.component';

describe('CreatGroupeSocialComponent', () => {
  let component: CreatGroupeSocialComponent;
  let fixture: ComponentFixture<CreatGroupeSocialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatGroupeSocialComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreatGroupeSocialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
