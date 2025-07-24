import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleRecentComponent } from './article-recent.component';

describe('ArticleRecentComponent', () => {
  let component: ArticleRecentComponent;
  let fixture: ComponentFixture<ArticleRecentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArticleRecentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleRecentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
