export interface Product {
  id?: number;
  sku?: string;
  brand?: string;
  modelName?: string;
  conditionGrade?: string;
  description?: string;
  imageUrl?: string;
  lensMount?: string;
  sensorType?: string;
  releaseYear?: number;
  shutterCount?: number;
  stockQuantity?: number;
  price?: number;
  featured?: boolean;
  available?: boolean;
}
