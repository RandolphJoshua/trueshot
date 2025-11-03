import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-featured-cameras',
  imports: [CommonModule],
  templateUrl: './featured-cameras.html',
  styleUrl: './featured-cameras.css'
})
export class FeaturedCameras implements OnInit {
  cameras: Product[] = [];
  currentIndex = 0;
  errorMessage = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadFeatured();
  }

  loadFeatured(): void {
    this.productService.getProducts().subscribe({
      next: products => {
        const featured = products.filter(product => product.featured);
        const source = featured.length ? featured : products;
        this.cameras = this.pickRandomFive(source);
        this.currentIndex = 0;
      },
      error: () => {
        this.errorMessage = 'Featured cameras are unavailable right now.';
      }
    });
  }

  previous(): void {
    if (!this.cameras.length) {
      return;
    }
    this.currentIndex = (this.currentIndex - 1 + this.cameras.length) % this.cameras.length;
  }

  next(): void {
    if (!this.cameras.length) {
      return;
    }
    this.currentIndex = (this.currentIndex + 1) % this.cameras.length;
  }

  private pickRandomFive(products: Product[]): Product[] {
    const copy = [...products];
    for (let i = copy.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [copy[i], copy[j]] = [copy[j], copy[i]];
    }
    return copy.slice(0, Math.min(5, copy.length));
  }
}
