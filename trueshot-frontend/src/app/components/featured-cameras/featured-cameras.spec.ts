import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeaturedCameras } from './featured-cameras';

describe('FeaturedCameras', () => {
  let component: FeaturedCameras;
  let fixture: ComponentFixture<FeaturedCameras>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeaturedCameras]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeaturedCameras);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
