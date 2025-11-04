import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { CartService } from '../services/cart.service';

type Condition = 'MINT' | 'EXCELLENT' | 'GOOD' | 'FAIR' | 'FOR_PARTS';

interface ToastMsg {
  id: number;
  text: string;
}

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.html',
  styleUrls: ['./catalog.css']
})
export class Catalog implements OnInit {
  private route = inject(ActivatedRoute);

  // master list loaded via resolver
  private allProducts: Product[] = [];

  // featured list shown on the page
  featuredProducts: Product[] = [];

  // rendered list
  products: Product[] = [];

  // filters
  allBrands: string[] = [];
  readonly conditions: Condition[] = ['MINT','EXCELLENT','GOOD','FAIR','FOR_PARTS'];
  searchKeyword = '';
  selectedBrand = '';
  selectedCondition = '';

  // ui
  errorMessage = '';
  feedbackMessage = '';

  // --- Toast state (top-right) ---
  toasts: ToastMsg[] = [];
  private toastSeq = 0;

  // --- Center dialog state ---
  showDialog = false;
  lastAddedName = '';

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const resolved = (this.route.snapshot.data['products'] as Product[]) ?? [];
    const resolvedFeatured = (this.route.snapshot.data['featuredProducts'] as Product[]) ?? [];

    this.allProducts = resolved;
    this.featuredProducts = resolvedFeatured;
    this.allBrands = this.extractBrands(this.allProducts);
    this.products = this.filterNow();

    if (!this.allProducts.length) {
      this.productService.getProducts().subscribe({
        next: list => {
          this.allProducts = (list || []).filter(p => p.available !== false);
          this.allBrands = this.extractBrands(this.allProducts);
          this.products = this.filterNow();
          // try to infer featured items from loaded products if none came from resolver
          if (!this.featuredProducts.length) {
            this.featuredProducts = this.allProducts.filter(p => (p as any).featured === true);
          }
        },
        error: () => (this.errorMessage = 'We could not load the catalog right now. Please try again later.')
      });
    } else {
      // If resolver provided products but not featured, infer from products
      if (!this.featuredProducts.length) {
        this.featuredProducts = this.allProducts.filter(p => (p as any).featured === true);
      }
    }
    // If still no featured items, fetch featured explicitly (backend may support featured=true)
    if (!this.featuredProducts.length) {
      this.productService.getFeaturedProducts().subscribe({
        next: list => {
          this.featuredProducts = (list || []).filter(p => p.available !== false);
        },
        error: () => {
          // silent fallback - template can still show "We will add featured cameras soon."
        }
      });
    }
  }

  // Filters
  applyFilters(): void { this.products = this.filterNow(); }
  onSearchInput(value: string): void { this.searchKeyword = (value || '').trim(); }
  onBrandChange(value: string): void { this.selectedBrand = value || ''; this.products = this.filterNow(); }
  onConditionChange(value: string): void { this.selectedCondition = value || ''; this.products = this.filterNow(); }
  clearFilters(): void { this.searchKeyword = ''; this.selectedBrand = ''; this.selectedCondition = ''; this.products = this.filterNow(); }

  private filterNow(): Product[] {
    const kw = this.searchKeyword.toLowerCase();
    const brand = this.selectedBrand;
    const cond = this.selectedCondition;

    return this.allProducts.filter(p => {
      if (brand && p.brand !== brand) return false;
      if (cond && p.conditionGrade !== cond) return false;
      if (kw) {
        const hay = [p.brand ?? '', p.modelName ?? '', p.description ?? '', p.sku ?? '']
          .join(' ')
          .toLowerCase();
        if (!hay.includes(kw)) return false;
      }
      return true;
    });
  }

  // Add to cart + notification
  addToCart(product: Product): void {
    if (product.available === false) {
      this.feedbackMessage = 'This item is currently unavailable.';
      return;
    }
    this.cartService.add(product);
    const name = [product.brand, product.modelName].filter(Boolean).join(' ');
    this.feedbackMessage = `${name || 'Product'} was added to your cart.`;
    setTimeout(() => (this.feedbackMessage = ''), 2000);

    // Choose ONE of the two lines below (toast vs dialog):

    // 1) Show toast (top-right)
    this.pushToast(`Added to cart: ${name || 'Product'}`);

    // 2) Or show centered dialog
    // this.openDialog(name || 'Product');
  }

  // --- Toast helpers (top-right) ---
  pushToast(text: string, ms = 2200): void {
    const id = ++this.toastSeq;
    this.toasts = [...this.toasts, { id, text }];
    setTimeout(() => this.removeToast(id), ms);
  }
  removeToast(id: number): void {
    this.toasts = this.toasts.filter(t => t.id !== id);
  }

  // --- Dialog helpers (center modal-ish) ---
  openDialog(name: string): void {
    this.lastAddedName = name;
    this.showDialog = true;
  }
  closeDialog(): void {
    this.showDialog = false;
  }

  // Image helpers
  getProductImage(id: number | null | undefined): string {
    if (id == null) return 'assets/images/placeholder-camera.jpg';
    return `assets/images/${id}.png`;
  }
  onImgError(event: Event): void {
    (event.target as HTMLImageElement).src = 'assets/images/placeholder-camera.jpg';
  }

  // ngFor perf
  trackByProductId = (_: number, p: Product) => p.id ?? _;

  private extractBrands(products: Product[]): string[] {
    return Array.from(
      new Set((products || []).map(p => p.brand).filter((b): b is string => !!b && !!b.trim()))
    ).sort();
  }
}
