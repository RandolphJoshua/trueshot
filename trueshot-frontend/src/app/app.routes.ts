import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '',
    pathMatch: 'full',
    loadComponent: () => {return import('./home/home').then(m => m.Home)} },
  { path: 'gallery',
    loadComponent: () => {return import('./gallery/gallery').then(m => m.Gallery)} }
];
