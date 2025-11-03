import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { CartService } from '../services/cart.service';
import { Product } from '../models/product';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.html',
  styleUrl: './catalog.css'
})
export class Catalog implements OnInit {
  products: Product[] = [];
  brandOptions: string[] = [];
  conditionOptions: string[] = [];
  searchTerm = '';
  selectedBrand = '';
  selectedCondition = '';
  isLoading = false;
  errorMessage = '';
  cartMessage = '';

  private readonly productService = inject(ProductService);
  private readonly cartService = inject(CartService);

  ngOnInit(): void {
    this.loadProducts();
  }

  applyFilters(): void {
    const brand = this.selectedBrand || undefined;
    const condition = this.selectedCondition || undefined;
    const keyword = this.searchTerm.trim();

    if (keyword) {
      this.fetchProductsWithSearch(keyword, brand, condition);
      return;
    }

    this.loadProducts({ brand, condition }, false);
  }

  clearFilters(): void {
    this.searchTerm = '';
    this.selectedBrand = '';
    this.selectedCondition = '';
    this.loadProducts();
  }

  addToCart(product: Product): void {
    this.cartService.addToCart(product);
    this.cartMessage = `${product.brand} ${product.modelName} added to cart.`;
    setTimeout(() => {
      this.cartMessage = '';
    }, 3000);
  }

  trackByProduct(_: number, product: Product): number {
    return product.id;
  }

  private fetchProductsWithSearch(keyword: string, brand?: string, condition?: string): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.productService
      .searchProducts({ keyword, brand, condition })
      .subscribe({
        next: (products) => {
          this.products = products;
          this.isLoading = false;
        },
        error: () => {
          this.errorMessage = 'We could not load the catalog. Please try again later.';
          this.isLoading = false;
        }
      });
  }

  private loadProducts(filters?: { brand?: string; condition?: string }, updateFilters = true): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.productService
      .getProducts(filters)
      .subscribe({
        next: (products) => {
          this.products = products;
          if (updateFilters) {
            this.updateFilterOptions(products);
          }
          this.isLoading = false;
        },
        error: () => {
          this.errorMessage = 'We could not load the catalog. Please try again later.';
          this.isLoading = false;
        }
      });
  }

  private updateFilterOptions(products: Product[]): void {
    const brands = new Set<string>();
    const conditions = new Set<string>();

    products.forEach((product) => {
      if (product.brand) {
        brands.add(product.brand);
      }
      if (product.conditionGrade) {
        conditions.add(product.conditionGrade);
      }
    });

    this.brandOptions = Array.from(brands).sort();
    this.conditionOptions = Array.from(conditions).sort();
  }
}
