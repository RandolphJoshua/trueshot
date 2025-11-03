import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '',
    pathMatch: 'full',
    loadComponent: () => {return import('./home/home').then(m => m.Home)} },
  { path: 'catalog',
    loadComponent: () => {return import('./catalog/catalog').then(m => m.Catalog)} },
  // { path: 'about',
  //   loadComponent: () => {return import('./about/about').then(m => m.About)} },
  { path: 'contact-us',
    loadComponent: () => {return import('./contact-us/contact-us').then(m => m.ContactUs)} },
  { path: 'cart',
    loadComponent: () => {return import('./cart/cart').then(m => m.Cart)} }
];
