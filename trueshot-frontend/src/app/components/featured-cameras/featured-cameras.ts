import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';

@Component({
  selector: 'app-featured-cameras',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './featured-cameras.html',
  styleUrl: './featured-cameras.css'
})
export class FeaturedCameras implements OnInit {
  featuredProducts: Product[] = [];
  currentIndex = 0;
  loading = false;
  hasError = false;

  private readonly productService = inject(ProductService);

  ngOnInit(): void {
    this.loading = true;
    this.productService.getProducts().subscribe({
      next: (products) => {
        const featured = products.filter((product) => product.featured);
        const choices = featured.length ? featured : products;
        this.featuredProducts = this.pickRandomItems(choices, 5);
        this.currentIndex = 0;
        this.loading = false;
      },
      error: () => {
        this.hasError = true;
        this.loading = false;
      }
    });
  }

  previous(): void {
    if (!this.featuredProducts.length) {
      return;
    }
    this.currentIndex = (this.currentIndex - 1 + this.featuredProducts.length) % this.featuredProducts.length;
  }

  next(): void {
    if (!this.featuredProducts.length) {
      return;
    }
    this.currentIndex = (this.currentIndex + 1) % this.featuredProducts.length;
  }

  goTo(index: number): void {
    this.currentIndex = index;
  }

  get currentProduct(): Product | null {
    if (!this.featuredProducts.length) {
      return null;
    }
    return this.featuredProducts[this.currentIndex];
  }

  private pickRandomItems(products: Product[], count: number): Product[] {
    const shuffled = [...products];

    for (let i = shuffled.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
    }

    return shuffled.slice(0, Math.min(count, shuffled.length));
  }
}
