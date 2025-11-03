import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartItem } from '../models/cart-item';
import { Product } from '../models/product';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly itemsSubject = new BehaviorSubject<CartItem[]>([]);
  readonly items$ = this.itemsSubject.asObservable();

  add(product: Product): void {
    const items = [...this.itemsSubject.value];
    const existing = items.find(item => item.product.id === product.id);
    if (existing) {
      existing.quantity += 1;
    } else {
      items.push({ product, quantity: 1 });
    }
    this.itemsSubject.next(items);
  }

  updateQuantity(productId: number, quantity: number): void {
    const items = this.itemsSubject.value
      .map(item =>
        item.product.id === productId ? { ...item, quantity: Math.max(1, quantity) } : item
      )
      .filter(item => item.quantity > 0);
    this.itemsSubject.next(items);
  }

  remove(productId: number): void {
    const items = this.itemsSubject.value.filter(item => item.product.id !== productId);
    this.itemsSubject.next(items);
  }

  clear(): void {
    this.itemsSubject.next([]);
  }

  getItems(): CartItem[] {
    return this.itemsSubject.value;
  }

  getTotal(): number {
    return this.itemsSubject.value.reduce((total, item) => {
      const price = item.product.price ?? 0;
      return total + price * item.quantity;
    }, 0);
  }
}
