import {ChangeDetectorRef, Component, DestroyRef, inject} from '@angular/core';
import {Order, OrderItem} from '../models/order';
import {OrderService} from '../services/order.service';
import {CommonModule, CurrencyPipe, DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {finalize} from 'rxjs';

@Component({
  selector: 'app-order-status',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe,
    FormsModule,
    CommonModule
  ],

  templateUrl: './order-status.html',
  styleUrl: './order-status.css',

})
export class OrderStatus {
  private readonly orderService = inject(OrderService);
  protected readonly placeholderImage = 'assets/images/placeholder-camera.jpg';
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly destroyRef = inject(DestroyRef);

  protected searchOrderId = '';
  protected searchEmail = '';
  protected loading = false;
  protected errorMessage = '';
  protected orders: Order[] = [];
  protected selectedOrder: Order | null = null;

  lookupOrder(): void {
    if (this.loading) {
      return;
    }
    const trimmedId = this.searchOrderId.trim();
    const trimmedEmail = this.searchEmail.trim();

    if (!trimmedId && !trimmedEmail) {
      this.errorMessage = 'Enter your order ID or the email you used during checkout.';
      this.selectedOrder = null;
      this.orders = [];
      return;
    }

    if (trimmedId && isNaN(Number(trimmedId))) {
      this.errorMessage = 'Order ID should contain numbers only.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.selectedOrder = null;
    this.orders = [];

    if (trimmedId) {
      const orderId = Number(trimmedId);
      this.orderService.getOrderById(orderId).subscribe({
        next: (order) => {
          this.orders = order ? [order] : [];
          this.selectedOrder = order ?? null;
          if (!this.selectedOrder) {
            this.errorMessage = 'Order not found.';
          }
          this.loading = false;
        },
        error: () => {
          this.errorMessage = 'We could not find an order with that ID.';
          this.loading = false;
        }
      });
      return;
    }

    this.orderService.getOrdersByEmail(trimmedEmail.toLowerCase()).subscribe({
      next: (orders) => {
        this.orders = orders ?? [];
        this.selectedOrder = this.orders[0] ?? null;
        if (!this.selectedOrder) {
          this.errorMessage = 'No orders found for that email address.';
        }
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Something went wrong while looking up your orders.';
        this.loading = false;
      }
    });
  }

  // Ensure pressing Enter uses the latest ngModel value.
  // Prevent default submit, blur the input (commits model), then call lookupOrder.
  onEnter(event: Event): void {
    // event is typed as Event from the template; cast to KeyboardEvent only if needed
    event.preventDefault();
    const target = event.target as HTMLInputElement | null;
    if (target && typeof target.blur === 'function') {
      target.blur();
    }
    // Schedule lookup after blur so ngModel has updated
    setTimeout(() => this.lookupOrder(), 0);
  }

  selectOrder(order: Order): void {
    this.selectedOrder = order;
  }

  reset(): void {
    this.searchEmail = '';
    this.searchOrderId = '';
    this.errorMessage = '';
    this.orders = [];
    this.selectedOrder = null;
  }

  getStatusLabel(status?: string | null): string {
    if (!status) {
      return 'Unknown status';
    }
    return status.replace(/_/g, ' ');
  }

  trackByItemId(_: number, item: OrderItem): number | undefined {
    return item.id ?? item.productId;
  }

  onImgError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = this.placeholderImage;
  }
}
