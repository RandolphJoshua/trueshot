import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';
import { CartItem } from '../models/cart-item';
import { CheckoutRequest } from '../models/checkout';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, FormsModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class Cart implements OnInit, OnDestroy {
  items: CartItem[] = [];
  total = 0;
  sending = false;
  successMessage = '';
  errorMessage = '';
  private subscription?: Subscription;

  buyer = {
    name: '',
    email: '',
    phone: '',
    instructions: ''
  };

  constructor(private cartService: CartService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.subscription = this.cartService.items$.subscribe(items => {
      this.items = items;
      this.total = this.cartService.getTotal();
    });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  updateQuantity(item: CartItem, change: number): void {
    const newQuantity = item.quantity + change;
    if (item.product.id == null) {
      return;
    }
    if (newQuantity <= 0) {
      this.cartService.remove(item.product.id);
    } else {
      this.cartService.updateQuantity(item.product.id, newQuantity);
    }
  }

  remove(item: CartItem): void {
    if (item.product.id == null) {
      return;
    }
    this.cartService.remove(item.product.id);
  }

  checkout(form: NgForm): void {
    if (!this.items.length) {
      this.errorMessage = 'Your cart is empty. Add some products first.';
      return;
    }

    if (form.invalid) {
      form.control.markAllAsTouched();
      this.errorMessage = 'Please complete your details before checking out.';
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.sending = true;

    const request: CheckoutRequest = {
      buyerName: this.buyer.name,
      buyerEmail: this.buyer.email,
      buyerPhone: this.buyer.phone,
      items: this.items
        .filter(item => item.product.id != null)
        .map(item => ({ productId: item.product.id as number, quantity: item.quantity })),
      specialInstructions: this.buyer.instructions || undefined
    };

    this.orderService.checkout(request).subscribe({
      next: () => {
        this.sending = false;
        this.successMessage = 'Thank you! Your order was submitted.';
        this.cartService.clear();
        form.resetForm();
        this.buyer = { name: '', email: '', phone: '', instructions: '' };
      },
      error: () => {
        this.sending = false;
        this.errorMessage = 'We could not process the checkout right now. Please try again later.';
      }
    });
  }
}
