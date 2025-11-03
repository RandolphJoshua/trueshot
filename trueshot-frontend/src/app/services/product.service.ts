import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'https://localhost:8080/api/products';

  getProducts(filters?: { brand?: string; condition?: string }): Observable<Product[]> {
    let params = new HttpParams();

    if (filters?.brand) {
      params = params.set('brand', filters.brand);
    }

    if (filters?.condition) {
      params = params.set('condition', filters.condition);
    }

    return this.http.get<Product[]>(this.baseUrl, { params });
  }

  searchProducts(criteria: { keyword?: string; brand?: string; condition?: string }): Observable<Product[]> {
    let params = new HttpParams();

    if (criteria.keyword) {
      params = params.set('keyword', criteria.keyword);
    }

    if (criteria.brand) {
      params = params.set('brand', criteria.brand);
    }

    if (criteria.condition) {
      params = params.set('condition', criteria.condition);
    }

    return this.http.get<Product[]>(`${this.baseUrl}/search`, { params });
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/${id}`);
  }
}
