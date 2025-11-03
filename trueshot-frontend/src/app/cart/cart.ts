import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { CartService } from '../services/cart.service';
import { CartItem } from '../models/cart-item';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class Cart implements OnInit, OnDestroy {
  cartItems: CartItem[] = [];
  total = 0;
  message = '';

  private readonly cartService = inject(CartService);
  private subscription?: Subscription;

  ngOnInit(): void {
    this.subscription = this.cartService.cartItems$.subscribe((items) => {
      this.cartItems = items;
      this.total = this.cartService.calculateTotal();
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  removeItem(item: CartItem): void {
    this.cartService.removeFromCart(item.product.id);
    this.message = `${item.product.brand} ${item.product.modelName} removed from the cart.`;
  }

  checkout(): void {
    if (!this.cartItems.length) {
      this.message = 'Your cart is empty.';
      return;
    }

    this.cartService.clearCart();
    this.message = 'Thank you! Your order has been placed.';
  }
}
