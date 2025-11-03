import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-catalog',
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.html',
  styleUrl: './catalog.css'
})
export class Catalog implements OnInit {
  products: Product[] = [];
  allBrands: string[] = [];
  readonly conditions = ['MINT', 'EXCELLENT', 'GOOD', 'FAIR', 'FOR_PARTS'];
  searchKeyword = '';
  selectedBrand = '';
  selectedCondition = '';
  loading = false;
  errorMessage = '';
  feedbackMessage = '';

  constructor(private productService: ProductService, private cartService: CartService) {}

  ngOnInit(): void {
    this.loadBrands();
    this.loadProducts();
  }

  loadBrands(): void {
    this.productService.getProducts().subscribe({
      next: products => {
        this.allBrands = this.extractBrands(products);
      },
      error: () => {
        this.allBrands = [];
      }
    });
  }

  loadProducts(): void {
    this.loading = true;
    this.errorMessage = '';
    const brand = this.selectedBrand || undefined;
    const condition = this.selectedCondition || undefined;
    const keyword = this.searchKeyword.trim();
    const request$ = keyword
      ? this.productService.searchProducts(keyword, brand, condition)
      : this.productService.getProducts(brand, condition);

    request$.subscribe({
      next: products => {
        this.products = products.filter(product => product.available !== false);
        if (!this.allBrands.length) {
          this.allBrands = this.extractBrands(products);
        }
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'We could not load the catalog right now. Please try again later.';
        this.products = [];
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.loadProducts();
  }

  clearFilters(): void {
    this.searchKeyword = '';
    this.selectedBrand = '';
    this.selectedCondition = '';
    this.loadProducts();
  }

  addToCart(product: Product): void {
    if (product.available === false) {
      this.feedbackMessage = 'This item is currently unavailable.';
      return;
    }
    this.cartService.add(product);
    const name = [product.brand, product.modelName].filter(Boolean).join(' ');
    this.feedbackMessage = `${name || 'Product'} was added to your cart.`;
    setTimeout(() => {
      this.feedbackMessage = '';
    }, 3000);
  }

  private extractBrands(products: Product[]): string[] {
    return Array.from(
      new Set(
        products
          .map(product => product.brand)
          .filter((brand): brand is string => !!brand && brand.trim().length > 0)
      )
    ).sort();
  }
}
