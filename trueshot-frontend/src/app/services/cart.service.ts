import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartItem } from '../models/cart-item';
import { Product } from '../models/product';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly items: CartItem[] = [];
  private readonly itemsSubject = new BehaviorSubject<CartItem[]>([]);

  readonly cartItems$ = this.itemsSubject.asObservable();

  addToCart(product: Product): void {
    const existing = this.items.find((item) => item.product.id === product.id);

    if (existing) {
      existing.quantity += 1;
    } else {
      this.items.push({ product, quantity: 1 });
    }

    this.itemsSubject.next([...this.items]);
  }

  removeFromCart(productId: number): void {
    const index = this.items.findIndex((item) => item.product.id === productId);

    if (index !== -1) {
      this.items.splice(index, 1);
      this.itemsSubject.next([...this.items]);
    }
  }

  clearCart(): void {
    this.items.length = 0;
    this.itemsSubject.next([]);
  }

  calculateTotal(): number {
    return this.items.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }
}
