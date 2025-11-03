import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, shareReplay } from 'rxjs/operators';

import { AppDetails } from '../models/app-details';
import { API_BASE_URL } from './api-config';

@Injectable({ providedIn: 'root' })
export class AppDetailsService {
  private readonly http = inject(HttpClient);
  private readonly activeDetails$ = this.http
    .get<AppDetails | null>(`${API_BASE_URL}/app-details/active`)
    .pipe(
      catchError(() => of(null)),
      shareReplay({ bufferSize: 1, refCount: true })
    );

  getActiveDetails(): Observable<AppDetails | null> {
    return this.activeDetails$;
  }
}
