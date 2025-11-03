import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { API_BASE_URL } from './api-config';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly productsUrl = `${API_BASE_URL}/products`;

  getProducts(brand?: string, condition?: string): Observable<Product[]> {
    let params = new HttpParams();
    if (brand) {
      params = params.set('brand', brand);
    }
    if (condition) {
      params = params.set('condition', condition);
    }
    return this.http.get<Product[]>(this.productsUrl, { params });
  }

  searchProducts(keyword: string, brand?: string, condition?: string): Observable<Product[]> {
    let params = new HttpParams();
    if (keyword) {
      params = params.set('keyword', keyword);
    }
    if (brand) {
      params = params.set('brand', brand);
    }
    if (condition) {
      params = params.set('condition', condition);
    }
    return this.http.get<Product[]>(`${this.productsUrl}/search`, { params });
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.productsUrl}/${id}`);
  }
}
