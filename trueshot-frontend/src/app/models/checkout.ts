export interface CheckoutItemRequest {
  productId: number;
  quantity: number;
}

export interface CheckoutRequest {
  buyerName: string;
  buyerEmail: string;
  buyerPhone: string;
  items: CheckoutItemRequest[];
  specialInstructions?: string;
  paymentProvider?: string;
  paymentReference?: string;
  amountPaid?: number;
}
