import {Component, Inject, PLATFORM_ID, OnInit, inject, ChangeDetectorRef} from '@angular/core';
import {RouterLink} from '@angular/router';
import {isPlatformBrowser, CommonModule} from '@angular/common';
import { AppDetailsService } from '../../services/app-details.service';
import { AppDetails } from '../../models/app-details';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ],
  templateUrl: './footer.html',
  styleUrls: ['./footer.css']
})
export class Footer implements OnInit {
  readonly year = new Date().getFullYear();

  private isBrowser: boolean;
  private readonly appDetails = inject(AppDetailsService);
  private readonly cdr = inject(ChangeDetectorRef);

  // dynamic contact information (loaded from API)
  supportEmail = '';
  supportPhone = '';

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngOnInit(): void {
    this.appDetails.getActive().subscribe((d: AppDetails | null) => {
      this.supportEmail = d?.supportEmail ?? '';
      this.supportPhone = d?.supportNumber ?? '';
      // ensure template bindings are stable after async update (prevents ExpressionChangedAfterItHasBeenCheckedError in dev mode)
      // detectChanges can throw in some lifecycle states so guard it.
      try { this.cdr.detectChanges(); } catch { /* noop */ }
    });
  }

  scrollTop(event: Event) {
    event.preventDefault();
    if (this.isBrowser) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
