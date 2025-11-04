import {Component, inject} from '@angular/core';
import {Order, OrderItem} from '../models/order';
import {OrderService} from '../services/order.service';
import {CommonModule, CurrencyPipe, DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';

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
  styleUrl: './order-status.css'
})
export class OrderStatus {
  private readonly orderService = inject(OrderService);
  protected readonly placeholderImage = 'assets/images/placeholder-camera.jpg';

  protected searchOrderId = '';
  protected searchEmail = '';
  protected loading = false;
  protected errorMessage = '';
  protected orders: Order[] = [];
  protected selectedOrder: Order | null = null;

  lookupOrder(): void {
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
