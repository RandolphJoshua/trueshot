import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { map, catchError, of } from 'rxjs';

export const catalogResolver: ResolveFn<Product[]> = () => {
  const productService = inject(ProductService);
  return productService.getProducts().pipe(
    map(products => (products || []).filter(p => p.available !== false)),
    catchError(() => of([]))
  );
};
