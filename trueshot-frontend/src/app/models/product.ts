export interface Product {
  id: number;
  sku: string;
  brand: string;
  modelName: string;
  conditionGrade: string;
  description: string;
  imageUrl: string;
  lensMount: string;
  sensorType: string;
  releaseYear: number | null;
  shutterCount: number | null;
  stockQuantity: number;
  price: number;
  featured: boolean;
  available: boolean;
  createdAt: string;
  lastUpdated: string;
}
