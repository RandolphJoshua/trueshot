import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Faqcomponent } from './faqcomponent';

describe('Faqcomponent', () => {
  let component: Faqcomponent;
  let fixture: ComponentFixture<Faqcomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Faqcomponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Faqcomponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
