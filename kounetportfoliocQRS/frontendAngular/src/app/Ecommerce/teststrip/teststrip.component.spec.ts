import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeststripComponent } from './teststrip.component';

describe('TeststripComponent', () => {
  let component: TeststripComponent;
  let fixture: ComponentFixture<TeststripComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TeststripComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeststripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
