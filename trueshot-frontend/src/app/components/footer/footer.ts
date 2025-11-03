import {Component, Inject, PLATFORM_ID} from '@angular/core';
import {RouterLink} from '@angular/router';
import {isPlatformBrowser} from '@angular/common';

@Component({
  selector: 'app-footer',
  imports: [
    RouterLink
  ],
  templateUrl: './footer.html',
  styleUrl: './footer.css'
})
export class Footer {
  readonly year = new Date().getFullYear();

  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  scrollTop(event: Event) {
    event.preventDefault();
    if (this.isBrowser) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
