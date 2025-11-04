import {ChangeDetectorRef, Component, OnInit, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';

type FeaturedCamera = Product & { imageSrc: string };


@Component({
  selector: 'app-featured-cameras',
  imports: [CommonModule],
  templateUrl: './featured-cameras.html',
  styleUrls: ['./featured-cameras.css']
})
export class FeaturedCameras implements OnInit {
  private readonly cdr = inject(ChangeDetectorRef);

  cameras: FeaturedCamera[] = [];
  currentIndex = 0;
  errorMessage = '';
  // fallback placeholder used when an image is missing
  placeholderImage = 'https://placehold.co/320x200';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadFeatured();
  }

  onImgError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (img) {
      img.src = this.placeholderImage;
    }
  }

  loadFeatured(): void {
    this.productService.getProducts().subscribe({
      next: products => {
        const featured = products.filter(product => product.featured);
        const source = featured.length ? featured : products;
        this.errorMessage = '';
        this.cameras = this.decorateCameras(this.pickRandomFive(source));
        this.currentIndex = 0;
        this.cdr.detectChanges();

      },
      error: () => {
        this.errorMessage = 'Featured cameras are unavailable right now.';
        this.cdr.detectChanges();
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

  private decorateCameras(products: Product[]): FeaturedCamera[] {
    return products.map(product => ({
      ...product,
      imageSrc: this.resolveImageSource(product)
    }));
  }

  private resolveImageSource(product: Product): string {
    if (product.id != null) {
      return `assets/images/${product.id}.png`;
    }
    if (product.imageUrl) {
      return product.imageUrl;
    }
    return this.placeholderImage;
  }
}
