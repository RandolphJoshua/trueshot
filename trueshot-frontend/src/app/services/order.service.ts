import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CheckoutRequest } from '../models/checkout';
import { API_BASE_URL } from './api-config';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly http = inject(HttpClient);
  private readonly ordersUrl = `${API_BASE_URL}/orders`;

  checkout(request: CheckoutRequest): Observable<any> {
    return this.http.post(`${this.ordersUrl}/checkout`, request);
  }

  getOrderbyId(id: number): Observable<any> {
    return this.http.get(`${this.ordersUrl}/${id}`);
  }
}
