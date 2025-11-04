export interface OrderItem {
  id?: number;
  orderId?: number;
  productId?: number;
  productName?: string;
  productBrand?: string;
  productCondition?: string;
  productImageUrl?: string;
  unitPrice?: number;
  quantity?: number;
  lineTotal?: number;
  status?: string;
}

export interface Order {
  id?: number;
  buyerName?: string;
  buyerEmail?: string;
  buyerPhone?: string;
  status?: string;
  totalAmount?: number;
  specialInstructions?: string;
  createdAt?: string;
  lastUpdated?: string;
  items?: OrderItem[];
}
