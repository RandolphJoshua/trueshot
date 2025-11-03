import {Component, Input} from '@angular/core';
import {CommonModule, CurrencyPipe, NgFor, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-featured-cameras',
  imports: [CommonModule, NgFor, NgIf, RouterLink, CurrencyPipe],
  templateUrl: './featured-cameras.html',
  styleUrl: './featured-cameras.css'
})
export class FeaturedCameras {

}
