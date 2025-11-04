import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, shareReplay, map } from 'rxjs/operators';

import { AppDetails } from '../models/app-details';
import { API_BASE_URL } from './api-config';

@Injectable({ providedIn: 'root' })
export class AppDetailsService {
  private readonly http = inject(HttpClient);
  private readonly url = `${API_BASE_URL}/app-details/active`;
  private readonly activeDetails$ = this.http
    .get<AppDetails | null>(`${API_BASE_URL}/app-details/active`);

  readonly heroMessage$: Observable<string | null> = this.activeDetails$.pipe(
    map(d => d?.heroMessage ?? null)
  );

  readonly aboutContent$: Observable<string | null> = this.activeDetails$.pipe(
    map(d => d?.aboutContent ?? null)
  );

  getActive(): Observable<AppDetails | null> {
    return this.activeDetails$;
  }

  getHeroMessage(): Observable<string | null> {
    return this.heroMessage$;
  }

  getAboutContent(): Observable<string | null> {
    return this.aboutContent$;
  }
}
