import { Routes } from '@angular/router';
import {catalogResolver} from './catalog/catalog.resolver';

export const routes: Routes = [
  { path: '',
    pathMatch: 'full',
    loadComponent: () => {return import('./home/home').then(m => m.Home)} },
  { path: 'catalog',
    loadComponent: () => {return import('./catalog/catalog').then(m => m.Catalog)},
    resolve: { products: catalogResolver }   // <-- prefetch here
  },
  { path: 'order-status',
    loadComponent: () => {return import('./order-status/order-status').then(m => m.OrderStatus)} },
  { path: 'contact-us',
    loadComponent: () => {return import('./contact-us/contact-us').then(m => m.ContactUs)} },
  { path: 'cart',
    loadComponent: () => {return import('./cart/cart').then(m => m.Cart)} }
];
